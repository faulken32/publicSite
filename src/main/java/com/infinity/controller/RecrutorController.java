package com.infinity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinity.controller.abstractC.AController;
import com.infinity.dao.UsersDao;
import com.infinity.entity.Users;
import com.infinity.exception.UserException;
import com.infinity.service.ClientsService;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RecrutorController extends AController {

    private static final Logger LOG = LoggerFactory.getLogger(RecrutorController.class);

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private ClientsService clientsService;

    @RequestMapping(value = {"/signin/recrutor"}, method = RequestMethod.GET)
    public ModelAndView simpleInscription() {

        ModelAndView mv = new ModelAndView("simple");
        return mv;
    }

    @RequestMapping(value = {"/signin/recrutor"}, method = RequestMethod.POST)
    public String simpleInscriptionForm(String name, String pass, HttpServletRequest request) throws IOException, JsonProcessingException, InterruptedException, ExecutionException {
        
        Users saveUser = null;
        try {
            saveUser = usersDao.saveClient(name, pass);
            
            super.authenticateUserAndSetSession(saveUser, request);
           
            
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
            return "redirect:/signin/recrutor";
        }

        return "redirect:/recrutor/step1";
    }

    @RequestMapping(value = {"/recrutor/step1"}, method = RequestMethod.GET)
    public ModelAndView step1(HttpServletRequest request) {
       
                
        
        String attribute;
        if(request.getSession() !=null){
         
           attribute = (String) request.getSession().getAttribute(CLIENT_ID);
           String toto = "";
           
        }
        
        ModelAndView mv = new ModelAndView("recrutorStep1");
        
        return mv;
    }

    @RequestMapping(value = {"/recrutor/info"}, method = RequestMethod.GET)
    public ModelAndView getRecrutorInfo(HttpServletRequest request) {
        
        String attribute;
        if(request.getSession() !=null){
         
           attribute = (String) request.getSession().getAttribute(CLIENT_ID);
        }
        
        ModelAndView mv = new ModelAndView("getRecrutor");
        return mv;
    }

}
