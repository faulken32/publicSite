/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author t311372
 */
@Controller
public class RecrutorController {
    
    
    
    @RequestMapping(value = {"/recrutor/signin"}, method = RequestMethod.GET)
    public ModelAndView simpleInscription() {

        ModelAndView mv = new ModelAndView("simple");
        mv.addObject("recrutor" , true);
        return mv;
    }
    
}
