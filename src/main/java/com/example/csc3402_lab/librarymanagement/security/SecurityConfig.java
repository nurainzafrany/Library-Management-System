package com.example.csc3402_lab.librarymanagement.security;

import com.example.csc3402_lab.librarymanagement.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Optional: disable for development/testing
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/borrower/signup", "/borrower/login", "/borrower/forgot-password",
                                "/borrower/mainpage", "/borrower/profile", "/borrower/fines", "/borrower/store",
                                "/borrower/store/local", "/borrower/store/local/details", "/borrower/store/international").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/image/**", "/h2-console/**").permitAll()
                        .requestMatchers("/borrower/**").hasRole("BORROWER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/borrower/login")
                        .loginProcessingUrl("/borrower/login")
                        .defaultSuccessUrl("/borrower/mainpage", true) // Change to your dashboard URL
                        .failureUrl("/borrower/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/borrower/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }

}