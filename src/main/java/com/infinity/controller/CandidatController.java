/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.dto.Candidat;
import com.infinity.entity.Users;
import com.infinity.repository.UserRepository;
import com.infinity.service.CandidatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author t311372
 */
@Controller
public class CandidatController {

    private static final Logger LOG = LoggerFactory.getLogger(CandidatController.class);
    
    @Autowired
    private CandidatService candidatService;
    
    @Autowired
    private UserRepository userRepository;
    
    
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String candidatRedirect(){
                
        
        
        return "redirect:/candidat";
    }
    
    
    
    /**
     *
     * @return
     * @throws java.io.IOException
     */

    @RequestMapping(value = {"/candidat"}, method = RequestMethod.GET)
    public ModelAndView candidat() throws Exception{
                
             ModelAndView mv = new ModelAndView("getCandidat");
             
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
             
            
        String name = auth.getName();
        Users findByname = userRepository.findByname(name);
        Candidat candidat = candidatService.getById(findByname.getId());
        
        if (candidat == null) {
            throw new Exception("missing candidat");
        } 
        
               
        
        if ( "listen".equals(candidat.getStatus())) {
            candidat.setStatus("à l'écoute du marché");
        }
        if ( "active".equals(candidat.getStatus())) {
            candidat.setStatus("en recherche active");
        }
        if ( "nosearch".equals(candidat.getStatus())) {
            candidat.setStatus("pas en recherche");
        }
        
        mv.addObject("candidat", candidat);
        LOG.debug("AUTH : "  +name);
        
        return mv;
    }
    
    
//    @RequestMapping(value = {"/getCandidat/{candidatId}"}, method = RequestMethod.GET)
//    public ModelAndView index(@PathVariable String candidatId) throws IOException, Exception {
//        
//        Candidat candidat = candidatService.getById(candidatId);
//        
//        if (candidat == null) {
//            throw new Exception("missing candidat");
//        }
//        ModelAndView mv = new ModelAndView("getCandidat");
//        mv.addObject("candidat", candidat);
//        return mv;
//    }

}
