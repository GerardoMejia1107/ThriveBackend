package com.gerardo.thrive.config.jwt;

import com.gerardo.thrive.user.services.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private CustomUserDetailsService customUserDetailsService;

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
                            userDetails, null, userDetails.getAuthorities());
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
