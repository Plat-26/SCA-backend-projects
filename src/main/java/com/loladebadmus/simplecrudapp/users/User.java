package com.loladebadmus.simplecrudapp.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(
                name = "username_unique_constraint",
                columnNames = "email"
        )
})
public class User implements UserDetails {
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid"
    )
    @GeneratedValue(
            generator = "user_uuid"
    )
    @Column(
            name = "id",
            updatable = false
    )
    @JsonView
    private UUID id;


    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    @NotBlank(message = "Please enter your first name")
    @JsonView
    private String firstName;
    @NotBlank(message = "Please enter your last name")
    @JsonView
    private String lastName;
    @NotBlank(message = "Please enter your email address")
    @JsonView
    private String email;
    private String password;
    private UserRole role;
    private Boolean enabled = false;
    private Boolean locked = false;

    @JsonView
    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Rental> rentals = new ArrayList<>();

    public User() {
    }


    public User(@NotBlank(message = "Please enter your first name") String firstName, @NotBlank(message = "Please enter your last name") String lastName, @NotBlank(message = "Please enter your email address") String email, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            List<Rental> rentals) {
        this.firstName = name;
        this.rentals = rentals;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !enabled;
    }

    public void addRental(Rental rental) {
        this.getRentals().add(rental);
    }

    public  void removeRental(Rental rental) {
        this.getRentals().remove(rental);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return firstName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public List<Rental> retrieveRentals() {
        return this.rentals;
    }

    public void updateRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                '}';
    }
}
