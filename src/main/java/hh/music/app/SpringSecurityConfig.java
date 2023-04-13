package hh.music.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests()
				// These pages will be permitted to view without signing in as admin
				.requestMatchers("/css/**", "index", "search", "searchResults").permitAll() // Enable css when logged
																							// out
				.requestMatchers("/").permitAll().requestMatchers("/h2-console/**").permitAll().anyRequest()
				.authenticated().and().formLogin().defaultSuccessUrl("/index", true).permitAll().and().logout()
				.logoutSuccessUrl("/index").permitAll().and().httpBasic();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}