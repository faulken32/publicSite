/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import com.infinity.dto.ClientOffers;
import com.infinity.service.ClientsJobsService;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller in order to generate site map .txt file
 *
 * @author t311372
 */
@Controller
public class SiteMapController {

    private static final String URL = "https://cerebros-jobs.com/offers/";

    @Autowired
    private ClientsJobsService clientsJobsService;

    @RequestMapping(value = "/siteMap.txt", produces = "text/plain;charset=UTf-8")
    @ResponseBody
    public String getSiteMapFile() throws IOException {

        ArrayList<ClientOffers> all = clientsJobsService.getAll();

        StringBuilder sb = new StringBuilder();

        for (ClientOffers offers : all) {

            sb.append(SiteMapController.URL);
            sb.append(offers.getId());
            sb.append("\r");

        }

        return sb.toString();
    }

//    @RequestMapping(value = "/78D4288A494377BA7A045EFD6C38124A.txt", produces = "text/plain;charset=UTf-8")
//    @ResponseBody
//    public String checkSSL() throws IOException {
//
//        BufferedReader br = null;
//          InputStream inputStream = 
//      getClass().getClassLoader().getResourceAsStream("/WEB-INF/78D4288A494377BA7A045EFD6C38124A.txt");
//        br = new BufferedReader(new InputStreamReader(inputStream));
//     
//        return br.toString();
//    }

}
