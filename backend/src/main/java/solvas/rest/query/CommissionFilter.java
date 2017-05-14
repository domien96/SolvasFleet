
package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.Commission;
import solvas.service.models.Company;
import solvas.service.models.CompanyType;
import solvas.service.models.Permission;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;

/**
 * Filters for a {@link Commission}.
 *
 * @author sjabasti
 */
public class CommissionFilter implements Filter<Commission> {

    private int company=-1;
    private int insuranceType=-1;
    private int fleet=-1;
    private int vehicle=-1;
    private int vehicleType=-1;


    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Commission> root) {
        Collection<Predicate> predicates= new HashSet<>();

        predicates.add(builder.equal(
                (root.get("insuranceType")),
                insuranceType!=-1?insuranceType:null
        ));
        // From most specific criterium to most general
        if (vehicle >0) {
            predicates.add(builder.equal(
                    root.get("vehicle"),
                    vehicle
            ));
            return predicates;
        }
        if (fleet > 0) {
            predicates.add(builder.equal(
                    root.get("fleet"),
                    fleet
            ));
            if (vehicleType > 0) {
                predicates.add(builder.equal(
                        (root.get("vehicleType")),
                        vehicleType
                ));
            }
            return predicates;
        }
        if (company > 0){
            predicates.add(builder.equal(
                    root.get("company"),
                    company
            ));

        }
        return predicates;
    }


    public void setCompany(int company) {
        this.company = company;
    }

    public void setInsuranceType(int insuranceType) {
        this.insuranceType = insuranceType;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}