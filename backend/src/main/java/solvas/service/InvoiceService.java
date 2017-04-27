package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.query.InvoiceFilter;
import solvas.service.invoices.Cost;
import solvas.service.invoices.PaymentInvoice;
import solvas.service.invoices.billing.AddCorrection;
import solvas.service.invoices.billing.BillingInvoice;
import solvas.service.invoices.billing.RemoveCorrection;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for invoices
 */
@Service
public class InvoiceService extends AbstractService<Invoice, ApiInvoice> {

    //Dao context
    protected DaoContext context;

    /**
     * Construct an abstract service
     *
     * @param context the DAO context
     * @param mapper  the mapper between the api model and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper) {
        super(context.getInvoiceDao(), mapper);
        this.context = context;
    }


    /**
     * This method is not supported by both the API and the backend for invoice.
     *
     * @param pagination param
     * @param filters param
     * @return nothing.
     */
    @Override
    public Page<ApiInvoice> findAll(Pageable pagination, Filter<Invoice> filters) {
        InvoiceFilter f = (InvoiceFilter) filters;
        try {
            generateMissingInvoices(f.getFleet());
        } catch (EntityNotFoundException e) {
            // Todo what here?
        }
        return super.findAll(pagination, filters);
    }

    /**
     * This method is not supported by both the API and the backend for invoice.
     *
     * @param filters param
     * @return nothing.
     */
    @Override
    public List<ApiInvoice> findAll(Filter<Invoice> filters) {
        InvoiceFilter f = (InvoiceFilter) filters;
        try {
            generateMissingInvoices(f.getFleet());
        } catch (EntityNotFoundException e) {
            // Todo what here?
        }
        return super.findAll(filters);
    }

    /**
     * Finds all types of insurance in the database
     *
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return context.getInsuranceTypeDao().findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());
    }

    /**
     * Find the current invoice for a fleet and a type.
     *
     * @param fleetId The ID of the fleet.
     * @param type    The type of the invoice.
     *
     * @return The invoice.
     *
     * @throws EntityNotFoundException If the fleet was not found.
     */
    public ApiInvoice findActiveInvoiceByType(int fleetId, InvoiceType type) throws EntityNotFoundException {
        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
        //the method not found in AbstractController
        generateMissingInvoices(fleet);
        // If the type is payment, just show the latest payment, as this is useless.
        Invoice invoice;
        switch (type) {
            case PAYMENT:
                invoice = context.getInvoiceDao()
                        .findFirstByTypeAndFleetOrderByStartDateDesc(InvoiceType.PAYMENT, fleet)
                        .orElseThrow(() -> new IllegalStateException("There is no payment billing, which should be impossible."));
                break;
            case BILLING:
                invoice = findCurrentInvoice(fleet).getInvoice();
                // Calculate the billing for the current period (up until now).
                break;
            default:
                throw new RuntimeException("Non-exhaustive enum switch");

        }
        return mapper.convertToApiModel(invoice);
    }

    /**
     * Find the extended current billing invoice.
     *
     * @param fleetId The ID of the fleet.
     *
     * @return The billing invoice.
     *
     * @throws EntityNotFoundException If the fleet was not found.
     */
    public BillingInvoice findCurrentInvoice(int fleetId) throws EntityNotFoundException {
        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        return findCurrentInvoice(fleet);
    }

    /**
     * Find the extended current billing invoice.
     *
     * @param fleet The fleet.
     *
     * @return The billing invoice.
     */
    public BillingInvoice findCurrentInvoice(Fleet fleet) {
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
        //the method not found in AbstractController
        generateMissingInvoices(fleet);
        return generateCurrentBillingInvoice(fleet);
    }

    /**
     * Generate all missing invoices for a fleet.
     *
     * @param fleetId The ID of the fleet.
     *
     * @throws EntityNotFoundException If the fleet could not be found.
     */
    private void generateMissingInvoices(int fleetId) throws EntityNotFoundException {
        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
        //the method not found in AbstractController
        generateMissingInvoices(fleet);
    }

