package service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.ContractDao;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.persistence.api.dao.TaxDao;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.CommissionService;
import solvas.service.InvoiceService;
import solvas.service.invoices.InvoiceCorrector;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test the invoice service.
 */
public class InvoiceServiceTest extends AbstractServiceTest<Invoice, ApiInvoice> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private InvoiceDao invoiceDao;
    private InvoiceCorrector invoiceCorrector;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private TaxDao taxDao;
    @Mock
    private ContractDao contractDao;
    @Mock
    private CommissionService commissionService;
    /**
     * Construct the test.
     */
    public InvoiceServiceTest() {
        super(Invoice.class, ApiInvoice.class);
    }

    @Before
    @Override
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getInvoiceDao()).thenReturn(invoiceDao);
        when(daoContextMock.getContractDao()).thenReturn(contractDao);
        when(daoContextMock.getTaxDao()).thenReturn(taxDao);

        invoiceCorrector = new InvoiceCorrector(daoContextMock, commissionService);
    }

    @Override
    protected InvoiceService getService() {
        return new InvoiceService(daoContextMock, invoiceMapper, invoiceCorrector);
    }

    @Override
    protected Dao<Invoice> getDaoMock() {
        return invoiceDao;
    }

    @Override
    protected AbstractMapper<Invoice, ApiInvoice> getMapperMock() {
        return invoiceMapper;
    }

    @Test
    public void canGenerateCorrections() throws EntityNotFoundException {
        Invoice lastInvoice = new Invoice();
        lastInvoice.setEndDate(LocalDateTime.now());
        when(commissionService.findAll(any(), any())).thenReturn(
                new PageImpl<>(new ArrayList<ApiCommission>() {{
                    ApiCommission commission = new ApiCommission();
                    commission.setValue(5);
                    add(commission);
                }})
        );
        when(invoiceDao.findFirstByTypeAndFleetOrderByStartDateDesc(any(), any())).thenReturn(Optional.of(lastInvoice));
        Fleet fleet = new Fleet();
        fleet.setSubscriptions(new HashSet<FleetSubscription>() {{
            FleetSubscription sub = new FleetSubscription();
            sub.setContracts( new HashSet<Contract>() {{
                Contract c = new Contract();
                c.setCompany(new Company());

                InsuranceType insuranceType = new InsuranceType();
                insuranceType.setName("LegalAid");


                c.setStartDate(LocalDateTime.now().minusMonths(8));
                c.setEndDate(LocalDateTime.now());
                FleetSubscription sub = new FleetSubscription();
                sub.setVehicle(new Vehicle());
                c.setFleetSubscription(sub);
                c.setInvoiceItems(new HashSet<InvoiceItem>() {{
                    InvoiceItem i = new InvoiceItem();
                    i.setStartDate(LocalDate.now().minusMonths(10));
                    i.setEndDate(LocalDate.now().minusMonths(5));
                    i.setType(InvoiceItemType.PAYMENT);

                    InvoiceItem i2 = new InvoiceItem();
                    i2.setStartDate(LocalDate.now().minusMonths(10));
                    i2.setEndDate(LocalDate.now().minusMonths(7));
                    i2.setType(InvoiceItemType.REPAYMENT);

                    InvoiceItem i3 = new InvoiceItem();
                    i3.setStartDate(LocalDate.now().minusMonths(11));
                    i3.setEndDate(LocalDate.now().minusMonths(10));
                    i3.setType(InvoiceItemType.PAYMENT);

                    add(i);
                    add(i2);
                    add(i3);
                }});

                Vehicle v = new Vehicle();
                VehicleType vehicleType = new VehicleType();
                vehicleType.setName("Van");
                v.setType(vehicleType);
                sub.setVehicle(v);
                sub.setFleet(fleet);
                c.setFleetSubscription(sub);
                c.setInsuranceType(insuranceType);
                add(c);
            }});
            add(sub);
        }});
        fleet.setPaymentPeriod(1);
        fleet.setFacturationPeriod(1);

        InvoiceItem i = new InvoiceItem();
        i.setEndDate(LocalDate.now());
        i.setStartDate(LocalDate.now().minusDays(5));
       /* when(invoiceCorrector.correctionItemsForContract(any(), any(), anyInt())).thenReturn(new HashSet<InvoiceItem>() {{
            add(i);
        }});*/
        when(taxDao.findDistinctByVehicleTypeAndInsuranceType(any(), any())).thenReturn(new Tax());


        getService().generateCorrectionsFor(fleet);
        verify(getDaoMock()).save(captor.capture());

        // 2 payments, one repayment
        assertThat(captor.getValue().getItems().size(),is(3));
    }

    @Test
    public void generateMissingInvoices() {
        Fleet fleet = new Fleet();
        fleet.setPaymentPeriod(1);
        fleet.setFacturationPeriod(1);
        Invoice lastInvoice = new Invoice();
        lastInvoice.setEndDate(LocalDateTime.now().minusMonths(5));
        when(commissionService.findAll(any(), any())).thenReturn(
                new PageImpl<>(new ArrayList<ApiCommission>() {{
                    ApiCommission commission = new ApiCommission();
                    commission.setValue(5);
                    add(commission);
                }})
        );
        when(taxDao.findDistinctByVehicleTypeAndInsuranceType(any(), any())).thenReturn(new Tax());
        when(invoiceDao.findFirstByTypeAndFleetOrderByStartDateDesc(any(), any())).thenReturn(Optional.of(lastInvoice));
        when(contractDao.findByFleetSubscriptionFleetAndStartDateBeforeAndEndDateAfter(any(), any(), any()))
                .thenReturn(new HashSet<Contract>() {{
                    Contract c = new Contract();
                    c.setEndDate(LocalDateTime.now());
                    c.setStartDate(LocalDateTime.now().minusMonths(3));
                    c.setCompany(new Company());

                    InsuranceType insuranceType = new InsuranceType();
                    insuranceType.setName("LegalAid");

                    FleetSubscription sub = new FleetSubscription();
                    Vehicle v = new Vehicle();
                    VehicleType vehicleType = new VehicleType();
                    vehicleType.setName("Van");
                    v.setType(vehicleType);
                    sub.setVehicle(v);
                    sub.setFleet(fleet);
                    c.setFleetSubscription(sub);
                    c.setInsuranceType(insuranceType);
                    add(c);
                }});


        getService().findCurrentInvoice(fleet);
        // 5 payments, 4 billing
        verify(getDaoMock(), times(9)).save(captor.capture());

    }
}
