
package it.polimi.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AngGenSecurityConfig
    extends WebSecurityConfigurerAdapter
{

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
        http
        .authorizeRequests()
        .antMatchers("/css/**","/img/**","/js/**","/auth/**","/login/**","/js/angular/**","/**").permitAll()
        .and()
        .authorizeRequests().anyRequest().fullyAuthenticated().and()
        .formLogin().and().csrf()
        .csrfTokenRepository(csrfTokenRepository()).and()
        .addFilterAfter(new it.polimi.boot.CsrfHeaderFilter(), org.springframework.security.web.csrf.CsrfFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository repository = new org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository();
         repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        return encoder;
    }

}
