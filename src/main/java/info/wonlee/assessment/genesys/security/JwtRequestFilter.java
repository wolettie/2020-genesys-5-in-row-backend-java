package info.wonlee.assessment.genesys.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static info.wonlee.assessment.genesys.security.JwtValidator.USER_CLAIM;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final SimpleUserDetailsService simpleUserDetailsService;

    public JwtRequestFilter(SimpleUserDetailsService simpleUserDetailsService) {
        this.simpleUserDetailsService = simpleUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String uuid = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            final DecodedJWT decodedJWT = JwtValidator.verifyJwt(jwtToken);
            if (decodedJWT != null) {
                uuid = decodedJWT.getClaim(USER_CLAIM).asString();
            }
        }

        if (uuid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.simpleUserDetailsService.loadUserByUsername(uuid);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.warn("failed to load user '{}' using valid token", uuid);
            }
        }
        filterChain.doFilter(request, response);
    }
}
