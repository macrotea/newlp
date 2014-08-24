package com.lesso.newlp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by Sean on 7/15/2014.
 */
public class RestWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { RESTConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/rest/*" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return null;
    }

    @Override
    protected String getServletName() {
        return "rest-exporter";
    }
}