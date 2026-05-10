package com.edu.plataforma.security;

import com.edu.plataforma.model.Role;
import com.edu.plataforma.model.User;
import com.edu.plataforma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OIDC provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setName(name);
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("OAUTH2-" + UUID.randomUUID());
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        return oidcUser;
    }
}
