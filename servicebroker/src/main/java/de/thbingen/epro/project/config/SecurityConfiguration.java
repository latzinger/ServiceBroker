/**
 * Web-Security Configuration.
 *
 * @since 1.0
 * @author larsatzinger
 * @version 1.0
 */

package de.thbingen.epro.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO add authentication provider
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO add antMatchers and fullyAuthenticated
        http
                .authorizeRequests().anyRequest()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf();
    }
}
