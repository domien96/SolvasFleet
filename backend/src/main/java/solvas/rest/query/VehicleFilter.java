package solvas.rest.query;

import solvas.models.*;
import solvas.persistence.api.Filter;

import javax.persistence.criteria.*;
import java.time.LocalDate;
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
    private int fleet = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Vehicle> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (vin != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("chassisNumber")),
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
        if (fleet >= 0) {
            Join<Vehicle, FleetSubscription> subscriptionJoin = root.join("fleetSubscriptions");
            Join<FleetSubscription, SubFleet> subFleetJoin = subscriptionJoin.join("subFleet");
            Join<SubFleet, Fleet> fleetJoin = subFleetJoin.join("fleet");

            LocalDate now = LocalDate.now();
            // The start must be before today
            Predicate start = builder.lessThanOrEqualTo(subscriptionJoin.get("startDate"), now);
            // The end is not set or after today
            Expression<LocalDate> endDate = subscriptionJoin.get("endDate");
            Predicate end = builder.or(
                    builder.isNull(endDate),
                    builder.greaterThan(endDate, now)
            );

            predicates.add(
                builder.and(
                        builder.equal(fleetJoin.get("id"), fleet),
                        start,
                        end
                )
            );
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

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }
}