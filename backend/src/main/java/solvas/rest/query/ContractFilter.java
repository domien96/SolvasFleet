package solvas.rest.query;

import solvas.service.models.Contract;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Expression;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Filters for a {@link Contract}.
 *
 * @author Domien
 * @author Steven
 */
public class ContractFilter extends ArchiveFilter<Contract> {
    private int insuranceCompany = -1;
    private int vehicle=-1;
    private int fleet=-1;
    private int clientCompany=-1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Contract> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (insuranceCompany >= 0) {
            predicates.add(builder.equal(root.get("company"), insuranceCompany));
        }
        if (vehicle >=0 ||fleet >=0 || clientCompany > 0){
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
            
            if (vehicle >= 0) {
                predicates.add(
                        builder.and(
                                builder.equal(subscriptionJoin.get("vehicle"), vehicle),
                                start,
                                end
                        )
                );
            }
            if (fleet >= 0){
                 predicates.add(
                        builder.and(
                                builder.equal(subscriptionJoin.get("fleet"), fleet),
                                start,
                                end
                        )
                );
            }
            if (clientCompany >= 0){
                Join<Contract, Fleet> fleetJoin = subscriptionJoin.join("fleet");
                predicates.add(
                        builder.equal(fleetJoin.get("company"), clientCompany)
                );



            }


        }

        return predicates;
    }

    public void setInsuranceCompany(int insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public void setClientCompany(int clientCompany) {this.clientCompany = clientCompany;}

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
