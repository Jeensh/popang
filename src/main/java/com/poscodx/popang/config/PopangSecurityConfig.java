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

        /**
         *  *authorizeHttpRequests 설정*
         *  my 으로 시작하는 URL은 접근 권한 필요
         *  notices, contact는 누구나 접근 가능
         *
         */

        http.authorizeHttpRequests( (requests) -> requests
                        .requestMatchers("/error").permitAll()
                        .anyRequest().permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/")
                        .loginProcessingUrl("/user/auth")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/board/showAll/1")
                        .failureForwardUrl("/"))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();

        /**
         *  모든 request 거절
         *  Configuration to deny all the requests
         */
        /*http.authorizeHttpRequests(requests -> requests.anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();*/

        /**
         * 	모든 request 허락
         *  Configuration to permit all the requests
         */
        /*http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();*/

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(USER_SERVICE);
        authenticationProvider.setPasswordEncoder(ENCODER);
        return authenticationProvider;
    }
}
