/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.controller.abstractC.AController;
import com.infinity.dto.Candidat;
import com.infinity.dto.CandidatOffers;
import com.infinity.dto.ClientOffers;
import com.infinity.dto.Clients;
import com.infinity.dto.PartialCandidat;
import com.infinity.service.CandidatOffersService;
import com.infinity.service.CandidatService;
import com.infinity.service.ClientsJobsService;
import com.infinity.service.ClientsService;
import com.infinity.service.mail.MailService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author t311372
 */
@Controller
public class CerebrosController extends AController {
    
    
    private static final String NEW_APPLICATION ="Une nouvelle application directe pour votre annonce : ";
    
    @Autowired
    private ClientsJobsService clientsJobsService;

    @Autowired
    private CandidatOffersService candidatOffersService;
    
    @Autowired
    private MailService mailService;
    @Autowired
    private ClientsService clientService;
    @Autowired
    private CandidatService candidatService;

    private final static String mainClass = "blacWhite";

    private List<Character> charToRemove;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView home() throws NoHandlerFoundException {

        ModelAndView mv = new ModelAndView("cerebros");
        super.setFooterDisPlayON(mv);
        mv.addObject("noRes", false);
        mv.addObject("page", "home");
        mv.addObject("mainClass", mainClass);
   
        return mv;
    }

    /**
     *
     * @param departement
     * @param text
     * @return
     * @throws IOException
     * @throws org.springframework.web.servlet.NoHandlerFoundException
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ModelAndView seachPage(String departement, String text) throws IOException, NoHandlerFoundException {
        
        
       
        
        ModelAndView mv = new ModelAndView("cerebros");
        mv.addObject("noRes", false);
        mv.addObject("mainClass", mainClass);
        text = this.removeSpecialChar(text);

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
                candidatOffers.setDate(new Date().toString());
                PartialCandidat partialCandidat = new PartialCandidat();
                partialCandidat.setId(candidatId);
                candidatOffers.setOfferId(offerId);
                candidatOffers.setPartialCandidat(partialCandidat);
                candidatOffersService.addCandidatOffers(candidatOffers);
                this.sendApplicationMail(candidatOffers, byId);
                mv.addObject("applyOk", true);
            } else {
                mv.addObject("alreadyApply", alreadyApply);
            }

        } else {
            mv.addObject("noAuth", true);
        }

        return mv;
    }
    /**
     * 
     * @param text
     * @return 
     */
    private String removeSpecialChar(String text) {

        this.init();

        for (Character charToRemove1 : charToRemove) {

            text = text.replace(charToRemove1, ' ');
                
            
        }

        return text;
    }
    /**
     * 
     * @return charToRemove
     */
    private List<Character> init() {

        charToRemove = new ArrayList<>();

        charToRemove.add('!');
        charToRemove.add('^');
        charToRemove.add('"');

        return this.charToRemove;
    }
    
    
   /**
    *  send an email to the client in case of a new application
    * @param candidatOffers
    * @param clientOffers
    * @throws IOException 
    */
    private void sendApplicationMail(CandidatOffers candidatOffers, ClientOffers clientOffers) throws IOException{
    
        
        String offerTitle = clientOffers.getTitle();
        String idClient = clientOffers.getPartialsClients().getId();
        Clients client = clientService.getById(idClient);
        String email = client.getEmail();
        String candidatID = candidatOffers.getPartialCandidat().getId();
        Candidat candidat = candidatService.getById(candidatID);
        
        String cvContends = candidat.getCvContends();
        
        mailService.send(email, cvContends, CerebrosController.NEW_APPLICATION + offerTitle);
        
        
    }

}
