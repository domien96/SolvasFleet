
package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Filters for a {@link Commission}.
 *
 * @author sjabasti
 * @author domien
 */
public class CommissionFilter implements Filter<Commission> {

    private int company=-1;
    private String insuranceType= null;
    private int fleet=-1;
    private int vehicle=-1;
    private String vehicleType= null;


    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Commission> root) {
        Collection<Predicate> predicates= new ArrayList<>();

        if (insuranceType != null) {
            Join<Commission,InsuranceType> join = root.join("insuranceType");
            predicates.add(builder.equal(
                    builder.lower(join.get("name")),
                    insuranceType.toLowerCase()
            ));
        }
        // From most specific criterium to most general
        if (vehicle >0) {
            predicates.add(builder.equal(
                    root.get("vehicle"),
                    vehicle
            ));
            //return predicates;
        } else { // to avoid multiple results
            predicates.add(builder.isNull(
                    root.get("vehicle")
            ));
        }

        if (fleet > 0) {
            predicates.add(builder.equal(
                    root.get("fleet"),
                    fleet
            ));
            //return predicates;
        } else { // to avoid multiple results when we fill in the company field (a company can be linked to multiple fleets)
            predicates.add(builder.isNull(
                    root.get("fleet")
            ));
        }

        if (company > 0){
            predicates.add(builder.equal(
                    root.get("company"),
                    company
            ));

        }

        if (vehicleType != null) {
            Join<Commission,VehicleType> joinVehicleType = root.join("vehicleType");
            predicates.add(builder.equal(
                    builder.lower(joinVehicleType.get("name")),
                    vehicleType.toLowerCase()
            ));
        } else { // to avoid multiple results when we fill in the fleet field (a fleet can be linked to multiple vehicletypes)
            predicates.add(builder.isNull(
                    root.get("vehicleType")
            ));
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