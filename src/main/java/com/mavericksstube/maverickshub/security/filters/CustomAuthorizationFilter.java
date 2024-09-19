package com.mavericksstube.maverickshub.security.filters;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

import com.auth0.jwt.JWT;
import static com.mavericksstube.maverickshub.security.utils.SecurityUtils.JWT_PREFIX;
import static com.mavericksstube.maverickshub.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

/**
 * 1. retrieve request path
 * 1b. if request path from 1a is a punllipath,
 *      call the next filter in the chain and terminate this filters execution
 *3  Decode access token
 *4. extract token permission
 *5. add token permiission to security context
 *6. call the next filter in the FilterChain
 */
        String requestPath = request.getServletPath(); // "/api/v1/..."
        boolean isRequestPathPublic = PUBLIC_ENDPOINTS.contains(requestPath);
        if (isRequestPathPublic) filterChain.doFilter(request, response);
        String authorizationHeader = request.getHeader("AUTHORIZATION");
        if (authorizationHeader != null) {

            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();
//decode access token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes())).withIssuer("mavericks_hub").withClaimPresence("roles").build();
            DecodedJWT decodeJWT = verifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodeJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }
}
