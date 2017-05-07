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
import solvas.service.invoices.InvoiceCorrector;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for invoices
 */
@Service
public class InvoiceService extends AbstractService<Invoice, ApiInvoice> {

    //Dao context
    protected DaoContext context;

    private final InvoiceCorrector invoiceCorrector;

    /**
     * Construct an abstract service
     *
     * @param context the DAO context
     * @param mapper  the mapper between the api model and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper, InvoiceCorrector invoiceCorrector) {
        super(context.getInvoiceDao(), mapper);
        this.context = context;
        this.invoiceCorrector = invoiceCorrector;
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

            // Definitely not just swallowing the exception!
            throw new RuntimeException(e);
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

            // Definitely not just swallowing the exception!
            throw new RuntimeException(e);
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
                invoice = findCurrentInvoice(fleet);
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
    public Invoice findCurrentInvoice(int fleetId) throws EntityNotFoundException {
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
    public Invoice findCurrentInvoice(Fleet fleet) {
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
    private Invoice generateCurrentBillingInvoice(Fleet fleet) {
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
     *
     * @param invoice The invoice.
     *
     * @return The extended invoice.
     */
    public Invoice generateInvoiceForView(Invoice invoice) {
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
    private Invoice generatePaymentInvoice(Invoice invoice) {

        // We need all active contracts on the start date. This includes contracts that start or end on this day,
        // since contract payments start per running day.
        LocalDateTime startLimit = invoice.getStartDate().plusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endLimit = invoice.getStartDate().toLocalDate().atStartOfDay();

        Collection<Contract> contracts = context.getContractDao()
                .findByFleetSubscriptionFleetAndStartDateBeforeAndEndDateAfter(invoice.getFleet(), startLimit, endLimit);

        Set<InvoiceItem> items = contracts.stream()
                .map(contract -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setInvoice(invoice);
                    item.setStartDate(invoice.getStartDate().toLocalDate());
                    if(contract.getEndDate() != null && contract.getEndDate().isBefore(invoice.getEndDate())) {
                        item.setEndDate(contract.getEndDate().toLocalDate());
                    } else {
                        item.setEndDate(invoice.getEndDate().toLocalDate());
                    }
                    item.setAmount(invoiceCorrector.calculateTotal(item, invoice.getFleet().getFacturationPeriod()));
                    item.setType(InvoiceItemType.PAYMENT);
                    item.setContract(contract);
                    return item;
                }).collect(Collectors.toSet());
        invoice.setItems(items);
        return invoice;
    }

    /**
     * Generate a billing invoice. This contains much more data than a regular {@link Invoice}, so it is useful
     * to make PDF's.
     *
     * @param invoice The invoice.
     *
     * @return The invoice with lots of data.
     */
    private Invoice generateBillingInvoice(Invoice invoice) {

        // Unlike the payment invoices, for the billing invoices we need all contracts that have been active
        // (no matter how short) in the periode of the invoice.
        LocalDateTime startLimit = invoice.getStartDate().toLocalDate().atStartOfDay();
        LocalDateTime endLimit = invoice.getEndDate().plusDays(1).toLocalDate().atStartOfDay();

        // We need all contracts that were not active on the first day, but started in the period of the invoice.
        // This also contains the contracts that start after the start date, but end before the end date.
        Collection<Contract> started = context.getContractDao()
                .findByFleetSubscriptionFleetAndStartDateAfterAndStartDateLessThanEqual(invoice.getFleet(), startLimit.plusDays(1), endLimit);

        Set<InvoiceItem> items = started.stream()
                .map(contract -> {
                    InvoiceItem item = new InvoiceItem();
                    item.setInvoice(invoice);
                    item.setStartDate(contract.getStartDate().toLocalDate());
                    if(contract.getEndDate() != null && contract.getEndDate().isBefore(invoice.getEndDate())) {
                        item.setEndDate(contract.getEndDate().toLocalDate());
                    } else {
                        item.setEndDate(invoice.getEndDate().toLocalDate());
                    }
                    item.setType(InvoiceItemType.PAYMENT);
                    item.setAmount(invoiceCorrector.calculateTotal(item, invoice.getFleet().getFacturationPeriod()));
                    item.setContract(contract);
                    return item;
                }).collect(Collectors.toSet());
        invoice.setItems(items);
        return invoice;
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
        // Start with last calculated invoice.
        for (LocalDate lastDate = getLatestGeneratedInvoice(fleet, InvoiceType.BILLING);
             lastDate.isBefore(LocalDate.now().minusMonths(fleet.getFacturationPeriod()));
             lastDate = lastDate.plusMonths(fleet.getFacturationPeriod()))
        {
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

    /**
     * Generate and save missing payment invoices.
     *
     * @param fleet The fleet to generate payments for.
     */
    private void generateMissingPaymentsFor(Fleet fleet) {
        // Start with last calculated invoice
        for (LocalDate lastDate = getLatestGeneratedInvoice(fleet, InvoiceType.PAYMENT);
             lastDate.isBefore(LocalDate.now()) || lastDate.equals(LocalDate.now());
             lastDate = lastDate.plusMonths(fleet.getPaymentPeriod()))
        {
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
     *
     * @param fleetId Id of the fleet to generate correction for
     * @return True of any corrections were generated
     */
    public boolean generateCorrectionsFor(int fleetId) throws EntityNotFoundException {
        return generateCorrectionsFor(context.getFleetDao().find(fleetId));
    }

    /**
     *
     * @param fleet Fleet to generate correction for
     * @return True of any corrections were generated
     */
    public boolean generateCorrectionsFor(Fleet fleet) {
        LocalDate lastDate = getLatestGeneratedInvoice(fleet, InvoiceType.BILLING);
        Set<InvoiceItem> corrections = fleet.getSubscriptions().stream()
                .map(FleetSubscription::getContracts)
                .flatMap(Set::stream)
                .map(contract -> invoiceCorrector.correctionItemsForContract(
                        contract,
                        lastDate,
                        fleet.getFacturationPeriod()))
                .flatMap(Set::stream)
                // Don't correct irregularities that have no billing invoice yet
                .filter(item -> item.getStartDate().isBefore(lastDate))
                .map(item -> {
                    if(! item.getEndDate().isBefore(lastDate)) {
                        // The check in filter will guarantee that endDate is still after or equal to startDate
                        item.setEndDate(lastDate.minusDays(1));
                    }
                    return item;
                })
                .collect(Collectors.toSet());

        if(corrections.isEmpty()) {
            return false;
        }
        LocalDate firstCorrection = corrections.iterator().next().getStartDate();
        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.CORRECTION);
        invoice.setStartDate(firstCorrection.atStartOfDay());
        invoice.setEndDate(LocalDateTime.now());
        invoice.setFleet(fleet);
        invoice.setPaid(false);
        invoice.setItems(corrections);
        corrections.forEach(item -> item.setInvoice(invoice));
        modelDao.save(invoice);

        return true;
    }
}