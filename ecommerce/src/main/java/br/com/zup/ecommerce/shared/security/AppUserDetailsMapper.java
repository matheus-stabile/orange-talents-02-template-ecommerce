package br.com.zup.ecommerce.shared.security;

import br.com.zup.ecommerce.users.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper {

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new AuthenticatedUser((User) shouldBeASystemUser);
    }
}
