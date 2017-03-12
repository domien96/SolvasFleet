package solvas.rest.query;

import solvas.models.Vehicle;
import solvas.models.VehicleType;
import solvas.persistence.Filter;

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
public class VehicleFilter implements Filter<Vehicle> {

    private String vin;
    private int leasingCompany = -1;
    private String licensePlate;
    private String type;
    private int year = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Vehicle> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (vin != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("vin")),
                    vin.toLowerCase()
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
            Join<Vehicle, VehicleType> join = root.join("type");
            predicates.add(builder.equal(
                    builder.lower(join.get("name")),
                    type.toLowerCase()
            ));
        }

        return predicates;
    }

    public void setVin(String vin) {
        this.vin = vin;
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