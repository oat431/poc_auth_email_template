package panomete.poc.resemail.security.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import panomete.poc.resemail.util.JWTUtil;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityFilterService extends OncePerRequestFilter {
    final JWTUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//        if (token != null) {
//            token = token.substring(7);
//            String username = jwtTokenUtil.getUsernameFromToken(token);
//            Auth user = authService.getUserByUsername(username);
//            if (jwtTokenUtil.isTokenValid(token, user)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                log.info("user {} using {} {}", username, request.getMethod(), request.getRequestURI());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                response.sendError(HttpServletResponse.SC_FORBIDDEN, "This Action is Forbidden");
//            }
//        }
        filterChain.doFilter(request, response);
    }
}
