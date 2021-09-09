package com.example.lessoncrud.config;

import com.example.lessoncrud.security.JwtConfigure;
import com.example.lessoncrud.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final JwtTokenProvider jwtTokenProvider;

  public SecurityConfiguration(UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
    this.userDetailsService = userDetailsService;
    this.jwtTokenProvider = jwtTokenProvider;
  }


  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//        .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////        .inMemoryAuthentication()
////        .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
////        .and()
////        .withUser("user").password(passwordEncoder().encode("user123")).roles("USER");
//  }


  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable()
        .headers().frameOptions().disable()
        .and()
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/user/**").permitAll()
        .antMatchers("/api/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigure(jwtTokenProvider))
    ;
  }

//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
}
