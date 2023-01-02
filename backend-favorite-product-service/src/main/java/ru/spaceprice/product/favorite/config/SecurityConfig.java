package ru.spaceprice.product.favorite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .anyExchange().authenticated();

        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable();

        http.oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
