package com.example.demo.auth;

import static com.example.demo.security.ApplicationUserRole.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		
		// TODO Auto-generated method stub
		return  getApplicationUsers()
					.stream()
					.filter(applicationuser -> username.equals(applicationuser.getUsername()))
					.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser(
						STUDENT.getGrantedAuthorities(),
						"annasmith",
						passwordEncoder.encode("1234"),
						true,
						true,
						true,
						true						
						),
				new ApplicationUser(
						ADMIN.getGrantedAuthorities(),
						"linda",
						passwordEncoder.encode("1234"),
						true,
						true,
						true,
						true						
						),
				new ApplicationUser(
						ADMINTRAINEE.getGrantedAuthorities(),
						"tom",
						passwordEncoder.encode("1234"),
						true,
						true,
						true,
						true						
						)
				);
		return applicationUsers;
	}

}
