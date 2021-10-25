package com.example.demo.security;

import static com.example.demo.security.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/**").hasRole(STUDENT.name())
			/*.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermissions())
			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermissions())
			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermissions())	*/
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

@Override
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

}
