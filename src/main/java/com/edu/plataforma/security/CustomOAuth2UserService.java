package com.edu.plataforma.security;

import com.edu.plataforma.model.Role;
import com.edu.plataforma.model.User;
import com.edu.plataforma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // ✅ Datos de Google
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        // ✅ Buscar usuario en BD
        Optional<User> userOptional = userRepository.findByEmail(email);

        User user;

        if (userOptional.isPresent()) {
            // ✅ Usuario existente → actualiza datos
            user = userOptional.get();
            user.setName(name);

        } else {
            // ✅ Usuario nuevo → se crea
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("OAUTH2-" + UUID.randomUUID()); // password placeholder para cumplir la restricción NOT NULL
            user.setRole(Role.USER); // ✅ rol por defecto
        }

        userRepository.save(user);

        // ✅ Retornar usuario OAuth2
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                attributes,
                "email");
    }
}