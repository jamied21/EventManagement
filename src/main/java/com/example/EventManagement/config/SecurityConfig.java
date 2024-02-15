/*
 * package com.example.EventManagement.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain;
 * 
 * import com.example.EventManagement.Services.IUserService;
 * 
 * @EnableWebSecurity public class SecurityConfig {
 * 
 * @Autowired private IUserService userService; // Inject your UserService
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
 * Exception { http // Use lambda DSL for authorization rules
 * .authorizeHttpRequests((authz) -> authz.requestMatchers("/login").permitAll()
 * // Allow access to login // endpoint .antMatchers("/admin").hasRole("ADMIN")
 * // Require ADMIN role for specific URL .anyRequest().authenticated() //
 * Require authentication for other requests ).formLogin().loginPage("/login")
 * // Custom login page URL .usernameParameter("username") // Adjust parameters
 * as needed .passwordParameter("password").defaultSuccessUrl("/home") //
 * Redirect after successful login .failureUrl("/login?error") // Redirect on
 * login failure .and().logout().logoutUrl("/logout") // Custom logout URL
 * .logoutSuccessUrl("/login?logout") // Redirect after logout
 * .and().csrf().disable(); // Disable CSRF for simplicity (consider enabling in
 * production) return http.build(); }
 * 
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
 * throws Exception { auth.userDetailsService(userDetailsService); }
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); // Use BCrypt for password hashing } }
 */