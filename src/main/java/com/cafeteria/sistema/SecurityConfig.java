package com.cafeteria.sistema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desativa CSRF para que o Thunder Client consiga fazer POST/DELETE sem erro
            .csrf(csrf -> csrf.disable())

            // 2. Regras de Autorização
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // Libera o banco H2 para você trabalhar
                .anyRequest().authenticated() // TRANCA TUDO: Qualquer outra rota exige login
            )

            // 3. Necessário para o console do H2 abrir dentro de um frame no navegador
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

            // 4. Habilita o formulário de login (Navegador) e o Basic Auth (Thunder Client)
            .formLogin(Customizer.withDefaults()) 
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}