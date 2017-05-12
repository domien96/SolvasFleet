package solvas.persistence.api.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.Company;
import solvas.service.models.EntityType;
import solvas.service.models.Revision;
import solvas.service.models.Vehicle;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Integration tests to test whether the hibernate mappings work for a  revision
 *
 */
public class RevisionDaoTest extends DaoTest {

    @Autowired
    private RevisionDao revisionDao;

    @Autowired
    private VehicleDao vehicleDao;

    /**
     * Test finding revisions with a certain name.
     */
    @Test
    public void retrieveAll() {

        assertThat(revisionDao.findAll(), hasSize(2));
    }

    /**
     * Test the entire mapping
     */
    @Test
    public void mappingTestId0() throws EntityNotFoundException {
        Revision revision = revisionDao.find(1);
        assertThat(revision.getId(),is(1));
        assertThat(revision.getEntityType(), is("VEHICLE"));
        assertThat(revision.getUser().getFirstName(), is("Sierra"));
        assertThat(vehicleDao.find((revision.getEntity())).getLicensePlate(), is("1-IKR-795"));
        assertThat(revision.getMethod().ordinal(), is(1));

    }



}