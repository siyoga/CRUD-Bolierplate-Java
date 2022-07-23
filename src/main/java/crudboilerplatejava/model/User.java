package crudboilerplatejava.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "salt", nullable = false)
    private String salt;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String hashedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "roles")
    private List<Role> roles;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    public User(String firstname, String lastname, String username, String email, String salt, String hash) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hash;
    }
}
