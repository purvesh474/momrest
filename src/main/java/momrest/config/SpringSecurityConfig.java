package momrest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.inMemoryAuthentication().
			withUser("avengers").password("{noop}pksh@28012528").roles("ADMIN");
	}
	
	
	protected void configure(HttpSecurity http) throws Exception{
		
		http
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/user/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/user/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,"/user/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/user/**").hasRole("ADMIN")
			
			.antMatchers(HttpMethod.GET,"/compMeeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/compMeeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,"/compMeeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/compMeeting/**").hasRole("ADMIN")
			
			.antMatchers(HttpMethod.GET,"/meeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/meeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,"/meeting/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/meeting/**").hasRole("ADMIN")
			
			.and()
			.csrf().disable()
			.formLogin().disable();
	}
}
