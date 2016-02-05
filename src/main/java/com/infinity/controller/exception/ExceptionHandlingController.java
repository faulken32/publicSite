/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.controller.exception;

import javax.servlet.http.HttpServletRequest;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;



/**
 *
 * @author t311372
 */
@ControllerAdvice
public class ExceptionHandlingController {
	
	
	
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);
  private static final String MSG = "Ooups une erreur c'est produite";
  
    @ExceptionHandler(Throwable.class)
  
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
    	
    	LOG.error(exception.getMessage(), exception);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", ExceptionHandlingController.MSG);
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
    
  
    @ExceptionHandler(NullPointerException.class)
   public ModelAndView handleErrorNull(Exception exception) {
    	
    	LOG.error(exception.getMessage(), exception);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", ExceptionHandlingController.MSG);
        mav.addObject("exception", exception);
     
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView myError(Exception exception) { 
        LOG.error(exception.getMessage(), exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exc", exception);
        mav.setViewName("error");
        return mav;
    }

}
