package com.study03.www.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthProvider authProvider;
	
    @Autowired
    AuthFailureHandler authFailureHandler;
 
    @Autowired
    AuthSuccessHandler authSuccessHandler;
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("param1").password("param2").roles("USER", "ADMIN");
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
	   		.antMatchers("/resource/**").permitAll()
	   		.antMatchers("/bd/**").hasAnyRole("USER","ADMIN")
	   		.antMatchers("/**").permitAll()
	   		.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/doLogin")
			.failureHandler(authFailureHandler)
	        .successHandler(authSuccessHandler)
			.usernameParameter("param1")
			.passwordParameter("param2")
		.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
		.and()
			.csrf().disable()
			.authenticationProvider(authProvider);
	}
}
