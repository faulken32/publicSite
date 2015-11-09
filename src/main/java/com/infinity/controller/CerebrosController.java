/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.controller.abstractC.AController;
import com.infinity.dto.CandidatOffers;
import com.infinity.dto.ClientOffers;
import com.infinity.dto.PartialCandidat;
import com.infinity.service.CandidatOffersService;
import com.infinity.service.ClientsJobsService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private CandidatOffersService candidatOffersService;

    private final static String mainClass = "blacWhite";

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView home() {

        ModelAndView mv = new ModelAndView("cerebros");
        super.setFooterDisPlayON(mv);
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
      
        
        super.setFooterDisPlayON(mv);
          mv.addObject("normalFooter", true);
        List<ClientOffers> findByTerms = clientsJobsService.findByTerms(departement, text);
        mv.addObject("departement", departement);
        mv.addObject("text", text);

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
        super.setFooterDisPlayON(mv);
        mv.addObject("mainClass", mainClass);
        ClientOffers byId = clientsJobsService.getById(offerId);
        mv.addObject("offers", byId);
        return mv;
    }

    @RequestMapping(value = {"/offers/apply/{offerId}"}, method = RequestMethod.GET)
    public ModelAndView applyOffer(@PathVariable String offerId, HttpServletRequest request) throws IOException {

        ModelAndView mv = new ModelAndView("displayOffers");
        super.setFooterDisPlayON(mv);
        mv.addObject("mainClass", mainClass);

        String candidatId = (String) request.getSession().getAttribute(AController.USER_ID);
        super.setAuth();

        ClientOffers byId = clientsJobsService.getById(offerId);
        mv.addObject("offers", byId);
        if (super.authName != null && !super.authName.isEmpty()
                && !"anonymousUser".equals(super.authName)
                && !"ROLE_CLIENT".equals(super.getAuthType())) {

            ArrayList<CandidatOffers> byOfferId = candidatOffersService.getByOfferId(offerId);
            boolean alreadyApply = false;
            for (CandidatOffers offer : byOfferId) {

                if (candidatId == null
                        ? offer.getPartialCandidat().getId() == null
                        : candidatId.equals(offer.getPartialCandidat().getId())) {

                    alreadyApply = true;
                    break;

                }
            }

            if (!alreadyApply) {

                CandidatOffers candidatOffers = new CandidatOffers();
                candidatOffers.setId(UUID.randomUUID().toString());
                PartialCandidat partialCandidat = new PartialCandidat();
                partialCandidat.setId(candidatId);
                candidatOffers.setOfferId(offerId);
                candidatOffers.setPartialCandidat(partialCandidat);
                candidatOffersService.addCandidatOffers(candidatOffers);
                mv.addObject("applyOk", true);
            } else {
                mv.addObject("alreadyApply", alreadyApply);
            }

        } else {
            mv.addObject("noAuth", true);
        }

        return mv;
    }

}
