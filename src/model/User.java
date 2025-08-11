package model;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.dash.mvc.annotation.Email;
import mg.dash.mvc.annotation.Numeric;
import mg.dash.mvc.annotation.Required;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Numeric
    private int id;

    private String firstName;

    @Required
    private String lastName;

    @Required
    @Email
    private String email;

    @mg.dash.mvc.annotation.Date
    @Required
    private Date birthDate;

    @Required
    private String password;

    private String role;

    /* -------------------------------------------------------------------------- */
    /*                Function to get user role as a set of string                */
    /* -------------------------------------------------------------------------- */
    public Set<String> getUserRoles(){
        return new HashSet<>(Collections.singletonList(this.getRole()));
    }
}
