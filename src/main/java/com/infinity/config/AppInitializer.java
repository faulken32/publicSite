package com.infinity.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.elasticsearch.common.base.Charsets;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";
    
    private static final String FILTER_ENCODING_NAME = "CharacterEncodingFilter";
    private static final String FILTER_ENCODING_MAPPING = "/*";

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(MvcConfiguration.class,SecurityConfig.class);

         FilterRegistration.Dynamic charEncodingfilter = servletContext.addFilter(FILTER_ENCODING_NAME, new CharacterEncodingFilter());

        charEncodingfilter.setInitParameter("encoding", Charsets.UTF_8.displayName());
        charEncodingfilter.setInitParameter("forceEncoding", "true");
        charEncodingfilter.addMappingForUrlPatterns(null, false, FILTER_ENCODING_MAPPING);
        
        servletContext.addListener(new ContextLoaderListener(dispatcherServlet));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                DISPATCHER_SERVLET_NAME, new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement("/", 1024*1024*5, 1024*1024*5*5, 1024*1024));
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

    }

}
