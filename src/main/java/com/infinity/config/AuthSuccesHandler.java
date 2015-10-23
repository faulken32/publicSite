/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.config;

import com.infinity.dao.UsersDao;
import com.infinity.entity.Users;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author t311372
 */
public class AuthSuccesHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MvcConfiguration.class);
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    protected final static String CLIENT_ID = "clientId";

    @Autowired
    private UsersDao usersDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOG.debug("SUCESS");

        User principal = (User) authentication.getPrincipal();
        if(principal != null && principal.getUsername() != null){
            
            this.setUserIdInSession(request, usersDao.findByName(principal.getUsername()));
            
        } 
        
        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     *
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isClient = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        OUTER:
        for (GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_USER":
                    isUser = true;
                    break OUTER;
                case "ROLE_CLIENT":
                    isClient = true;
                    break OUTER;
            }
        }

        if (isUser) {

            return "/candidat";
        } else if (isClient) {
            return "/recrutor/info";
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * store logged user Id in session
     *
     * @param request
     * @param saveUser
     */
    protected void setUserIdInSession(HttpServletRequest request, Users saveUser) {

        request.getSession().setAttribute(CLIENT_ID, saveUser.getId());

    }

}
