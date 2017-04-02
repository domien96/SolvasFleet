package solvas.rest.query;

import solvas.service.models.Contract;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        //TOdo make predicates to filter bij fleet and vehicle
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
