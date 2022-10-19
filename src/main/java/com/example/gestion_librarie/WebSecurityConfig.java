package com.example.gestion_librarie;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("******configure********");
        http.authorizeRequests().antMatchers("/include/auteur.xhtml").hasAnyAuthority("USER")
                .antMatchers("/javax.faces.resource/**, /resources/**").permitAll().anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/auteur.xhtml", true).permitAll().and().logout()
                .permitAll().and().exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();
        
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");

    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * @Bean
     *
     * @Override public UserDetailsService userDetailsService() { UserDetails user =
     * User.withDefaultPasswordEncoder() .username("user") .password("password")
     * .roles("USER") .build();
     *
     * return new InMemoryUserDetailsManager(user); }
     */
}
