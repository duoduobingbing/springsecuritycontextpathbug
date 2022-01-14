package de.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    PasswordEncoder passwordEncoder;
    BasicAuthenticationEntryPoint authEntryPoint;
    AccessDeniedHandlerImpl accessDeniedHandler;
    InMemoryUserDetailsManager inMemDetailsManager;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        authEntryPoint = new CustomBasicAuthenticationEntryPoint();
        authEntryPoint.setRealmName("protected");
        accessDeniedHandler =  new AccessDeniedHandlerImpl();
        inMemDetailsManager = new InMemoryUserDetailsManager(
           List.of(User.builder().username("user").password(passwordEncoder.encode("123456")).authorities("PERM:USER").build())
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        
       auth.userDetailsService(inMemDetailsManager);
    }
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringAntMatchers("/logout"))
                .exceptionHandling(
                        eh -> eh.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeRequests(ar ->
                        ar
                                .antMatchers("/public/**").permitAll()
                                .antMatchers("/", "/index", "/error", "/logout").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(
                    htb -> htb.authenticationEntryPoint(authEntryPoint)
                )
                .logout(l -> l
                        .logoutSuccessUrl("/index?logout=success")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                );

    }

}
