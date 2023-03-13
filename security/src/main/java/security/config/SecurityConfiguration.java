package security.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    final private JwtAuthenticationFilter jwtAuthFilter;
    final private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()//no need authenticated
                .anyRequest()
                .authenticated()//any other all need authenticated
                .and()
                .sessionManagement() //session management->
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//each session need to be authenticated, each time new session
                .and()
                .authenticationProvider(authenticationProvider) //which auth provider we'll use
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);   //jwt filter,filter before UsernamePasswordAuthenticationFilter

        return httpSecurity.build();
    }
}
