package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/osobnipodaci",
                    "/html/**", 
                    "/js/**", 
                    "/prijava",
                    "/css/**",
                    "/v3/api-docs/**",      
                    "/swagger-ui/**",       
                    "/swagger-ui.html"      
                ).permitAll()
                .anyRequest().authenticated()
            )
                .formLogin(form -> form
                .loginPage("/prijava")                
                .loginProcessingUrl("/prijava")       
                .defaultSuccessUrl("/osobni-podaci", true)
                .failureUrl("/prijava?error=true")    
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");

                    String authHeader = request.getHeader("Authorization");
                    String poruka;

                    if (authHeader == null) {
                        poruka = "Niste prijavljeni. Prijavite se svojim korisničkim imenom i lozinkom!";
                    } else {
                        poruka = "Korisničko ime ili lozinka nisu ispravni. Ponovite prijavu!";
                    }

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
        return NoOpPasswordEncoder.getInstance();
    }
}