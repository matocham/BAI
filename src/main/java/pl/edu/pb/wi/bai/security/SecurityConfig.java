package pl.edu.pb.wi.bai.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pl.edu.pb.wi.bai.repositories.PasswordRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationFilter;
import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationProvider;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationFilter;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordRepository passwordRepository;
	@Autowired
	OracleUserDetailsService usersService;
	@Autowired
	PasswordAuthenticationProvider passwordAuthenticationProvider;
	@Autowired
	UsernameAuthenticationProvider usernameAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(usernameAuthenticationProvider, passwordAuthenticationProvider));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(usernameProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterAfter(passwordProcessingFilter(), UsernameAuthenticationFilter.class)
		.addFilterAfter(new ClearPreAuthAuthorityFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/login*", "/register*").anonymous()
		.antMatchers("/", "/index").permitAll()
		.antMatchers("/webjars/**", "/css/**", "/js/**").permitAll()
		.antMatchers("/secondLoginStep*").hasAuthority("ROLE_PREAUTH")
		.anyRequest().hasRole("USER")
		.and().formLogin().loginPage("/login")
				.failureUrl("/login?error").defaultSuccessUrl("/secondLoginStep*")
				.successHandler(new SecondStepRedirectLoginSuccessHandler())
		.and().exceptionHandling().accessDeniedPage("/403");
	}

	@Bean
	UsernameAuthenticationFilter usernameProcessingFilter() throws Exception {
		UsernameAuthenticationFilter usernameAuthenticationFilter = new UsernameAuthenticationFilter();
		usernameAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		usernameAuthenticationFilter.setAuthenticationSuccessHandler(new SecondStepRedirectLoginSuccessHandler());
		return usernameAuthenticationFilter;
	}

	@Bean
	PasswordAuthenticationFilter passwordProcessingFilter() throws Exception {
		PasswordAuthenticationFilter passwordAuthenticationFilter = new PasswordAuthenticationFilter();
		passwordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		passwordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		passwordAuthenticationFilter.setAuthenticationFailureHandler(new PasswordProviderAuthFailureHandler());
		return passwordAuthenticationFilter;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessLogHandler(userRepository, passwordRepository);
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}