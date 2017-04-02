package solvas.rest.query;

import solvas.service.models.Contract;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.SubFleet;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by domien on 30/03/2017.
 * @author Steven
 */
public class ContractFilter extends ArchiveFilter<Contract> {
    private int company = -1;
    private int vehicle=-1;
    private int fleet=-1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Contract> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (company >= 0) {
            predicates.add(builder.equal(root.get("company"), company));
        }
        if (vehicle >= 0) {
            Join<Contract, FleetSubscription> subscriptionJoin = root.join("fleetSubscription");


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
                            builder.equal(subscriptionJoin.get("vehicle"), vehicle),
                            start,
                            end
                    )
            );
        }
        if (fleet >= 0){
            Join<Contract, FleetSubscription> subscriptionJoin = root.join("fleetSubscription");
            Join<FleetSubscription, SubFleet> subFleetJoin = subscriptionJoin.join("subFleet");


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
                            builder.equal(subFleetJoin.get("fleet"), fleet),
                            start,
                            end
                    )
            );
        }

        return predicates;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public int getVehicle() {
        return vehicle;
    }
}
