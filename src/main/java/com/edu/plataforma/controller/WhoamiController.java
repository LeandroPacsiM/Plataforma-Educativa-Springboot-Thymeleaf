package com.edu.plataforma.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class WhoamiController {

    @GetMapping("/whoami")
    public Map<String, Object> whoami(Principal principal, Authentication authentication) {
        boolean authenticated = authentication != null && authentication.isAuthenticated() && !(principal == null || "anonymousUser".equals(principal.getName()));

        List<String> authorities = authentication == null ? List.of() : authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String authClass = authentication == null ? null : authentication.getClass().getSimpleName();

        Object oauthAttributes = null;
        if (authentication instanceof OAuth2AuthenticationToken oauth2) {
            oauthAttributes = oauth2.getPrincipal().getAttributes();
        }

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("authenticated", authenticated);
        response.put("principal", principal == null ? null : principal.getName());
        response.put("authorities", authorities);
        response.put("authClass", authClass);
        response.put("oauthAttributes", oauthAttributes);

        return response;
    }
}
