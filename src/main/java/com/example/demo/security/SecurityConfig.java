package com.example.demo.security;

import static com.example.demo.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.auth.ApplicationUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
		
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/**").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/courses", true)
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			.rememberMe()
				.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
				.key("somethingverysecured")
				.rememberMeParameter("remember-me")
			.and()
			.logout()
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID","_ga","remember-me")
			.logoutSuccessUrl("/login"); 		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		System.out.println("daoAuthenticationProvider()");
		DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		
		return provider;		
	}

	

/*@Override
@Bean 
protected UserDetailsService userDetailsService() {
	UserDetails annasmithUser = User.builder()
			.username("annasmith")
			.password(passwordEncoder.encode("1234"))
			//.roles(STUDENT.name())
			.authorities(STUDENT.getGrantedAuthorities()).build();
			
	
	UserDetails lindaUser = User.builder()
			.username("linda")
			.password(passwordEncoder.encode("1234"))
			//.roles(ADMIN.name())
			.authorities(ADMIN.getGrantedAuthorities()).build();
	
	UserDetails tomUser = User.builder()
			.username("tom")
			.password(passwordEncoder.encode("1234"))
			//.roles(ADMINTRAINEE.name()).build();
			.authorities(ADMINTRAINEE.getGrantedAuthorities()).build();
	
	return new InMemoryUserDetailsManager(annasmithUser,lindaUser,tomUser);
}
*/
}
