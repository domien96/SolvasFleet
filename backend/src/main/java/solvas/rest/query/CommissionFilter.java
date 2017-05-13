
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
    private String insuranceType=null;
    private int fleet=-1;
    private int vehicle=-1;
    private String vehicleType=null;


    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Commission> root) {
        Collection<Predicate> predicates= new HashSet<>();

        predicates.add(builder.equal(
                builder.lower(root.get("vehicleType")),
                insuranceType!=null?insuranceType.toLowerCase():null
        )); // if
        if (vehicle >0) {
            predicates.add(builder.equal(
                    builder.lower(root.get("vehicle")),
                    vehicle
            ));
        } else {
            if (fleet > 0) {
                predicates.add(builder.equal(
                        builder.lower(root.get("fleet")),
                        fleet
                ));
                if (vehicleType != null) {
                    predicates.add(builder.equal(
                            builder.lower(root.get("insuranceType")),
                            vehicleType.toLowerCase()
                    ));
                }

            } else {
                if (company > 0){
                    predicates.add(builder.equal(
                            builder.lower(root.get("insuranceType")),
                            company
                    ));

                }


            }
        }








        return predicates;
    }


    public void setCompany(int company) {
        this.company = company;
    }

    public void setInsuranceType(String insuranceType) {
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