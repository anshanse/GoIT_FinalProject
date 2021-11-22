package ua.goit.notesStorage.enums;

import org.springframework.security.core.GrantedAuthority;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

@NotNull
@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
public enum Role implements GrantedAuthority {

    USER,ADMIN;

    /*ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    @NotNull
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Optional<Role> getRole(String role) {
        return Arrays.stream(Role.values())
                .filter(enumValue -> enumValue.getRole().equals(role))
                .findAny();
    }*/

    @Override
    public String getAuthority() {
        return name();
    }
}
