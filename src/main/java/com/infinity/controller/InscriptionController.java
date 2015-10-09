/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinity.dao.UsersDao;
import com.infinity.dto.Candidat;
import com.infinity.dto.CandidatEnum;
import com.infinity.dto.Experiences;
import com.infinity.dto.PartialCandidat;
import com.infinity.dto.School;
import com.infinity.entity.Users;
import com.infinity.exception.UserException;
import com.infinity.service.CandidatService;
import com.infinity.service.ExpService;
import com.infinity.service.SchoolService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author t311372
 */
@Controller
public class InscriptionController {

    private static final Logger LOG = LoggerFactory.getLogger(InscriptionController.class);

    @Autowired
    private CandidatService candidatService;
    @Autowired
    private ExpService expService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UsersDao usersDao;

//    @Autowired
//    protected AuthenticationManager authenticationManager;
    @Autowired
    protected AuthenticationManagerBuilder authenticationManager;

    @RequestMapping(value = {"/signin"}, method = RequestMethod.GET)
    public ModelAndView simpleInscription() {

        ModelAndView mv = new ModelAndView("simple");

        return mv;
    }

    /**
     *
     * @param <T>
     * @param name
     * @param pass
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/signin"}, method = RequestMethod.POST)
    public <T extends Object> T simpleInscriptionPost(String name, String pass, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String res = null;

        try {
            Users saveUser = usersDao.saveUser(name, pass);
            this.authenticateUserAndSetSession(saveUser, request);
            return (T) ((T) "redirect:/register/step1/" + saveUser.getId());

        } catch (UserException ex) {
            LOG.error(ex.getMessage());
            res = "error";

        }

        ModelAndView mv = new ModelAndView("simple");
        mv.addObject("error", res);

        return (T) mv;
    }

    @RequestMapping(value = {"/register/step1/{id}", "/register/step1/{id}/{update}"}, method = RequestMethod.GET)
    public ModelAndView index(@PathVariable String id, @PathVariable Optional<String> update) {

        ModelAndView mv = new ModelAndView("step1");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Candidat byId = candidatService.getById(id);

        CandidatEnum candidatEnum = new CandidatEnum();
        mv.addObject("status", candidatEnum.getStatusList());
        mv.addObject("name", name);
        mv.addObject("candidat", byId);
        if (update.isPresent() && "true".endsWith(update.get())) {

            mv.addObject("update", true);
        }

        return mv;
    }

    @RequestMapping(value = {"/register/step1/{id}"}, method = RequestMethod.POST)
    public String indexForm(@ModelAttribute Candidat candidat, @PathVariable String id, String update) throws JsonProcessingException {

        Date date = new Date();
        String url = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        candidat.setEnterDate(simpleDateFormat.format(date));
        candidatService.updateOneById(candidat);
        if("true".equals(update)){
             url  = "redirect:/candidat";
        } else {
            url  = "redirect:/register/step2/" + id;
        }
     
        return url;

    }

    @RequestMapping(value = {"/register/step2/{id}"}, method = RequestMethod.GET)
    public ModelAndView step2(@PathVariable String id) {

        ModelAndView mv = new ModelAndView("step2");
        mv.addObject("candidatId", id);
        return mv;
    }

    @RequestMapping(value = {"/register/step2/{candidatid}"}, method = RequestMethod.POST)
    public String step2Form(@ModelAttribute Experiences exp, @PathVariable String candidatid, String technoListblock) throws JsonProcessingException, IOException, ParseException, InterruptedException, ExecutionException {

//        Candidat byId = candidatService.getById(id);
//        
//        PartialCandidat partialCandidat = new PartialCandidat();
//        partialCandidat.setId(id);
//        partialCandidat.setName(byId.getName());
//        experiences.setPartialCandidat(partialCandidat);
        if (!technoListblock.isEmpty()) {

            String[] split = technoListblock.split(",");
            List<String> tecnoList = new ArrayList<>();
            tecnoList.addAll(Arrays.asList(split));
            exp.setTecnoList(tecnoList);

        }

        exp.setId(null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = simpleDateFormat.parse(exp.getStart());
        Date end = simpleDateFormat.parse(exp.getEnd());

        long diff = end.getTime() - start.getTime();
        float nbYear = diff / 31536000000.0f;

        Candidat candidat = candidatService.getById(candidatid);
        candidat.setId(candidatid);

        PartialCandidat partialCandidat = new PartialCandidat();
        partialCandidat.setId(candidat.getId());
        partialCandidat.setName(candidat.getName());

        exp.setDuration(nbYear);
        exp.setPartialCandidat(partialCandidat);

        String addExp = expService.addExp(exp);
        Experiences byId = expService.getById(addExp);
        byId.setId(addExp);
        expService.updateById(byId);

        return "redirect:/register/step3/" + candidatid;
    }

    @RequestMapping(value = {"/register/step3/{candidatid}"}, method = RequestMethod.GET)
    public ModelAndView step3(@PathVariable String candidatid) {

        ModelAndView mv = new ModelAndView("step3");
        mv.addObject("candidatId", candidatid);

        return mv;
    }

    @RequestMapping(value = {"/register/step3/{candidatid}"}, method = RequestMethod.POST)
    public String step3Form(@ModelAttribute("school") School school, @PathVariable String candidatid) throws JsonProcessingException, IOException {

        Candidat byId = candidatService.getById(candidatid);
        PartialCandidat partialCandidat1 = new PartialCandidat();
        partialCandidat1.setId(candidatid);
        partialCandidat1.setName(byId.getName());

        school.setPartialCandidat(partialCandidat1);

        schoolService.addSchool(school);

        return "redirect:/register/step3/" + candidatid;
    }

    private void authenticateUserAndSetSession(Users user, HttpServletRequest request) {

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

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
