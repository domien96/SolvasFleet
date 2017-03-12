package solvas.rest.query;

import solvas.models.Vehicle;
import solvas.models.Vehicletype;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@SuppressWarnings("unused")
public class VehicleFilter implements Filterable<Vehicle> {

    private String chassisNumber;
    private int leasingCompany = -1;
    private String licensePlate;
    private String type;
    private int year = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Vehicle> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (chassisNumber != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("chassisNumber")),
                    chassisNumber.toLowerCase()
            ));
        }
        if (leasingCompany >= 0) {
            predicates.add(builder.equal(root.get("leasingCompany"), leasingCompany));
        }
        if (year >= 0) {
            predicates.add(builder.equal(root.get("year"), year));
        }
        if (licensePlate != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("licensePlate")),
                    licensePlate.toLowerCase()
            ));
        }
        if (type != null) {
            Join<Vehicle, Vehicletype> join = root.join("type");
            predicates.add(builder.equal(
                    builder.lower(join.get("name")),
                    type.toLowerCase()
            ));
        }

        return predicates;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public void setLeasingCompany(int leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }
}