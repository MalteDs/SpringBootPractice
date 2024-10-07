package com.example.SpringBootPractice.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.SpringBootPractice.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    // Rutas públicas
    private static final List<String> PUBLIC_URLS = List.of("/authenticate", "/register");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
        // Si la ruta es pública, no hacer nada y continuar
        String requestURI = request.getRequestURI();
        if (PUBLIC_URLS.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username=null;
        String jwtToken=null;

        if (requestTokenHeader != null) {
            jwtToken = requestTokenHeader.substring(7); // Extrae solo el token
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken); // Obtiene el username desde el token
            } catch (IllegalArgumentException e) {
                System.out.println("No se puede obtener el JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("El JWT Token ha expirado");
            }
        } else {
            logger.warn("JWT Token no empieza con 'Bearer '");
        }

        // validar token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            // Si el token es válido, configura Spring Security para establecer la autenticación
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);


    }
    
}