    /**
     * Generate invoice with type billing. If the new invoice period has not yet been completed
     * then the new invoice will not be saved. With other words, if the next invoice is equal to the current
     * running invoice, then nothing will be saved.
     * A period is considered not completed if the enddate is before <u>or equal</u> to now.
     *
     * @param fleet the fleet
     *
     * @return newly generated invoice
     */
    private BillingInvoice generateCurrentBillingInvoice(Fleet fleet) {
        LocalDate startDate = getLatestGeneratedInvoice(fleet, InvoiceType.BILLING);
        LocalDateTime now = LocalDateTime.now();

        LocalDate endDate = startDate.plusMonths(fleet.getFacturationPeriod()).minusDays(1);
        Invoice invoice = new Invoice();
        invoice.setStartDate(startDate.atStartOfDay());
        invoice.setEndDate(endDate.atStartOfDay());
        invoice.setPaid(false);
        invoice.setFleet(fleet);
        invoice.setType(InvoiceType.BILLING);
        invoice.setCreatedAt(now);
        invoice.setUpdatedAt(now);

        // This will generate the total and put it in the invoice.
        return generateBillingInvoice(invoice);
    }

    /**
     * Generate all missing invoices (both types).
     *
     * @param fleet Fleet for which the invoices have to be calculated.
     */
    private void generateMissingInvoices(Fleet fleet) {
        // We first generate the payment types, as the billing types depend on those.
        generateMissingPaymentsFor(fleet);

        // Secondly, we generate the billing invoices.
        generateMissingBillingsFor(fleet);
    }

    /**
     * Generate and save missing payment invoices.
     *
     * @param fleet The fleet to generate payments for.
     */
    private void generateMissingPaymentsFor(Fleet fleet) {
        // Get the last calculated invoice
        LocalDate lastDate = getLatestGeneratedInvoice(fleet, InvoiceType.PAYMENT);

        for (; lastDate.isBefore(LocalDate.now()); lastDate = lastDate.plusMonths(fleet.getPaymentPeriod())) {
            // Generate an invoice.
            Invoice invoice = new Invoice();
            invoice.setType(InvoiceType.PAYMENT);
            invoice.setStartDate(lastDate.atStartOfDay());
            invoice.setEndDate(lastDate.plusMonths(fleet.getPaymentPeriod()).minusDays(1).atStartOfDay());
            invoice.setFleet(fleet);
            invoice.setPaid(false);
            // Set the total on the invoice.
            generatePaymentInvoice(invoice);
            modelDao.save(invoice);
        }
    }

    /**
     * Get the newest generated (and saved) invoice of a certain type.
     *
     * @param fleet The fleet.
     * @param type  The type.
     *
     * @return The date of the newest invoice.
     */
    private LocalDate getLatestGeneratedInvoice(Fleet fleet, InvoiceType type) {
        return context.getInvoiceDao()
                .findFirstByTypeAndFleetOrderByStartDateDesc(type, fleet)
                .map(invoice -> invoice.getEndDate().plusDays(1))
                .orElseGet(() -> context.getContractDao()
                        .findFirstByFleetSubscriptionFleetOrderByStartDateAsc(fleet)
                        .map(Contract::getStartDate)
                        .orElse(fleet.getCreatedAt()))
                .toLocalDate();
    }

    /**
     * Generate an extended invoice, based on the type of the given invoice.
     * <p>
     * TODO: make this better, without object.
     *
     * @param invoice The invoice.
     *
     * @return The extended invoice.
     */
    public Object generateInvoiceForView(Invoice invoice) {
        switch (invoice.getType()) {
            case PAYMENT:
                return generatePaymentInvoice(invoice);
            case BILLING:
                return generateBillingInvoice(invoice);
            default:
                throw new RuntimeException("Non-exhaustive enum switch!");
        }
    }

    /**
     * Generate a payment invoice. This contains much more data than a regular {@link Invoice}, so it is useful
     * to make PDF's.
     *
     * @param invoice The invoice to generate for.
     *
     * @return The invoice with lot's of data.
     */
    private PaymentInvoice generatePaymentInvoice(Invoice invoice) {

        // We need all active contracts on the start date. This includes contracts that start or end on this day,
        // since contract payments start per running day.
        LocalDateTime startLimit = invoice.getStartDate().plusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endLimit = invoice.getStartDate().toLocalDate().atStartOfDay();

        Collection<Contract> contracts = context.getContractDao()
                .findByFleetSubscriptionFleetAndStartDateBeforeAndEndDateAfter(invoice.getFleet(), startLimit, endLimit);

        Collection<Cost> costs = contracts.stream()
                .map(contract -> {
                    Cost cost = new Cost();
                    cost.setContract(contract);
                    cost.setTax(context.getTaxDao().findDistinctByVehicleTypeAndInsuranceType(contract.getFleetSubscription().getVehicle().getType(), contract.getInsuranceType()));
                    return cost;
                }).collect(Collectors.toList());

        PaymentInvoice paymentInvoice = new PaymentInvoice(invoice);
        paymentInvoice.addCosts(costs);
        return paymentInvoice;
    }

