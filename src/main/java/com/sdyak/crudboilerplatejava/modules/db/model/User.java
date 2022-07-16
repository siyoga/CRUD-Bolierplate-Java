package com.sdyak.crudboilerplatejava.modules.db.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "salt")
    private byte[] salt;

    @Column(name = "password")
    private String hashedPassword;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    public User(String firstname, String lastname, String username, String email, byte[] salt, String hash) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hash;
    }
}
