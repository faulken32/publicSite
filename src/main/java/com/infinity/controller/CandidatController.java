/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.controller.abstractC.AController;
import com.infinity.dao.UsersDao;
import com.infinity.dto.Candidat;
import com.infinity.dto.CandidatOffers;
import com.infinity.dto.ClientOffers;
import com.infinity.dto.Experiences;
import com.infinity.dto.School;
import com.infinity.entity.Users;
import com.infinity.service.CandidatOffersService;

import com.infinity.service.CandidatService;
import com.infinity.service.ClientsJobsService;
import com.infinity.service.ExpService;
import com.infinity.service.SchoolService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author t311372
 */
@Controller
public class CandidatController extends AController {

    private static final Logger LOG = LoggerFactory.getLogger(CandidatController.class);

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private ExpService expService;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private CandidatOffersService candidatOffersService;

    @Autowired
    private ClientsJobsService clientsJobsService;

    private final static String mainClass = "back";
    private final static String mainClassWhite = "blacWhite";

    @RequestMapping(value = {"/home/candidat"}, method = RequestMethod.GET)
    public ModelAndView homeCandidat() {

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("mainClass", mainClass);

        return mv;
    }

    /**
     *
     * @return @throws java.io.IOException
     */
    @RequestMapping(value = {"/candidat"}, method = RequestMethod.GET)
    public ModelAndView candidat() throws Exception {

        boolean noExp = true;
        boolean noSchool = true;
        boolean noApply = true;
        ModelAndView mv = new ModelAndView("getCandidat");
        mv.addObject("mainClass", mainClass);
        mv.addObject("page", "candidat");

        super.setAuth();

        String name = super.authName;
        Users findByname = usersDao.findByName(name);
        Candidat candidat = candidatService.getById(findByname.getId());
        List<Experiences> byIdSearhText = expService.getByIdSearhText(findByname.getId());
        List<School> schoolList = schoolService.getByIdSearhText(findByname.getId());
        List<CandidatOffers> candidatOffersList = candidatOffersService.getByIdSearhText(findByname.getId());
        List<ClientOffers> clientOffersList = new ArrayList<>();

        for (CandidatOffers candidatOffers : candidatOffersList) {

            ClientOffers byId = clientsJobsService.getById(candidatOffers.getOfferId());
            if (byId != null) {
                clientOffersList.add(byId);
            }
        }

        if (clientOffersList.isEmpty()) {
            noApply = true;
        } else {
            noApply = false;
            mv.addObject("noApply", noApply);
            mv.addObject("clientOffersList", clientOffersList);
        }

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
        Map<String, String> dataForGraph = this.getDataForGraph();

        if (dataForGraph != null && !dataForGraph.isEmpty()) {

            mv.addObject("graph", dataForGraph);

        } else {
        
            mv.addObject("graphError", "Renseigner vos expériences pour voir le grpahique de vos compétences");
        }

        return mv;
    }

    @RequestMapping(value = {"/candidat/changePass", "/recrutor/changePass"}, method = RequestMethod.GET)
    public ModelAndView candidatChangePass() throws Exception {

        ModelAndView modelAndView = new ModelAndView("pass");
        modelAndView.addObject("mainClass", mainClass);
        super.setFooterDisPlayOff(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = {"/candidat/changePass", "/recrutor/changePass"}, method = RequestMethod.POST)
    public ModelAndView candidatChangePassPost(String oldPass, String pass, String pass2) throws Exception {

        ModelAndView modelAndView = new ModelAndView("pass");
        modelAndView.addObject("mainClass", mainClass);
        super.setAuth();
        super.setFooterDisPlayOff(modelAndView);

        Users findByName = usersDao.findByName(super.authName);
        boolean old = false;
        boolean newOne = false;
        if (oldPass.equals(findByName.getPass())) {

            old = true;

        } else {
            modelAndView.addObject("errorOld", true);
        }

        if (!pass.equals(pass2)) {
            modelAndView.addObject("errorPass", true);
        } else {
            newOne = true;
        }
        if (newOne && old) {

            findByName.setPass(pass);
            usersDao.saveOrUpdate(findByName);
            modelAndView.addObject("succes", true);
        }

        return modelAndView;
    }

    /**
     * resultat de web service pour l'update des status des candidat par mail
     *
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/updateStatus/{status}/{id}"}, method = RequestMethod.GET)
    public ModelAndView candidatUpdateStatus(@PathVariable String id, @PathVariable String status) throws Exception {

        ModelAndView modelAndView = new ModelAndView("updateStatus");
        modelAndView.addObject("mainClass", mainClass);
        Candidat byId = candidatService.getById(id);
        if (byId != null) {

            byId.setStatus(status);
            candidatService.updateOneById(byId);
        }

        LOG.debug("ID : " + id);
        LOG.debug("status : " + status);
        modelAndView.addObject("mainClass", mainClassWhite);
        super.setFooterDisPlayOff(modelAndView);
        return modelAndView;
    }

    /**
     *
     * @return
     */
    private Map<String, String> getDataForGraph() throws IOException {

        String name = super.authName;
        Users findByname = usersDao.findByName(name);
        Candidat candidat = candidatService.getById(findByname.getId());
        List<Terms.Bucket> agregatedDurationExp = expService.getAgregatedDurationExp(candidat.getId());
        Map<String, String> res = new HashMap<>();
        if (agregatedDurationExp != null || !agregatedDurationExp.isEmpty()) {

            for (Bucket agregatedDurationExp1 : agregatedDurationExp) {

                Text keyAsText = agregatedDurationExp1.getKeyAsText();

                Sum agg = agregatedDurationExp1.getAggregations().get("by_year");

                double value = agg.getValue();
                res.put(keyAsText.toString(), Double.toString(value));
                LOG.debug(keyAsText.toString());
                LOG.debug(Double.toString(value));

            }

        }
        return res;

    }
}
