package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.Authentication.DemoAuthenticationFailureHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler demoAuthenticationSuccessHandler;
	@Autowired
	private DemoAuthenticationFailureHandler demoAuthenticationFailureHandler;
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
		.loginPage("/")
		.loginProcessingUrl("/login_test")
		.successHandler(demoAuthenticationSuccessHandler)
		.failureHandler(demoAuthenticationFailureHandler)
                .and().anonymous().disable()
                .authorizeRequests()
                .antMatchers("/success/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
