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
public class SpringSecurityConfig  {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests()
        	.requestMatchers("/css/**", "index").permitAll() // Enable css when logged out
        	.anyRequest().authenticated()
        	.and()
      .formLogin()
          .defaultSuccessUrl("/index", true)
          .permitAll()
          .and()
      .logout()
          .logoutSuccessUrl("/index")
          .permitAll()
          .and()
      .httpBasic();
      return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}