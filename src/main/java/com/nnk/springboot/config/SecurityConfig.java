package com.nnk.springboot.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    /**
     * Provides a bean for password hashing.
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method defines:
     * Role-based access rules for different URL patterns
     * Form login page and default success URL
     * Logout URL and behavior
     * Access denied page for unauthorized access attempts
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login", "/css/**", "/js/**", "/error/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/403")
                );

        return http.build();
    }

}