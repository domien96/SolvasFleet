package solvas.authentication.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import solvas.service.models.Company;
import solvas.service.models.Permission;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Permissions a user has within the scope of a company
 */
public class Authority implements GrantedAuthority {
    private int companyId;
    private Collection<Permission> permissions = new HashSet<>();

    /**
     * Default constructor for Jackson
     */
    public Authority() {
    }

    Authority(Company company, Collection<Permission> permissions) {
        if(company != null) {
            this.companyId = company.getId();
        } else {
            this.companyId = -1;
        }
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return null;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }
}
