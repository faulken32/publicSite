/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller.abstractC;

import com.infinity.dao.UsersDao;
import com.infinity.entity.Users;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.ModelAndView;

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
    protected final static boolean noFooter = false;
    protected final static boolean footer = true;

    protected void setFooterDisPlayOff(ModelAndView mv) {

        if (mv != null) {
            mv.addObject("nofooter", noFooter);
        }

    }

    protected void setFooterDisPlayON(ModelAndView mv) {

        if (mv != null) {
            mv.addObject("nofooter", footer);
        }

    }

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
            this.setAuth();
            boolean isUser = false;
            boolean isClient = false;

            Collection<? extends GrantedAuthority> authorities = this.auth.getAuthorities();
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

            } else if (isClient) {
                request.getSession().setAttribute("authType", "client");

            } else {
                throw new IllegalStateException();
            }

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
