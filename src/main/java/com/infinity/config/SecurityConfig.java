/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/**
 *
 * @author t311372
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    
    
    @Autowired
    private  BasicDataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select name,pass,enable from users where name=?")
                .authoritiesByUsernameQuery(
                        "select name, role from users_roles where name=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                
                .antMatchers("/register/**").access("hasRole('ROLE_USER')")
                .antMatchers("/candidat/**").access("hasRole('ROLE_USER')")
                .antMatchers("/recrutor/**").access("hasRole('ROLE_CLIENT')")
                .antMatchers("/signin/**").permitAll()            
                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .successHandler(new AuthSuccesHandler())
                .usernameParameter("name").passwordParameter("pass")
         
                .and()
                .logout()                                                          
			.logoutUrl("/logout")                                              
			.logoutSuccessUrl("/")                                          
//			.logoutSuccessHandler(logoutSuccessHandler)                              
			.invalidateHttpSession(true);                                      
////			.addLogoutHandler(logoutHandler)                                        
//			.deleteCookies(cookieNamesToClear)
                
             

    }
    

}
