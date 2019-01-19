package tn.abouzidi.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.abouzidi.model.Account;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static Map<String, Account> users = new HashMap<>();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void init() {
		users.put("admin", new Account("admin", passwordEncoder.encode("admin@123"), "ROLE_ADMIN"));
		users.put("user", new Account("user", passwordEncoder.encode("user@123"), "ROLE_USER"));
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Account account = users.get(authentication.getName().trim());
		String password = authentication.getCredentials().toString().trim();
		if ((account != null) && (passwordEncoder.matches(password, account.getPassword()))) {

			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole()));

			log.info("grantedAuthorities : " + grantedAuthorities);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authentication.getName(),
					authentication.getCredentials().toString(), grantedAuthorities);
			log.info("auth : " + auth.isAuthenticated());
			return auth;
		}
		throw new BadCredentialsException("Authentication failed for user = " + account.getName());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
