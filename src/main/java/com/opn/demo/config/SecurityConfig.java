  package com.opn.demo.config;
  
  import com.opn.demo.filter.JWTAccessDeniedHandler;
  import com.opn.demo.filter.JWTAuthenticationEntryPoint;
  import com.opn.demo.filter.JWTAuthenticationFilter;
  import lombok.RequiredArgsConstructor;
  import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
  import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.web.SecurityFilterChain;
  import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
  import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
  import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
  
  @Configuration
  @EnableWebSecurity
  @EnableMethodSecurity
  @RequiredArgsConstructor
  public class SecurityConfig {
  
  
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
  
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
  
    protected static final String[] WHITELIST_URLS = {
          "/api/v1/auth/login",
          "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
          .authorizeHttpRequests(configurer -> configurer
              .requestMatchers(WHITELIST_URLS).permitAll()
              .requestMatchers(PathRequest.toH2Console()).permitAll()
              .anyRequest().authenticated())
          .csrf(AbstractHttpConfigurer::disable)
          .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
          .exceptionHandling(
              configurer ->
                  configurer
                      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                      .accessDeniedHandler(jwtAccessDeniedHandler))
          .build();
    }
  
    @Bean
    public AuthenticationManager authenticationManagerBean(
          AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }
  
  }
