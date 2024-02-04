package savoginEros.ParkprojectBE.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.UnauthorizedException;
import savoginEros.ParkprojectBE.services.UserService;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            throw new UnauthorizedException("Per favore metti il token nell' Authorization header oppure controlla che l'authorization cominci per Bearer");

        } else {

            String accessToken = authHeader.substring(7);

            jwtTools.verifyToken(accessToken);

            String id = jwtTools.extractIdFromToken(accessToken);
            User user = userService.getUserById(Long.parseLong(id));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
