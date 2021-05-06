package com.loladebadmus.simplecrudapp.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(
                name = "username_unique_constraint",
                columnNames = "name"
        )
})
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private UUID id; ///todo:How to use UUId for sequence generation


    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    private String name;

    public User(
                @JsonProperty("name") String name) {
        this.name = name;
    }

    public User(UUID id) {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
