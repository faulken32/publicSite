/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.dao.UsersDao;
import com.infinity.dto.Candidat;
import com.infinity.dto.Experiences;
import com.infinity.dto.School;
import com.infinity.entity.Users;
import com.infinity.repository.UserRepository;
import com.infinity.service.CandidatService;
import com.infinity.service.ExpService;
import com.infinity.service.SchoolService;
import java.util.ArrayList;
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
    private ExpService expService;

    
    
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private SchoolService schoolService;
    
    

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String candidatRedirect() {

        return "redirect:/candidat";
    }

    /**
     *
     * @return @throws java.io.IOException
     */
    @RequestMapping(value = {"/candidat"}, method = RequestMethod.GET)
    public ModelAndView candidat() throws Exception {

        boolean noExp = true;
        boolean noSchool = true;

        ModelAndView mv = new ModelAndView("getCandidat");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        Users findByname = usersDao.findByName(name);
        Candidat candidat = candidatService.getById(findByname.getId());
        ArrayList<Experiences> byIdSearhText = expService.getByIdSearhText(findByname.getId());
        ArrayList<School> schoolList = schoolService.getByIdSearhText(findByname.getId());

        if (byIdSearhText.isEmpty()) {

            noExp = true;

        } else {

            noExp = false;
            mv.addObject("exp", byIdSearhText);
        }

        if (schoolList.isEmpty()) {

            noSchool = true;

        } else {

            noSchool = false;
            mv.addObject("school", schoolList);
        }

        mv.addObject("noExp", noExp);
        mv.addObject("noSchool", noSchool);

        if (candidat == null) {
            throw new Exception("missing candidat");
        }

        if ("listen".equals(candidat.getStatus())) {
            candidat.setStatus("à l'écoute du marché");
        }
        if ("active".equals(candidat.getStatus())) {
            candidat.setStatus("en recherche active");
        }
        if ("nosearch".equals(candidat.getStatus())) {
            candidat.setStatus("pas en recherche");
        }

        mv.addObject("candidat", candidat);
        LOG.debug("AUTH : " + name);

        return mv;
    }

    
    @RequestMapping(value = {"/candidat/changePass"}, method = RequestMethod.GET)
    public ModelAndView candidatChangePass() throws Exception {

        ModelAndView modelAndView = new ModelAndView("pass");

        return modelAndView;
    }
    
    
     @RequestMapping(value = {"/candidat/changePass"}, method = RequestMethod.POST)
    public ModelAndView candidatChangePassPost(String oldPass, String pass , String pass2) throws Exception {

        
        ModelAndView modelAndView = new ModelAndView("pass");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Users findByName = usersDao.findByName(name);
        
         if (oldPass.equals(findByName.getPass())) {
             
         } else {
             modelAndView.addObject("errorOld", true);
         }
         
         if (!pass.equals(pass2)) {
             modelAndView.addObject("errorPass", true);
         }
        
        return modelAndView;
    }
}
