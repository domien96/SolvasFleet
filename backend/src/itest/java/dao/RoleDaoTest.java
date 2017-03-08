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
    public void addRole()
    {

    }

    @Test
    public void destroyRole()
    {

    }

    @Test
    public void updateRole()
    {

    }

    @Test
    public void findRoleById()
    {

    }

    @Test
    public void findRoles()
    {

    }
}
