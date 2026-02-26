package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/registracija/**").permitAll()
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/html/**", 
                    "/js/**", 
                    "/css/**", 
                    "/images/**", 
                    "/favicon.ico",
                    "/prijava",
                    "/prijava.html",
                    "/registracija",
                    "/registracija.html",
                    "/api/registracija/**",
                    "api/registracija/registracija",
                    "/api/korisnik/prijava",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/html/prijava.html") 
                .loginProcessingUrl("/prijava")   
                .defaultSuccessUrl("/", true)
                .failureUrl("/html/prijava.html?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/odjava") 
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");

                    String authHeader = request.getHeader("Authorization");
                    String poruka = (authHeader == null) 
                        ? "Niste prijavljeni. Prijavite se!" 
                        : "Korisničko ime ili lozinka nisu ispravni.";

                    String jsonResponse = String.format(
                        "{\"status\": 401, \"greska\": \"Neautoriziran pristup\", \"detalji\": \"%s\"}", 
                        poruka
                    );
                    
                    response.getWriter().write(jsonResponse);
                })
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

   @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}