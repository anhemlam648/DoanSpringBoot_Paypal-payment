package com.example.DoAnJava.Utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig5 {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request.anyRequest().authenticated())
                .oauth2Login();
        return http.build();
    }

    @Bean
    public FilterRegistrationBean<OAuth2LoginAuthenticationFilter> oauth2LoginAuthenticationFilter() {
        FilterRegistrationBean<OAuth2LoginAuthenticationFilter> registrationBean =
                new FilterRegistrationBean<>(new OAuth2LoginAuthenticationFilter(authorizedClientService));
        registrationBean.setOrder(-100); // Set the order to a lower value to make it execute before the main Spring Security filter chain.
        return registrationBean;
    }
}
