package br.com.zup.ecommerce.users;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static io.jsonwebtoken.lang.Assert.notNull;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @PastOrPresent
    @NotNull
    @JsonIgnore
    private final LocalDateTime registrationDate = LocalDateTime.now();

    @NotBlank
    @Email
    private String username;

    @NotBlank
    @Length(min = 6)
    @JsonIgnore
    private String password;

    public User(@NotBlank @Email String username, @NotBlank @Length(min = 6) String password) {

        notNull(username, "o nome de usuário não pode ser nulo");
        isTrue(StringUtils.hasLength(username), "o nome de usuário não pode estar em branco");
        notNull(password, "a senha não pode ser nula");
        isTrue(password.length() >= 6, "a senha deve ter 6 ou mais caracteres");

        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);

    }

    @Deprecated
    public User() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

