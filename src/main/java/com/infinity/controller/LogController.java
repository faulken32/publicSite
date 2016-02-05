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
 * login controler
 * @author t311372
 */
@Controller
public class LogController {

    private final static String mainClass = "back";
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("mainClass", mainClass);
        return modelAndView;
    }
    
    
    
}
