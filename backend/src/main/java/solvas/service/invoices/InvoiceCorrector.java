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

@Component
public class InvoiceCorrector {
    private final DaoContext daoContext;
    private static final int PERCENTAGE_SCALE = 2;

    @Autowired
    public InvoiceCorrector(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    public Set<InvoiceItem> correctionItemsForContract(Contract contract, LocalDate correctBefore, int facturationPeriod) {
        Collection<InvoiceItem> invoiceItems = contract.getInvoiceItems();
        Collection<Period> paidPeriods = invoiceItems.stream()
                .filter(item -> item.getType().equals(InvoiceItemType.PAYMENT))
                .map(item -> new Period(item.getStartDate(), item.getEndDate()))
                .collect(Collectors.toList());

        Collection<Period> repaidPeriods = invoiceItems.stream()
                .filter(item -> item.getType().equals(InvoiceItemType.REPAYMENT))
                .map(item -> new Period(item.getStartDate(), item.getEndDate()))
                .collect(Collectors.toList());

        List<Period> positivePeriods = merge(paidPeriods, repaidPeriods);
        Period periodToPay = new Period(contract.getStartDate().toLocalDate(), contract.getEndDate().toLocalDate());
        Pair<List<Period>, List<Period>> corrections = calculateCorrections(positivePeriods, periodToPay);

        Set<InvoiceItem> items = limitPeriods(corrections.getFirst().stream(), correctBefore)
                .map(period -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setContract(contract);
                    item.setStartDate(period.getStartDate());
                    item.setEndDate(period.getEndDate());
                    item.setType(InvoiceItemType.REPAYMENT);
                    item.setAmount(calculateTotal(item, facturationPeriod).negate());//TODO
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
                    item.setAmount(calculateTotal(item, facturationPeriod));
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

    public BigDecimal calculateTotal(InvoiceItem invoiceItem, int totalPeriod) {
        return calculateTotal(invoiceItem, new BigDecimal(totalPeriod));
    }

    public BigDecimal calculateTotal(InvoiceItem invoiceItem, BigDecimal totalPeriod) {
        Contract contract = invoiceItem.getContract();
        Tax tax = daoContext.getTaxDao()
                .findDistinctByVehicleTypeAndInsuranceType(
                        contract.getFleetSubscription().getVehicle().getType(),
                        contract.getInsuranceType());
        BigDecimal taxPercentage = BigDecimal.ONE.add(tax.getTax());

        long itemPeriod = ChronoUnit.DAYS.between(invoiceItem.getStartDate(), invoiceItem.getEndDate());

        BigDecimal dayMultiplier = new BigDecimal(itemPeriod)
                .divide(totalPeriod, PERCENTAGE_SCALE, BigDecimal.ROUND_HALF_UP);


        // TODO: commission
        BigDecimal commissionPercentage = BigDecimal.ONE; //.add(BigDecimal.valueOf(commission));
        BigDecimal premium = BigDecimal.valueOf(contract.getPremium());
        return premium.multiply(commissionPercentage).multiply(taxPercentage).multiply(dayMultiplier);
    }

    public List<Period> merge(Collection<Period> paidPeriods,
                              Collection<Period> repaidPeriods) {
        // Using tree guarantees log(n) sorted insertion and log(n) popping of first element
        // Possible optimization would be a structure that can add to the front in constant time
        // However, this wouldn't change the complexity in big O-notation, and #lazy
        TreeSet<Period> payments = new TreeSet<>(
                Comparator.comparing(Period::getStartDate)
                        .thenComparing(Period::getEndDate)
        );
        payments.addAll(paidPeriods);
        TreeSet<Period> repayments = new TreeSet<>(
                Comparator.comparing(Period::getStartDate)
                        .thenComparing(Period::getEndDate)
        );
        repayments.addAll(repaidPeriods);

        List<Period> periods = new ArrayList<>();
        System.out.println("merged");
        System.out.println(payments);
        System.out.println(repayments);

        while (repayments.size() > 0) {
            if (payments.size() == 0) {
                throw new InvalidInvoiceItems("Day with more repayments than payments");
            }

            System.out.println("iteration");
            System.out.println(repayments);
            System.out.println(payments);
            Period payment = payments.pollFirst();
            Period repayment = repayments.pollFirst();
            if (repayment.getStartDate().isBefore(payment.getStartDate())) {
                throw new InvalidInvoiceItems("Day with more repayments than payments");
            }


            if (repayment.getStartDate().equals(payment.getStartDate())) {
                if (repayment.getEndDate().equals(payment.getEndDate())) {
                    continue; // Both cancel eachother out, discard them
                }
                if (repayment.getEndDate().isAfter(payment.getEndDate())) {
                    // repayment cancels out a part of the payment, split up and readd to repayments
                    repayments.add(new Period(payment.getEndDate().plusDays(1), repayment.getEndDate()));
                } else {
                    // payment cancels out a part of the repayment, split up and readd to payments
                    payments.add(new Period(repayment.getEndDate().plusDays(1), payment.getEndDate()));
                }
            } else {
                // First part of payment won't be cancelled out, so we are add it to the result collection
                if (payment.getEndDate().isBefore(repayment.getStartDate())) {
                    periods.add(payment);
                    repayments.add(repayment);
                } else {
                    // Split in the part that won't be cancelled out and the part that will be cancelled out on the next iteration
                    periods.add(new Period(payment.getStartDate(), repayment.getStartDate().minusDays(1)));
                    payments.add(new Period(repayment.getStartDate(), payment.getEndDate()));
                    repayments.add(repayment);
                }
            }

        }

        periods.addAll(payments);
        periods.sort(Comparator.comparing(Period::getStartDate)
                .thenComparing(Period::getEndDate));
        // Merge together consecutive periods
        System.out.println("pre-merge");
        System.out.println(periods);
        List<Period> mergedPeriods = new ArrayList<>();

        Period last = null;
        for (Period period : periods) {
            if (last == null) { // Only on first iteration
                last = period;
                continue;
            }

            if (last.getEndDate().plusDays(1).equals(period.getStartDate())) {
                // Consecutive periods, merge together
                last = new Period(last.getStartDate(), period.getEndDate());
            } else if (last.getEndDate().plusDays(1).isAfter(period.getStartDate())) {
                // At least one day contains 2 or more more payments than repayments.
                throw new InvalidInvoiceItems("Contains day with too many payments");
            } else {
                // non consecutive periods, add last to result and start anew with the rest of the periods
                mergedPeriods.add(last);
                last = period;
            }
        }

        if (last != null) {
            mergedPeriods.add(last); // Add last calculated period
        }

        return mergedPeriods;
    }

    public Pair<List<Period>, List<Period>> calculateCorrections(List<Period> paidPeriods, Period periodToPay) {
        List<Period> overPaid = new ArrayList<>(); // Shallow copy for modification
        System.out.println("paid");
        System.out.println(paidPeriods);
        System.out.println(periodToPay);
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
