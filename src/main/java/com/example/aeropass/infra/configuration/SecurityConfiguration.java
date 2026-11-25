package com.example.aeropass.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/auth/login",
                        "/auth/cadastre-se",
                        "/auth/redefinir-senha",
                        "/auth/esqueci-senha",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                ).permitAll()
                // Exemplo de filtro por role
                                // Usuários
                                .requestMatchers(HttpMethod.POST, "/usuarios/admin").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/usuarios/cadastro/empresa/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/usuarios/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PATCH, "/usuarios/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAnyRole("ADMIN", "USER")
                                // Empresas
                                .requestMatchers(HttpMethod.POST, "/empresas/cadastro").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/empresas/*/status").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/empresas/*/excluir").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/empresas/**").hasAnyRole("ADMIN", "USER")
                                // Forma de pagamento
//                                .requestMatchers("/forma-pagamento/cadastro").hasRole("ADMIN")
                                // Aviões e pilotos
                                .requestMatchers("/avioes/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/pilotos/**").hasAnyRole("ADMIN", "USER")
                                // Voos
                                .requestMatchers(HttpMethod.POST, "/voos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/voos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PATCH, "/voos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/voos/**").hasAnyRole("ADMIN", "USER")
                                // Passageiros
                                .requestMatchers(HttpMethod.POST, "/passageiros/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/passageiros/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/passageiros/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/passageiros/**").hasRole("ADMIN")
                                // Passagens
                                .requestMatchers(HttpMethod.POST, "/passagens/*/reservar").hasRole("CLIENTE")
                                .requestMatchers(HttpMethod.PATCH, "/passagens/*/cancelar").hasRole("CLIENTE")
                                .requestMatchers(HttpMethod.POST, "/passagens/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/passagens/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PATCH, "/passagens/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/passagens/**").hasAnyRole("ADMIN", "USER")

                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
