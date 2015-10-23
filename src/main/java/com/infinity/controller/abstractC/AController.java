/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller.abstractC;

import com.infinity.controller.CandidatController;
import com.infinity.entity.Users;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author t311372
 */
public class AController {

    private static final Logger LOG = LoggerFactory.getLogger(AController.class);

    @Autowired
    protected AuthenticationManagerBuilder authenticationManager;
    protected Authentication auth;
    protected String authName;
    protected final static String CLIENT_ID = "clientId";
    
    
    /**
     * set auth name if logged
     */
    protected void setAuth() {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            this.auth = SecurityContextHolder.getContext().getAuthentication();
            this.authName = auth.getName();                        
        }

    }
    

    protected void authenticateUserAndSetSession(Users user, HttpServletRequest request) {

        try {

            String username = user.getName();
            String password = user.getPass();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

            // generate session if one doesn't exist
            request.getSession();

            token.setDetails(new WebAuthenticationDetails(request));

            AuthenticationManager object = authenticationManager.getObject();
            Authentication authenticateUser = object.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authenticateUser);
            request.getSession().setAttribute(CLIENT_ID, user.getId());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
