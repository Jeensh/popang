package com.poscodx.popang.config;

import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class PopangSecurityConfig {
    private final UserService USER_SERVICE;
    private final BCryptPasswordEncoder ENCODER;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/error/**", "/user/**").permitAll()
                        .requestMatchers("/rest/**", "/products/**", "/order/**", "/category/**", "/main").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/seller/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SELLER")
                        .anyRequest().permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/")
                        .loginProcessingUrl("/user/auth")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main")
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(USER_SERVICE);
        authenticationProvider.setPasswordEncoder(ENCODER);
        return authenticationProvider;
    }
}
