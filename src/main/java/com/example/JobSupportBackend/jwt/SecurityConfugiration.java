package com.example.JobSupportBackend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfugiration {

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtRequsetFilter jwtRequestFilter;
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers("/authenticate", "/register", "/update/*", "/persnolInfo/*", "/otherInfo/*",
						"/addUserData/*", "/getUser/*", "/employerInfo/*", "/verify/**", "/sendOTP/*", "/verifyOTP/**",
						"/upload/*", "/resetPassword/**", "/photo/*", "/regenerate-otp/*", "/adminRegister",
						"/adminLogin/**", "/updateFreelancer/*", "/deleteSkill/*", "/email/*", "/change-password/***",
						"/postReason/*", "/files/*", "/addproject/*", "/regenerate-otp/*", "/updateFreelancer/*",
						"/adminRegister", "/adminLogin/**", "/postPortfolio/*", "/getPortfolios/*", "/images/**",
						"getallProjects", "/updatedprojectIds", "gettingupdatedprojectIds", "/updatePortfolio/**",
						"/deletePortfolio/**", "/getPortByEmail&Title/**", "/getAllUsers/*", "/getAllAdminProjects",
						"/filesGet/*", "/files/*", "/projects/*", "/changeStatus/*", "/deleteUser/*",
						"/getAllUsersByStatus/**", "/active/*", "/deactivated/*", "/getAdminProjectById/*",
						"/updateAdminProject/*", "/totalUsersByRole/*", "/accountStatus/*", "/getProjectById/*",
						"/sendProposal/**", "/getProposals/*", "/getProposalById/*", "/updateProposal/*",
						"/deleteProposal/*", "/getProjectsOfAdmin", "/getProposalsByProjectId/*",
						"/updateInfoForEmployeerDashBoard/*", "/photoUpdate/*", "/uploadPhotoToS3", "/check-email",
						"/unpublished", "/status/toggle/*", "/expired", "/getProjectsByIds", "/updateProject/*")
				.permitAll().anyRequest().authenticated())

				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	CorsFilter corsFilter() {
		return new CorsFilter();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
}
