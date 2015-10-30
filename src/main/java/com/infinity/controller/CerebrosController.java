/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.controller.abstractC.AController;
import com.infinity.dto.ClientOffers;
import com.infinity.service.ClientsJobsService;
import java.io.IOException;
import java.util.List;
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
public class CerebrosController extends AController {

    @Autowired
    private ClientsJobsService clientsJobsService;

    private final static String mainClass = "blacWhite";

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView candidatRedirect() {

        ModelAndView mv = new ModelAndView("cerebros");
        mv.addObject("noRes", true);
        mv.addObject("page", "home");
        mv.addObject("mainClass", mainClass);

        return mv;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ModelAndView seachPage(String departement, String text) throws IOException {

        ModelAndView mv = new ModelAndView("cerebros");
        mv.addObject("noRes", false);
        mv.addObject("mainClass", mainClass);
        List<ClientOffers> findByTerms = clientsJobsService.findByTerms(departement, text);
        if (!findByTerms.isEmpty()) {

            mv.addObject("res", findByTerms);
            mv.addObject("noRes", false);

        } else {
            mv.addObject("noRes", true);
        }

        mv.addObject("page", "home");
        return mv;
    }

    @RequestMapping(value = {"/offers/{offerId}"}, method = RequestMethod.GET)
    public ModelAndView displayOffer(@PathVariable String offerId) throws IOException {

        ModelAndView mv = new ModelAndView("displayOffers");
        mv.addObject("mainClass", mainClass);
        ClientOffers byId = clientsJobsService.getById(offerId);
        mv.addObject("offers", byId);
        return mv;
    }
}
