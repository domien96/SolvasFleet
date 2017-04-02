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
    private Collection<String> permissions = new HashSet<>();

    /**
     * Default constructor for Jackson
     */
    public Authority() {
    }

    Authority(Company company, Collection<Permission> permissions) {
        this.companyId = company.getId();
        this.permissions = permissions.stream().map(Permission::getName).collect(Collectors.toSet());
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

    public Collection<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<String> permissions) {
        this.permissions = permissions;
    }
}
