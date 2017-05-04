package solvas.service.invoices;

import org.springframework.data.util.Pair;
import solvas.service.models.Contract;
import solvas.service.models.InvoiceItem;
import solvas.service.models.InvoiceItemType;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceCorrector {

    public static Set<InvoiceItem> correctionItemsForContract(Contract contract) {
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
        System.out.println(corrections.getFirst());
        System.out.println(corrections.getSecond());
        System.out.println("a");
        Set<InvoiceItem> items = corrections.getFirst().stream()
                .map(period -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setContract(contract);
                    item.setStartDate(period.getStartDate());
                    item.setEndDate(period.getEndDate());
                    item.setType(InvoiceItemType.PAYMENT);
                    return item;
                })
                .collect(Collectors.toSet());
        items.addAll(corrections.getSecond().stream()
                .map(period -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setContract(contract);
                    item.setStartDate(period.getStartDate());
                    item.setEndDate(period.getEndDate());
                    item.setType(InvoiceItemType.REPAYMENT);
                    return item;
                })
                .collect(Collectors.toSet()));
        System.out.println(items);
        System.out.println(items.size());
        return items;
    }

    public static List<Period> merge(Collection<Period> paidPeriods,
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
        payments.addAll(repaidPeriods);

        List<Period> periods = new ArrayList<>();

        while (repayments.size() > 0) {
            if (payments.size() == 0) {
                throw new InvalidInvoiceItems("Day with more repayments than payments");
            }

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
                    // payment ends before repayment starts, not sure if this can happen but it doesn't break the algorithm
                    periods.add(payment);
                } else {
                    // Split in the part that won't be cancelled out and the part that will be cancelled out on the next iteration
                    periods.add(new Period(payment.getStartDate(), repayment.getStartDate().minusDays(1)));
                    payments.add(new Period(repayment.getStartDate(), payment.getEndDate()));
                    repayments.add(repayment);
                }
            }

        }

        periods.addAll(paidPeriods);
        periods.sort(Comparator.comparing(Period::getStartDate)
                .thenComparing(Period::getEndDate));
        // Merge together consecutive periods
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

    public static Pair<List<Period>, List<Period>> calculateCorrections(List<Period> paidPeriods, Period periodToPay) {
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
                        || period.getEndDate().equals(periodToPay.getStartDate())) {
                    // Fully paid
                    periodToPay = null;
                    break;
                }

                periodToPay = new Period(period.getEndDate().plusDays(1), periodToPay.getEndDate());
            }

            if (period.getStartDate().isAfter(periodToPay.getStartDate())) {
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
                // This should never happen, this is a security measure so a possible bug doesn't go undetected
                throw new RuntimeException("A bug occured");
            }
        }

        if (periodToPay != null) {
            toPay.add(periodToPay);
        }

        return Pair.of(overPaid, toPay);
    }
}
