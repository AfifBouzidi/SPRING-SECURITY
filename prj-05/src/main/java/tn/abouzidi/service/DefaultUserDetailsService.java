package tn.abouzidi.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.abouzidi.model.Account;

@Slf4j
@Service
public class DefaultUserDetailsService implements UserDetailsService {

	private static Map<String, Account> users = new HashMap<>();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void init() {
		users.put("admin", new Account("admin", passwordEncoder.encode("admin@123"), "ADMIN"));
		users.put("user", new Account("user", passwordEncoder.encode("user@123"), "USER"));
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("load user : {}",userName);
		Account user = users.get(userName);
		if ((user != null))
			return User.withUsername(user.getName()).password(user.getPassword()).roles(user.getRole()).build();
		else
			throw new UsernameNotFoundException("User not found by name: " + userName);
	}

}
