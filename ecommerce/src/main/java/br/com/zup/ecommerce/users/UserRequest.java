package br.com.zup.ecommerce.users;

import br.com.zup.ecommerce.shared.validations.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "username", message = "o usuário informado já está cadastrado")
    private String username;

    @NotBlank
    @Length(min = 6)
    private String password;

    @Deprecated
    public UserRequest() {
    }

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User toModel() {
        return new User(username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
