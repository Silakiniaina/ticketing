package model;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mg.dash.mvc.annotation.Email;
import mg.dash.mvc.annotation.Numeric;
import mg.dash.mvc.annotation.Required;

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

    /* -------------------------------------------------------------------------- */
    /*                                   Getters                                  */
    /* -------------------------------------------------------------------------- */
    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    /* -------------------------------------------------------------------------- */
    /*                                   Setters                                  */
    /* -------------------------------------------------------------------------- */
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role){
        this.role = role;
    }
}
