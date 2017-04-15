package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiContract;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.api.models.ApiModel;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.Contract;
import solvas.service.models.InsuranceType;
import solvas.service.models.Invoice;
import solvas.service.models.Model;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by steve on 15/04/2017.
 */
public class InvoiceService extends AbstractService<Invoice,ApiInvoice> {

    //Dao for generating a response for findAllInsuranceTypes
    private InsuranceTypeDao insuranceTypeDao;

    /**
     * Contruct an abstractservice
     *
     * @param context the DAO context
     * @param mapper   the mapper between the apimodel and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper) {
        super(context.getInvoiceDao(), mapper);
        insuranceTypeDao= context.getInsuranceTypeDao();
    }


    /**
     * Finds all types of insurance in the database
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return insuranceTypeDao.findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());

    }
}