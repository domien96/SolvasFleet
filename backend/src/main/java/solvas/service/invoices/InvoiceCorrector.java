package solvas.service.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.service.models.Contract;
import solvas.service.models.InvoiceItem;
import solvas.service.models.InvoiceItemType;
import solvas.service.models.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class containing logic to correct invoices after retroactive changes
 */
@Component
public class InvoiceCorrector {

    private static final int PERCENTAGE_SCALE = 2;
    private static final String TOO_MANY_REPAYMENT_MESSAGE = "Day with more repayments than payments";
    private final DaoContext daoContext;

    /**
     * Create instance
     *
     * @param daoContext DaoContext
     */
    @Autowired
    public InvoiceCorrector(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    /**
     * Generate correction items for a contract
     *
     * @param contract      Contract to generate corrections for
     * @param correctBefore Corrections at or after this date are ignored
     * @param paymentPeriod Duration of the payment period in months.
     *
     * @return Set of generated invoice items to correct
     */
    public Set<InvoiceItem> correctionItemsForContract(Contract contract, LocalDate correctBefore, int paymentPeriod) {
        Collection<InvoiceItem> invoiceItems = contract.getInvoiceItems();

        Map<InvoiceItemType, List<Period>> groupedItems = invoiceItems.stream()
                .collect(Collectors.groupingBy(
                        InvoiceItem::getType,
                        Collectors.mapping(t -> new Period(t.getStartDate(), t.getEndDate()), Collectors.toList())
                ));

        // Sort to be sure
        List<Period> paidPeriods = groupedItems.getOrDefault(InvoiceItemType.PAYMENT, new ArrayList<>());
        paidPeriods.sort(Comparator.comparing(Period::getStartDate).thenComparing(Period::getEndDate));

        Period periodToPay = new Period(contract.getStartDate().toLocalDate(), contract.getEndDate().toLocalDate());
        Pair<List<Period>, List<Period>> corrections = calculateCorrections(paidPeriods, periodToPay);

        Set<InvoiceItem> items = limitPeriods(corrections.getFirst().stream(), correctBefore)
                .map(period -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setContract(contract);
                    item.setStartDate(period.getStartDate());
                    item.setEndDate(period.getEndDate());
                    item.setType(InvoiceItemType.REPAYMENT);
                    setTotalAndTax(item, paymentPeriod, true);
                    return item;
                })
                .collect(Collectors.toSet());

        items.addAll(limitPeriods(corrections.getSecond().stream(), correctBefore)
                .map(period -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setContract(contract);
                    item.setStartDate(period.getStartDate());
                    item.setEndDate(period.getEndDate());
                    item.setType(InvoiceItemType.PAYMENT);
                    setTotalAndTax(item, paymentPeriod);
                    return item;
                })
                .collect(Collectors.toSet()));
        return items;
    }

    private Stream<Period> limitPeriods(Stream<Period> unlimited, LocalDate correctBefore) {
        LocalDate lastDay = correctBefore.minusDays(1);
        return unlimited.filter(period -> period.getStartDate().isBefore(correctBefore))
                .map(period -> {
                    // Limit periods
                    if (period.getEndDate().isAfter(lastDay)) {
                        period = new Period(period.getStartDate(), lastDay);
                    }

                    return period;
                });
    }

    /**
     * Calculate the amount to pay in a given period
     *
     * @param invoiceItem Item to calculate for
     * @param totalPeriod The payment period in months.
     *
     * @return The amount to pay or repay
     */
    public BigDecimal setTotalAndTax(InvoiceItem invoiceItem, int totalPeriod) {
        return setTotalAndTax(invoiceItem, new BigDecimal(totalPeriod), false);
    }

    /**
     * Calculate the amount to pay in a given period
     *
     * @param invoiceItem Item to calculate for
     * @param totalPeriod The standard payment period in months.
     * @param negate      Negate the total.
     *
     * @return The amount to pay or repay
     */
    public BigDecimal setTotalAndTax(InvoiceItem invoiceItem, int totalPeriod, boolean negate) {
        return setTotalAndTax(invoiceItem, new BigDecimal(totalPeriod), negate);
    }

    /**
     * Calculate the amount to pay in a given period
     *
     * @param invoiceItem Item to calculate for
     * @param totalPeriod The standard payment period in months.
     * @param negate      Negate the total.
     *
     * @return The amount to pay or repay
     */
    public BigDecimal setTotalAndTax(InvoiceItem invoiceItem, BigDecimal totalPeriod, boolean negate) {
        Contract contract = invoiceItem.getContract();
        Tax tax = daoContext.getTaxDao()
                .findDistinctByVehicleTypeAndInsuranceType(
                        contract.getFleetSubscription().getVehicle().getType(),
                        contract.getInsuranceType());

        BigDecimal dayMultiplier = getDayMultiplier(invoiceItem, totalPeriod);

        // TODO: commission
        BigDecimal commissionPercentage = BigDecimal.ONE; //.add(BigDecimal.valueOf(commission));
        BigDecimal premium = BigDecimal.valueOf(contract.getNettoPremium());
        BigDecimal total = premium.multiply(commissionPercentage)
                .multiply(dayMultiplier);
        if (negate) {
            total = total.negate();
        }
        // BigDecimal taxCost = total.multiply(taxPercentage);
        invoiceItem.setTax(tax.getTax());
        //total = total.add(taxCost);
        invoiceItem.setAmount(total);
        return total;
    }

    /**
     * Get the multiplier for the amount, based on the period.
     *
     * @param invoiceItem The invoice item.
     * @param totalPeriod The payment period in months.
     *
     * @return A multiplier.
     */
    private BigDecimal getDayMultiplier(InvoiceItem invoiceItem, BigDecimal totalPeriod) {
        long itemPeriod;
        if (invoiceItem.getStartDate().equals(invoiceItem.getEndDate())) {
            itemPeriod = 1;
        } else {
            itemPeriod = ChronoUnit.DAYS.between(invoiceItem.getStartDate(), invoiceItem.getEndDate());
        }
        long totalPeriodInDays = ChronoUnit.DAYS.between(invoiceItem.getStartDate(),
                invoiceItem.getStartDate().plusMonths(totalPeriod.longValue()));

        return new BigDecimal(itemPeriod)
                .divide(new BigDecimal(totalPeriodInDays), PERCENTAGE_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    private Pair<List<Period>, List<Period>> calculateCorrections(List<Period> paidPeriods, Period periodToPay) {
        List<Period> overPaid = new ArrayList<>(); // Shallow copy for modification
        for (Period period : paidPeriods) {
            if (period.getEndDate().isBefore(periodToPay.getStartDate())
                    || period.getStartDate().isAfter(periodToPay.getEndDate())) {
                // Period falls before or after period to pay for, so is entirely overpaid
                overPaid.add(period);
                continue;
            }


            if (period.getEndDate().isAfter(periodToPay.getEndDate())) {
                LocalDate start;
                if (period.getStartDate().isAfter(periodToPay.getStartDate())) {
                    start = period.getStartDate();
                } else {
                    start = periodToPay.getEndDate().plusDays(1);
                }

                overPaid.add(new Period(start, period.getEndDate()));
            }


            if (period.getStartDate().isBefore(periodToPay.getStartDate())) {
                LocalDate end;
                if (period.getEndDate().isBefore(periodToPay.getStartDate())) {
                    end = period.getEndDate();
                } else {
                    end = periodToPay.getStartDate().minusDays(1);
                }

                overPaid.add(new Period(period.getStartDate(), end));
            }
        }

        List<Period> toPay = new ArrayList<>();

        // Sort to be sure
        paidPeriods.sort(Comparator.comparing(Period::getStartDate));

        for (Period period : paidPeriods) {
            if (period.getEndDate().isBefore(periodToPay.getStartDate())) {
                // Period too early, keep going
                continue;
            }

            if (period.getStartDate().isAfter(periodToPay.getEndDate())) {
                break; // Periods are sorted, no need to look further
            }

            // We are now sure this is some overlap
            if (period.getStartDate().equals(periodToPay.getStartDate())) {
                if (period.getEndDate().isAfter(periodToPay.getEndDate())
                        || period.getEndDate().equals(periodToPay.getEndDate())) {
                    // Fully paid
                    periodToPay = null;
                    break;
                }

                periodToPay = new Period(period.getEndDate().plusDays(1), periodToPay.getEndDate());
            } else if (period.getStartDate().isAfter(periodToPay.getStartDate())) {
                // Costs for begin of this period aren't covered
                toPay.add(new Period(periodToPay.getStartDate(), period.getStartDate().minusDays(1)));

                if (period.getEndDate().isBefore(periodToPay.getEndDate())) {
                    // We're still looking for payments for the rest of this period
                    periodToPay = new Period(period.getEndDate().plusDays(1), periodToPay.getEndDate());
                } else {
                    // The rest of the period is already paid for
                    periodToPay = null;
                    break;
                }
            } else if (period.getEndDate().isBefore(periodToPay.getEndDate())) {
                // Paid for first part of period, check for next in next iterations
                periodToPay = new Period(period.getEndDate().plusDays(1), periodToPay.getEndDate());
            } else {
                periodToPay = null; // We overpaid this period, but this is already calculated
                break;
            }
        }

        if (periodToPay != null) {
            toPay.add(periodToPay);
        }

        return Pair.of(overPaid, toPay);
    }
}