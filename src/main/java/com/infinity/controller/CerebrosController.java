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

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView candidatRedirect() {

        ModelAndView mv = new ModelAndView("cerebros");
           mv.addObject("noRes" , true);
        mv.addObject("page", "home");
        return mv;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ModelAndView seachPage(String departement, String text) throws IOException {

        ModelAndView mv = new ModelAndView("cerebros");
         mv.addObject("noRes" , false);
        List<ClientOffers> findByTerms = clientsJobsService.findByTerms(departement, text);
        if (!findByTerms.isEmpty()) {

            mv.addObject("res", findByTerms);
             mv.addObject("noRes" , false);

        } else {
            mv.addObject("noRes" , true);
        }

        mv.addObject("page", "home");
        return mv;
    }
}
