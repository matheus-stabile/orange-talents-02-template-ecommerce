package br.com.zup.ecommerce.shared.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements UserDetails {

    private User springUserDetails;
    private br.com.zup.ecommerce.users.User user;

    public AuthenticatedUser(@NotNull @Valid br.com.zup.ecommerce.users.User user) {
        this.user = user;
        springUserDetails = new User(user.getUsername(), user.getPassword(), List.of());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return springUserDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return springUserDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }

    public br.com.zup.ecommerce.users.User get() {
        return user;
    }
}
