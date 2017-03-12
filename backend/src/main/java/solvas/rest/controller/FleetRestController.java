package solvas.rest.controller;

import org.springframework.validation.Validator;
import solvas.models.Company;
import solvas.models.Fleet;
import solvas.persistence.Dao;
import solvas.persistence.DaoContext;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiFleet;

/**
 * Created by David Vandorpe.
 */
public class FleetRestController extends AbstractRestController<Fleet,ApiFleet> {
    /**
     * Default constructor.
     *
     * @param daoContext       The dao to work with.
     * @param mapper    The mapper class for objects of domain model class T and API-model class E.
     * @param validator The validator to use when creating/updating entities
     */
    protected FleetRestController(DaoContext daoContext, AbstractMapper<Fleet, ApiFleet> mapper, Validator validator) {
        super(dao, mapper, validator);
    }
}
