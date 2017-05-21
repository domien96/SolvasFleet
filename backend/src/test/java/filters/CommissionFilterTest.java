package filters;


import org.junit.Ignore;
import solvas.persistence.api.Filter;
import solvas.rest.query.CommissionFilter;
import solvas.service.models.Commission;

public class CommissionFilterTest extends AbstractFilterTest<Commission> {
    @Override
    Filter<Commission> getFilterWithCorrectParameters() {
        CommissionFilter filter = new CommissionFilter();
        filter.setCompany(1);
        filter.setFleet(1);
        filter.setVehicle(1);
        filter.setInsuranceType("LegalAid");
        filter.setVehicleType("Truck");
        return filter;
    }

    @Override
    Filter<Commission> getFilterWithBadParameters() {
        return new CommissionFilter();
    }

    @Override
    int parameterSize() {
        return 5;
    }

    @Override
    protected int emptyFilterParameterSize() {
        return 3;
    }

}
