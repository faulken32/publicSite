/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinity.controller.abstractC.AController;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
public class InscriptionController extends AController {

    private static final Logger LOG = LoggerFactory.getLogger(InscriptionController.class);

    @Autowired
    private CandidatService candidatService;
    @Autowired
    private ExpService expService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UsersDao usersDao;

    private final static String mainClass = "back";

    @RequestMapping(value = {"/signin"}, method = RequestMethod.GET)
    public ModelAndView simpleInscription() {

        ModelAndView mv = new ModelAndView("simple");
        mv.addObject("mainClass", mainClass);
        return mv;
    }

    /**
     *
     * @param <T>
     * @param name
     * @param pass
     * @param request
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping(value = {"/signin"}, method = RequestMethod.POST)
    public <T extends Object> T simpleInscriptionPost(String name, String pass, HttpServletRequest request) throws IOException {

        String res = null;

        try {
            Users saveUser = usersDao.saveUser(name, pass);
            super.authenticateUserAndSetSession(saveUser, request);
            return (T) ((T) "redirect:/register/step1/" + saveUser.getId());

        } catch (UserException ex) {
            LOG.error(ex.getMessage());
            res = "error";

        }

        ModelAndView mv = new ModelAndView("simple");
        mv.addObject("mainClass", mainClass);
        mv.addObject("error", res);

        return (T) mv;
    }

    @RequestMapping(value = {"/register/step1/{id}", "/register/step1/{id}/{update}"}, method = RequestMethod.GET)
    public ModelAndView index(@PathVariable String id, @PathVariable Optional<String> update) throws IOException {

        ModelAndView mv = new ModelAndView("step1");
        super.setFooterDisPlayOff(mv);
        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Candidat byId = candidatService.getById(id);

        CandidatEnum candidatEnum = new CandidatEnum();
        mv.addObject("status", candidatEnum.getStatusList());
        mv.addObject("name", name);
        mv.addObject("candidat", byId);
        mv.addObject("mainClass", mainClass);
        if (update.isPresent() && "true".endsWith(update.get())) {

            mv.addObject("update", true);
        }

        return mv;
    }

    @RequestMapping(value = {"/register/step1"}, method = RequestMethod.POST)
    public <T extends Object> T indexForm(@ModelAttribute Candidat candidat, String update, String candidatId) throws JsonProcessingException {

        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        CandidatEnum candidatEnum = new CandidatEnum();
        candidat.setId(candidatId);

        Date date = new Date();
        String url = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        url = "redirect:/candidat";

        boolean errorL = false;
        boolean errorM = false;
        ModelAndView mv = new ModelAndView("step1");
        super.setFooterDisPlayOff(mv);
        mv.addObject("status", candidatEnum.getStatusList());
        mv.addObject("selectedStatus", candidat.getStatus());
        mv.addObject("name", name);
        mv.addObject("mainClass", mainClass);

        if (candidat.getLanguage() == null || candidat.getLanguage().isEmpty()) {

            errorL = true;
            mv.addObject("erroL", errorL);

        } else {

            ArrayList<String> language = candidat.getLanguage();
            for (Iterator<String> iterator = language.iterator(); iterator.hasNext();) {
                if (iterator.next().isEmpty()) {
                    iterator.remove();
                }
            }
            candidat.setLanguage(language);
        }

        if (candidat.getMobilite() == null || candidat.getMobilite().isEmpty()) {
            errorM = true;
            mv.addObject("erroM", errorM);
        } else {
            List mobilite = candidat.getMobilite();
            for (Iterator<String> iterator = mobilite.iterator(); iterator.hasNext();) {

                if (iterator.next().isEmpty()) {
                    iterator.remove();
                }
            }
            candidat.setMobilite(mobilite);
        }
        if (errorL || errorM) {
            return (T) mv;
        } else {

            if (!update.isEmpty() && "true".equals(update)) {
                candidat.setUpdateDate(simpleDateFormat.format(date));
            } else {
                candidat.setEnterDate(simpleDateFormat.format(date));
            }
            candidatService.updateOneById(candidat);
            return (T) url;
        }
    }

    @RequestMapping(value = {"/register/step2/{expId}/{update}", "/register/step2/{expId}", "/register/step2"}, method = RequestMethod.GET)
    public ModelAndView step2(@PathVariable Optional<String> expId, @PathVariable Optional<String> update) throws IOException {

        ModelAndView mv = new ModelAndView("step2");
        super.setFooterDisPlayOff(mv);
        mv.addObject("mainClass", mainClass);
        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Users findByName = usersDao.findByName(name);

        if (expId.isPresent()) {

            Experiences byId = expService.getById(expId.get());
            List<String> tecnoList = byId.getTecnoList();
            StringBuilder stringBuilder = new StringBuilder();
            for (String tecnoList1 : tecnoList) {

                stringBuilder.append(tecnoList1).append(',');
            }
            int length = stringBuilder.length() - 1;
            char charAt = stringBuilder.charAt(length);

            if (charAt == ',') {
                stringBuilder.deleteCharAt(length);
            }

            if (update.isPresent() && "true".equals(update.get())) {
                mv.addObject("update", true);
            } else {
                mv.addObject("update", false);
            }
            mv.addObject("techno", stringBuilder);
            mv.addObject("exp", byId);
        }

        mv.addObject("candidatId", findByName.getId());

        return mv;
    }

    
    
    /**
     * update an candantidate exp
     * @param exp
     * @param expId
     * @param technoListblock
     * @param update
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws ParseException
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @RequestMapping(value = {"/register/step2/{expId}", "/register/step2"}, method = RequestMethod.POST)
    public String step2Form(@ModelAttribute Experiences exp, @PathVariable Optional<String> expId, String technoListblock,
            String update)
            throws JsonProcessingException, IOException, ParseException, InterruptedException, ExecutionException {
        
        if (!technoListblock.isEmpty()) {
            
         
            String technoListblockTrim = StringUtils.trimAllWhitespace(technoListblock);
          
            String[] split = technoListblockTrim.split(",");
            List<String> tecnoList = new ArrayList<>();
            tecnoList.addAll(Arrays.asList(split));
//            for (String tecnoList1 : tecnoList) {
//                
//                String trimAllWhitespace = StringUtils.trimAllWhitespace(tecnoList1);
//                tecnoList1 = trimAllWhitespace;
//            }
            exp.setTecnoList(tecnoList);

        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = simpleDateFormat.parse(exp.getStart());
        Date end = simpleDateFormat.parse(exp.getEnd());

        long diff = end.getTime() - start.getTime();
        float nbYear = diff / 31536000000.0f;

        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Users findByName = usersDao.findByName(name);
        Candidat candidat = candidatService.getById(findByName.getId());

        candidat.setId(findByName.getId());

        PartialCandidat partialCandidat = new PartialCandidat();
        partialCandidat.setId(candidat.getId());
        partialCandidat.setName(candidat.getName());

        exp.setDuration(nbYear);
        exp.setPartialCandidat(partialCandidat);

        if ("true".equals(update)) {

            exp.setId(expId.get());
            expService.updateById(exp);

        } else {
            exp.setId(null);
            String addExp = expService.addExp(exp);
            //Experiences byId = expService.getById(addExp);
            exp.setId(addExp);
            expService.updateById(exp);
        }

        return "redirect:/candidat";
    }

    @RequestMapping(value = {"/register/step3/{schoolId}", "/register/step3"}, method = RequestMethod.GET)
    public ModelAndView step3(@PathVariable Optional<String> schoolId) {

        ModelAndView mv = new ModelAndView("step3");
        super.setFooterDisPlayOff(mv);
        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Users findByName = usersDao.findByName(name);

        if (schoolId.isPresent()) {

            School byId = schoolService.getById(schoolId.get());
            mv.addObject("school", byId);
        }

        mv.addObject("candidatId", findByName.getId());
        mv.addObject("mainClass", mainClass);

        return mv;
    }

    @RequestMapping(value = {"/register/step3/{schoolId}", "/register/step3"}, method = RequestMethod.POST)
    public String step3Form(@ModelAttribute("school") School school, @PathVariable Optional<String> schoolId) throws JsonProcessingException, IOException, InterruptedException, ExecutionException {

        auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Users findByName = usersDao.findByName(name);

        PartialCandidat partialCandidat1 = new PartialCandidat();
        partialCandidat1.setId(findByName.getId());
        partialCandidat1.setName(name);

        if (schoolId.isPresent()) {

            school.setPartialCandidat(partialCandidat1);
            school.setId(schoolId.get());
            schoolService.updateOneById(school);
        } else {

            school.setPartialCandidat(partialCandidat1);

            String addSchoolId = schoolService.addSchool(school);
            school.setId(addSchoolId);
            schoolService.updateOneById(school);

        }

        return "redirect:/candidat/";
    }

    @RequestMapping(value = {"/register/del/{expId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity delExpbyId(@PathVariable String expId) throws IOException {

        ResponseEntity<String> responseEntity = null;
        try {
            expService.deleteById(expId);
            responseEntity = new ResponseEntity<>("OK ", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            responseEntity = new ResponseEntity<>("error ", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = {"/register/del/school/{schoolId}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity delSchoolbyId(@PathVariable String schoolId) throws IOException {

        ResponseEntity<String> responseEntity = null;
        try {
            schoolService.deleteById(schoolId);
            responseEntity = new ResponseEntity<>("OK ", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            responseEntity = new ResponseEntity<>("error ", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
