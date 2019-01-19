package tn.abouzidi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .userSearchBase("ou=people")
            .userSearchFilter("uid={0}")
            .groupSearchBase("ou=groups")
            .groupSearchFilter("uniqueMember={0}")
        .contextSource().url("ldap://localhost:10389/dc=abouzidi,dc=com").and()
        .passwordCompare()
            .passwordEncoder(passwordEncoder())
            .passwordAttribute("userPassword");
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/api/**").hasRole("ADMIN");
	}

	@SuppressWarnings("deprecation")
	@Bean
	public LdapShaPasswordEncoder passwordEncoder() {
		return new LdapShaPasswordEncoder();
	}
}
