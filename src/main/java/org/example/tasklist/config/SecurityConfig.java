package org.example.tasklist.config;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.security.JwtTokenFilter;
import org.example.tasklist.security.JwtTokenProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class SecurityConfig {

    private final ApplicationContext applicationContext;
    private final JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(
                                        (request, response, exception) -> {
                                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                            response.getWriter()
                                                    .write("Unauthorized.");
                                        }
                                )
                                .accessDeniedHandler(
                                        (request, response, exception) -> {
                                            response.setStatus(
                                                    HttpStatus.FORBIDDEN
                                                            .value()
                                            );
                                            response.getWriter()
                                                    .write("Unauthorized.");
                                        }
                                )
                )
                .authorizeHttpRequests(configurer ->
                        configurer.requestMatchers("/tasklist/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers("/tasklist/swagger-ui/**")
                                .permitAll()
                                .requestMatchers("/tasklist/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers("/tasklist/graphiql")
                                .permitAll()
                                .anyRequest().authenticated()
                )
//                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new JwtTokenFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public MethodSecurityExpressionHandler expressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
