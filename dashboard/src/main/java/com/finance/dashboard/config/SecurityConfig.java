package com.finance.dashboard.config;

import com.finance.dashboard.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/register", "/login/**", "/auth/**", "/h2-console/**").permitAll()

						// 🔴 ADMIN ONLY: Create/Delete Records (both /api/records and /records)
						.requestMatchers(HttpMethod.POST, "/api/records/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/records/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/records/**").hasAnyRole("ADMIN", "ANALYST")
						.requestMatchers(HttpMethod.PUT, "/records/**").hasAnyRole("ADMIN", "ANALYST")
						.requestMatchers(HttpMethod.DELETE, "/records/**").hasRole("ADMIN")

						// 🔴 ADMIN ONLY: User Management
						.requestMatchers("/users/**").hasRole("ADMIN")

						// 🟡 ANALYST & ADMIN: Dashboard
						.requestMatchers("/api/dashboard/**").hasAnyRole("ANALYST", "ADMIN")

						// 🟢 ALL ROLES: View Records (both /api/records and /records)
						.requestMatchers(HttpMethod.GET, "/api/records/**").hasAnyRole("VIEWER", "ANALYST", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/records/**").hasAnyRole("VIEWER", "ANALYST", "ADMIN")

						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setStatus(403);
					response.setContentType("application/json");
					response.getWriter()
							.write("{\"message\": \"Access denied. You are not authorized to perform this action.\"}");
				}).authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(401);
					response.setContentType("application/json");
					response.getWriter().write("{\"message\": \"Unauthorized. Please login first.\"}");
				})).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}