package com.example.DoAnJava.Utils;


import com.example.DoAnJava.services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig5 {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    return     http.authorizeHttpRequests(request->request
//                        .anyRequest()
//                        .authenticated())
//                .oauth2Login(oauth2Login ->
//                        oauth2Login.loginPage("/login") // Specify your custom login page here
//                                .defaultSuccessUrl("/") // Change to your desired landing page after successful login
        @Bean
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/js/**", "/", "/register", "/404")
                            .permitAll()
                            .requestMatchers("/admin/product")
                            .hasAnyAuthority("ADMIN")
                            .requestMatchers("/product/list")
                            .hasAnyAuthority("ADMIN", "USER")
                            .anyRequest().authenticated()
                    )
                    .logout(logout -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .permitAll()
                    )
                    .formLogin(formLogin -> formLogin.loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/")
                            .permitAll()
                    )
                    .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                            .tokenValiditySeconds(86400)
                            .userDetailsService(userDetailsService())
                    )
                    .exceptionHandling(exceptionHandling ->
                            exceptionHandling.accessDeniedPage("/404")
                    )
                    .oauth2Login(oauth2Login ->
                            oauth2Login.loginPage("/login") // Specify your custom login page here
                                    .defaultSuccessUrl("/") // Change to your desired landing page after successful login
                    );
        }

        // Define the SecurityFilterChain to enable both OAuth2 and regular form login
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                    .oauth2Login();
            return http.build();
        }
}