    /**
     * Generate a billing invoice. This contains much more data than a regular {@link Invoice}, so it is useful
     * to make PDF's.
     *
     * @param invoice The invoice.
     *
     * @return The invoice with lots of data.
     */
    private BillingInvoice generateBillingInvoice(Invoice invoice) {

        // Unlike the payment invoices, for the billing invoices we need all contracts that have been active
        // (no matter how short) in the periode of the invoice.
        LocalDateTime startLimit = invoice.getStartDate().toLocalDate().atStartOfDay();
        LocalDateTime endLimit = invoice.getEndDate().plusDays(1).toLocalDate().atStartOfDay();

        // We need all contracts that were active on the first day, but ended inside the period of the invoice.
        Collection<Contract> ended = context.getContractDao()
                .findFleetAndStartDateBeforeAndEndDateBetween(invoice.getFleet(), startLimit, endLimit);

        // We need all contracts that were not active on the first day, but started in the period of the invoice.
        // This also contains the contracts that start after the start date, but end before the end date.
        Collection<Contract> started = context.getContractDao()
                .findByFleetSubscriptionFleetAndStartDateAfterAndStartDateLessThanEqual(invoice.getFleet(), startLimit.plusDays(1), endLimit);

        Collection<RemoveCorrection> removeCorrections = ended.stream()
                .map(contract -> {
                    RemoveCorrection correction = new RemoveCorrection();
                    correction.setContract(contract);
                    correction.setTax(context.getTaxDao().findDistinctByVehicleTypeAndInsuranceType(contract.getFleetSubscription().getVehicle().getType(), contract.getInsuranceType()));
                    correction.setDays(numberOfDaysRemoved(contract, invoice));
                    return correction;
                })
                .collect(Collectors.toList());

        Collection<AddCorrection> addCorrections = started.stream()
                .map(contract -> {
                    AddCorrection correction = new AddCorrection();
                    correction.setContract(contract);
                    correction.setTax(context.getTaxDao().findDistinctByVehicleTypeAndInsuranceType(contract.getFleetSubscription().getVehicle().getType(), contract.getInsuranceType()));
                    correction.setDays(numberOfDaysAdded(contract, invoice));
                    return correction;
                })
                .collect(Collectors.toList());

        BillingInvoice billingInvoice = new BillingInvoice(invoice);
        billingInvoice.addCosts(removeCorrections);
        billingInvoice.addCosts(addCorrections);

        // Since it is useful to know what we are billing for, add the payment invoices from the same period.
        Collection<Invoice> invoices = context.getInvoiceDao()
                .findByTypeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(InvoiceType.PAYMENT, invoice.getStartDate(), invoice.getEndDate());
        billingInvoice.setPayments(invoices);

        return billingInvoice;
    }

    /**
     * The number of days for the addition.
     */
    private long numberOfDaysAdded(Contract contract, Invoice invoice) {
        long totalDays = ChronoUnit.DAYS.between(invoice.getStartDate(), invoice.getEndDate());
        long skippedDays = ChronoUnit.DAYS.between(invoice.getStartDate(), contract.getStartDate());
        return totalDays - skippedDays;
    }

    /**
     * The number of days for the removal.
     */
    private long numberOfDaysRemoved(Contract contract, Invoice invoice) {
        long totalDays = ChronoUnit.DAYS.between(invoice.getStartDate(), invoice.getEndDate());
        long skippedDays = ChronoUnit.DAYS.between(invoice.getStartDate(), contract.getEndDate());
        return totalDays - skippedDays;
    }

    /**
     * Generate missing billing invoices.
     *
     * @param fleet The fleet.
     */
    private void generateMissingBillingsFor(Fleet fleet) {
        // Get the last calculated invoice
        LocalDate lastDate = getLatestGeneratedInvoice(fleet, InvoiceType.BILLING);

        for (; lastDate.isBefore(LocalDate.now().minusMonths(fleet.getFacturationPeriod())); lastDate = lastDate.plusMonths(fleet.getFacturationPeriod())) {
            // Generate an invoice.
            Invoice invoice = new Invoice();
            invoice.setType(InvoiceType.BILLING);
            invoice.setStartDate(lastDate.atStartOfDay());
            invoice.setEndDate(lastDate.plusMonths(fleet.getFacturationPeriod()).minusDays(1).atStartOfDay());
            invoice.setFleet(fleet);
            invoice.setPaid(false);
            // Set the total on the invoice.
            generateBillingInvoice(invoice);
            modelDao.save(invoice);
        }
    }
}