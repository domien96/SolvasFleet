package solvas.authentication.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import solvas.service.models.Company;
import solvas.service.models.Permission;

import java.util.Collection;
import java.util.stream.Collectors;

public class Authority implements GrantedAuthority {
    private final Company company;
    private final Collection<Permission> permissions;

    Authority(Company company, Collection<Permission> permissions) {
        this.company = company;
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return null;
    }

    public Integer getCompanyId() {
        return company.getId();
    }

    public Collection<String> getPermissions() {
        return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
    }
}
