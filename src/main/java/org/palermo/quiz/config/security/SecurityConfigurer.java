package org.palermo.quiz.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
//@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    public final DataSource dataSource;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT EMAIL, PASSWORD, true FROM ACCOUNT WHERE EMAIL = ?")
                .authoritiesByUsernameQuery("SELECT 'eduardo', 'PLAY' FROM sysibm.sysdummy1 WHERE '1' = '1' OR '1' = ?")
                .groupAuthoritiesByUsername("SELECT 1 FROM sysibm.sysdummy1 WHERE '1' = ?");

        /*

        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN")
                .and()
                .withUser("eduardo").password(passwordEncoder().encode("eduardo")).roles("USER");
         */
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .requiresChannel().anyRequest().requiresSecure() // Demands HTTPS
        .and()
                //.csrf().disable()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/room").permitAll()
                .antMatchers("/webjars/**").permitAll()
                //.antMatchers("/js/**").permitAll()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/test.html").permitAll()
                .antMatchers("/new_test*.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/performLogin")
                .defaultSuccessUrl("/selectRoom", true)
                .failureUrl("/login?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/performLogout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/logout.html");
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
