package org.lessons.java.spring.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()

                // Static Resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                // ADMIN Routes
                .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
                .requestMatchers("/pizzas/{id}/offer", "/offers/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/offers/**").hasAuthority("ADMIN")
                .requestMatchers("/ingredients/create", "/ingredients/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/ingredients/**").hasAuthority("ADMIN")

                // USER and ADMIN Routes
                .requestMatchers("/pizzas", "/pizzas/**", "/offers", "/offers/**", "/ingredients", "/ingredients/**")
                .hasAnyAuthority("USER", "ADMIN")

                // All other requests
                .anyRequest().permitAll();


                http.formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/pizzas", true)
                    .permitAll()
                );
    
                http.logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                );
    
                http.exceptionHandling(exception -> exception
                    .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}
