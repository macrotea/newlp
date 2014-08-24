package com.lesso.newlp.config;

import com.lesso.newlp.config.ext.ApplicationContextUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

/**
 * UserDTO: Sean
 * Date: 7/2/13
 * Time: 8:08 AM
 */
@Configuration
@ComponentScan(
        basePackages = {"com.lesso.newlp"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        }
)
@ImportResource({
        "classpath:spring-aop.xml"
})
public class AppConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new Resource[]{
                new ClassPathResource("/resources.properties")
        };
        ppc.setLocations(resources);
        return ppc;
    }

    @Bean
    public ApplicationContextUtils applicationContextUtils(){
        return new ApplicationContextUtils();
    }
}