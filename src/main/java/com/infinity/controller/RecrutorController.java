package com.infinity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinity.controller.abstractC.AController;
import com.infinity.dao.UsersDao;
import com.infinity.dto.ClientOffers;
import com.infinity.dto.Clients;
import com.infinity.dto.PartialsClients;
import com.infinity.dto.TechnoCriteria;
import com.infinity.entity.Users;
import com.infinity.exception.UserException;
import com.infinity.service.ClientsJobsService;
import com.infinity.service.ClientsService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecrutorController extends AController {

    private static final Logger LOG = LoggerFactory.getLogger(RecrutorController.class);

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private ClientsService clientsService;
    @Autowired
    private ClientsJobsService clientsJobsService;

    private final static String mainClass = "back";

    @RequestMapping(value = {"/signin/recrutor"}, method = RequestMethod.GET)
    public ModelAndView simpleInscription() {

        ModelAndView mv = new ModelAndView("simple");
        super.setFooterDisPlayOff(mv);
        mv.addObject("mainClass", mainClass);
        return mv;
    }

    @RequestMapping(value = {"/home/recrutor"}, method = RequestMethod.GET)
    public ModelAndView homeRecrutor() {

        ModelAndView mv = new ModelAndView("homeRecrutor");
        super.setFooterDisPlayOff(mv);
        mv.addObject("mainClass", mainClass);
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
    public ModelAndView step1(HttpServletRequest request) throws IOException {

        String attribute;
        ModelAndView mv = new ModelAndView("recrutorStep1");
        super.setFooterDisPlayOff(mv);
        mv.addObject("mainClass", mainClass);
        if (request.getSession() != null) {

            attribute = (String) request.getSession().getAttribute(CLIENT_ID);
            Clients byId = clientsService.getById(attribute);
            mv.addObject("client", byId);

        }

        return mv;
    }

    @RequestMapping(value = {"/recrutor/step1"}, method = RequestMethod.POST)
    public String step1(@ModelAttribute Clients clients) throws IOException, InterruptedException, ExecutionException {

        if (clients != null) {

            clientsService.updateOneById(clients);
        }

        return "redirect:/recrutor/info";
    }

    @RequestMapping(value = {"/recrutor/info"}, method = RequestMethod.GET)
    public ModelAndView getRecrutorInfo(HttpServletRequest request) throws IOException {

        String attribute;
        ModelAndView mv = new ModelAndView("getRecrutor");
        super.setFooterDisPlayOff(mv);
        mv.addObject("mainClass", mainClass);
        if (request.getSession() != null) {

            attribute = (String) request.getSession().getAttribute(CLIENT_ID);
            Clients byId = clientsService.getById(attribute);

            ArrayList<ClientOffers> all = clientsJobsService.getAllByClientId(byId.getId());

            mv.addObject("all", all);
            mv.addObject("client", byId);

        }

        return mv;
    }

    @RequestMapping(value = {"/recrutor/step2/{jobId}", "/recrutor/step2"}, method = RequestMethod.GET)
    public ModelAndView step2(HttpServletRequest request, @PathVariable Optional<String> jobId) throws IOException {

        String attribute;
        ClientOffers byId = null;
        ModelAndView mv = new ModelAndView("recrutorStep2");
        mv.addObject("mainClass", mainClass);
        super.setFooterDisPlayOff(mv);
        if (request.getSession() != null) {

            attribute = (String) request.getSession().getAttribute(CLIENT_ID);

            if (jobId.isPresent()) {
                byId = clientsJobsService.getById(jobId.get());
                mv.addObject("jobs", byId);
            }

        }

        return mv;
    }

    @RequestMapping(value = {"/recrutor/step2/{jobId}", "/recrutor/step2"}, method = RequestMethod.POST)
    public String step2Post(HttpServletRequest request, @PathVariable Optional<String> jobId, @ModelAttribute ClientOffers clientOffers) throws IOException, InterruptedException, ExecutionException {

        String attribute;
        ClientOffers fromDb = null;
        ModelAndView mv = new ModelAndView("recrutorStep2");
        mv.addObject("mainClass", mainClass);
         super.setFooterDisPlayOff(mv);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
        Date date = new Date();
        String format = simpleDateFormat.format(date);

        if (request.getSession() != null) {

            attribute = (String) request.getSession().getAttribute(CLIENT_ID);
            Clients byId = clientsService.getById(attribute);

            if (jobId.isPresent()) {

                fromDb = clientsJobsService.getById(jobId.get());

                PartialsClients partialsClients = new PartialsClients();
                partialsClients.setName(byId.getName());
                partialsClients.setId(attribute);

                clientOffers.setLastUpdateDate(format);

                clientOffers.setPartialsClients(partialsClients);
                clientOffers.setTechnoCriterias(fromDb.getTechnoCriterias());
                clientsJobsService.updateOneById(clientOffers);
                mv.addObject("jobs", clientOffers);
            } else {
                if (clientOffers != null) {
                    clientOffers.setLastUpdateDate(format);
                    String addJobs = clientsJobsService.addJobs(clientOffers);
                    clientOffers.setId(addJobs);
                    PartialsClients partialsClients = new PartialsClients();
                    partialsClients.setName(byId.getName());
                    partialsClients.setId(attribute);
                    clientOffers.setPartialsClients(partialsClients);
                    clientsJobsService.updateOneById(clientOffers);

                    mv.addObject("jobs", clientOffers);
                }
            }

        }

        return "redirect:/recrutor/step2/" + clientOffers.getId();
    }

    @RequestMapping(value = {"/recrutor/step2/criteria/updateAdd/{offerId}/{clienId}"}, method = RequestMethod.POST)
    public String addOrUpdateCriteria(@ModelAttribute ClientOffers clientOffers,
            @PathVariable String clienId,
            @PathVariable String offerId, @ModelAttribute TechnoCriteria technoCriteria) throws IOException, InterruptedException, ExecutionException {

        ClientOffers jobs = clientsJobsService.getById(offerId);

        if (jobs != null && jobs.getTechnoCriterias() == null) {

            jobs.setTechnoCriterias(new ArrayList<>());
        }

        if (jobs != null && jobs.getTechnoCriterias() != null) {

            if (jobs.getTechnoCriterias().isEmpty()) {

                ArrayList<TechnoCriteria> technoList = new ArrayList<>();

                technoList.add(technoCriteria);
                jobs.setTechnoCriterias(technoList);
                clientsJobsService.updateOneById(jobs);
            } else {

                ArrayList<TechnoCriteria> technoCriterias = jobs.getTechnoCriterias();
                technoCriterias.add(technoCriteria);
                jobs.setTechnoCriterias(technoCriterias);
                clientsJobsService.updateOneById(jobs);
            }

        }
        return "redirect:/recrutor/step2/" + offerId;
    }

    @RequestMapping(value = {"/recrutor/step2/criteria/techno/update/{offerId}/{clienId}"}, method = RequestMethod.POST)
    public String UpdateCriteriaTechnoList(HttpServletRequest request, @PathVariable String offerId) throws IOException, InterruptedException, ExecutionException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ArrayList<TechnoCriteria> technoList = new ArrayList<>();

        for (int i = 0; i < parameterMap.size(); i++) {
            TechnoCriteria technoCriteria = new TechnoCriteria();

            if (parameterMap.containsKey("technoName-" + i)) {

                technoCriteria.setTechnoName(parameterMap.get("technoName-" + i)[0]);

            }
            if (parameterMap.containsKey("expDurationStart-" + i)) {

                technoCriteria.setExpDurationStart(Integer.valueOf(parameterMap.get("expDurationStart-" + i)[0]));

            }
            if (parameterMap.containsKey("expDurationEnd-" + i)) {

                technoCriteria.setExpDurationEnd(Integer.valueOf(parameterMap.get("expDurationEnd-" + i)[0]));

            }
            if (technoCriteria.getTechnoName() != null) {
                technoList.add(technoCriteria);
            }

        }
        ClientOffers fromDb = clientsJobsService.getById(offerId);
        fromDb.setTechnoCriterias(technoList);
        clientsJobsService.updateOneById(fromDb);

        return "redirect:/recrutor/step2/" + offerId;
    }

}
