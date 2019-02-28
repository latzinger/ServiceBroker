package de.thbingen.epro.project.config;

import de.thbingen.epro.project.web.security.userdetails.UserInfoDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security Configuration for REST-API.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserInfoDetailsService userInfoDetailsService;

    @Value("#{new Boolean('${osb-security.permit-all}')}")
    private boolean permit_all;


    @Value("#{new Boolean('${osb-security.use-db}')}")
    private boolean useDb;

    @Value("${osb-security.username}")
    private String username;

    @Value("${osb-security.password}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.debug("AUTH uses DB: " + useDb);
        if (useDb) {
            auth.authenticationProvider(authenticationProvider());
        } else {
            log.debug("Using username=" + username + "  password=" + password);
            auth.inMemoryAuthentication().withUser(username).password("{noop}" + password).roles("USER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("AUTH permit-all: " + permit_all);
        if (permit_all) {
            http
                    .antMatcher("/**")
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                    .csrf().disable();
        } else {
            http
                    .authorizeRequests()
                    .antMatchers("/**")
                    .fullyAuthenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }

    /**
     * {@link DaoAuthenticationProvider} for using DB for {@link org.springframework.security.core.userdetails.UserDetails}
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userInfoDetailsService);
        authProvider.setPasswordEncoder(noOpPasswordEncoder());
        return authProvider;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public NoOpPasswordEncoder noOpPasswordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
