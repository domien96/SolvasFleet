package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Company;
import solvas.persistence.api.Dao;
import solvas.service.models.Revision;

import java.util.Collection;
import java.util.Optional;

/**
 * DAO for revisions.
 *
 */
@Repository
public interface RevisionDao extends Dao<Revision> {

}