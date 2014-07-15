package com.lesso.newlp.config;

import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * UserDTO: Sean
 * Date: 7/3/13
 * Time: 10:50 AM
 */
@Order(0)
public class WebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {

//    @Override
//    public void onStartup(ServletContext servletContext) {
//        // Create the 'root' Spring application context
//        AnnotationConfigWebApplicationContext rootContext =
//                new AnnotationConfigWebApplicationContext();
//        rootContext.register(AppConfig.class);
//
//        // Manage the lifecycle of the root application context
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//        // Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext dispatcherContext =
//                new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(WebConfig.class);
//
//        // Register and map the dispatcher servlet
//        ServletRegistration.Dynamic dispatcher =
//                servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//        ServletRegistration.Dynamic reg =
//                servletContext.addServlet("rest-exporter", new DispatcherServlet(dispatcherContext));
//        reg.setLoadOnStartup(1);
//        reg.addMapping("/api/rest/*");
//    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/","/api/rest/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new OpenEntityManagerInViewFilter()
        };
    }

}