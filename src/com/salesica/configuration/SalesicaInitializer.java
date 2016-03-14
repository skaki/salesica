package com.salesica.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SalesicaInitializer implements WebApplicationInitializer {
    
	@Override
    public void onStartup(ServletContext container) throws ServletException {
 
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SalesicaConfig.class);
        ctx.setServletContext(container);
        
        final Logger log = Logger.getLogger(SalesicaInitializer.class.getName());
        
        ServletRegistration.Dynamic servlet = container.addServlet(
                "dispatcher", new DispatcherServlet(ctx));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        servlet.addMapping("/v1");
        servlet.addMapping("/v1/assets");
        servlet.addMapping("/v1/crawls");
        servlet.addMapping("/slack");
        servlet.addMapping("/v1/nlp");
        
        log.debug("Added REST Mapping");
    }
 
}
