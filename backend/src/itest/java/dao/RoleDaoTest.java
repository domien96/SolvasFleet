package dao;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Role;
import solvas.persistence.role.HibernateRoleDao;
import solvas.persistence.role.RoleDao;

import static org.junit.Assert.assertEquals;

@Ignore
@Transactional
public class RoleDaoTest {
    private RoleDao roleDao=new HibernateRoleDao();

    @Test
    public void testCreate()
    {
        assertEquals("Empty to begin with",0,roleDao.findAll().size());

    }
}
