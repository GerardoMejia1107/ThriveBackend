package com.gerardo.thrive.auth.filters;

import com.gerardo.thrive.auth.services.CustomUserDetailsService;
import com.gerardo.thrive.auth.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String rawToken = authorizationHeader.substring(7);

        try {
            String sub = jwtService.extractUsername(rawToken);

            if (sub != null && !sub.isBlank() && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(sub);

                boolean attemptIsValid = jwtService.isTokenValid(rawToken, userDetails);


                if (attemptIsValid) {
                    UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(
                            userDetails, null, Arrays.stream(jwtService.extractAuthorities(rawToken)
                                            .split(", "))
                                    .map(SimpleGrantedAuthority::new)
                                    .toList());
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);

                }
            }
        } catch (IllegalArgumentException | JwtException e) {
            SecurityContextHolder.clearContext();
            logger.error("An error occurred during JwtAuthenticationFilter: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
