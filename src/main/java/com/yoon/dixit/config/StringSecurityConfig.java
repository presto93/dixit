package com.yoon.dixit.config;

import com.yoon.dixit.user.enums.Role;
import com.yoon.dixit.user.repository.UserRepository;
import com.yoon.dixit.user.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class StringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests()
                .requestMatchers( "/auth").permitAll()
                .requestMatchers("/play/leader").hasAuthority(Role.LEADER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/auth/sign-in")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserValidationService userValidationService) {
        return userValidationService;
    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
//    }
}
