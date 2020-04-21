package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Authentication.DemoAuthenticationFailureHandler;


@EnableWebSecurity
//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationSuccessHandler demoAuthenticationSuccessHandler;
	@Autowired
	private DemoAuthenticationFailureHandler demoAuthenticationFailureHandler;
	
	// 密码明文加密方式配置
    @Bean
    public PasswordEncoder myEncoder() {
      return new BCryptPasswordEncoder();
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/").permitAll()
//			.antMatchers("/success/**").hasRole("USER");
		http.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/login_test")
			.successHandler(demoAuthenticationSuccessHandler)
			.failureHandler(demoAuthenticationFailureHandler)
			.and()
			.sessionManagement()
			.invalidSessionUrl("/sessionTimeOut")
			.and()
			.cors().and()
            .csrf().disable()
			.authorizeRequests().antMatchers("/","/login_test","/sessionTimeOut", "/oauth/token").permitAll()
			.anyRequest()
			.authenticated();
//	    http.csrf().disable();
//	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.logout();
		
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService);
//	}
//
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("user").password("{noop}admin").roles("USER");
//	}
	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("user").password(encoder.encode("password")).roles("USER").build());
//		return manager;
//	}

	
}
