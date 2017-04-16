package solvas.rest.query;

import solvas.service.models.InsuranceType;
import solvas.service.models.Tax;
import solvas.service.models.VehicleType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Filters for a {@link Tax}.
 *
 * @author Sjabasti
 */
public class TaxFilter extends ArchiveFilter<Tax> {

    private String vehicleType;
    private String contractType;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Tax> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);

        Join<Tax, VehicleType> join = root.join("vehicleType");
        predicates.add(builder.equal(
                builder.lower(join.get("name")),
                vehicleType.toLowerCase()
        ));

        Join<Tax, InsuranceType> join2 = root.join("insuranceType");
        predicates.add(builder.equal(
                builder.lower(join2.get("name")),
                contractType.toLowerCase()
        ));


        return predicates;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}