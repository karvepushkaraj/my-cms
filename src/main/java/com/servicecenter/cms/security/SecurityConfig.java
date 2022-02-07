package com.servicecenter.cms.security;

import com.servicecenter.cms.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.servicecenter.cms.security.UserPermission.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private AppUserService appUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/service-center/admin/**").hasAuthority(TECH_MODIFY.name())
                .antMatchers(HttpMethod.GET,"/api/v1/service-center/technician/**").hasAuthority(TECH_GET.name())
                .antMatchers(HttpMethod.POST,"/api/v1/service-center/technician/**").hasAuthority(SERVICE_CALL_DELETE.name())
                .antMatchers("/api/v1/service-center/allocate/**").hasAuthority(SERVICE_CALL_ALLOCATE.name())
                .antMatchers(HttpMethod.GET, "/api/v1/service-center/service-request/**").hasAuthority(SERVICE_CALL_GET.name())
                .antMatchers(HttpMethod.POST, "/api/v1/service-center/service-request/**").hasAuthority(SERVICE_CALL_CLOSE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

}
