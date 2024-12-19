package com.backend.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@Configuration
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class SecurityConfig {

//By making it a bean, Spring can manage the lifecycle of the SecurityFilterChain, ensuring it is properly initialized and available when handling requests.
//	Marking SecurityFilterChain as a bean enables it to be injected into other parts of the application if needed.
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authz -> authz
        .requestMatchers(HttpMethod.POST, "/customer/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/**").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/**").authenticated()
        .requestMatchers(HttpMethod.POST, "/**").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
        .requestMatchers("/swagger-ui/**", "/v3/api-docs").permitAll()  
        .anyRequest().permitAll() 
    )
            .httpBasic(withDefaults())  
            .csrf(csrf -> csrf.disable()) 
            .cors(withDefaults())
            .requiresChannel(channel -> channel
                .anyRequest()
                .requiresInsecure() // Allow non-HTTPS requests
                // .requiresSecure() // Enforce HTTPS
            );

        return http.build();
    }
     @Bean
    public UserDetailsService userDetailsService() {
        // In-memory user store configuration
        UserDetails user = User.builder()
            .username("user") // Hardcoded username
            .password(passwordEncoder().encode("user")) // Hardcoded password (encoded)
            .roles("USER") // Assigning roles
            .build();
        UserDetails admin = User.builder()
            .username("admin") // Hardcoded username
            .password(passwordEncoder().encode("admin")) // Hardcoded password (encoded)
            .roles("ADMIN") // Assigning roles
            .build();

        return new InMemoryUserDetailsManager(user, admin); // Return user details manager
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allow only Swagger UI origin (replace this with  expected origin)
        corsConfiguration.addAllowedOrigin("http://localhost:8080/"); // Expected Origin

        corsConfiguration.setAllowCredentials(true); // Allow cookies/auth
        corsConfiguration.addAllowedHeader("*"); // Allow all headers
        corsConfiguration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)

        // Apply the CORS policy to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);


    return source;
}
@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public-api")
            .pathsToMatch("/customer/**") 
            // .securitySchemes(List.of(new SecurityScheme().name("basicAuth")
            //         .type(SecuritySchemeType.HTTP).scheme("basic")))
            .build();
    }



}
