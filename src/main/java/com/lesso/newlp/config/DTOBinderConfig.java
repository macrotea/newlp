package com.lesso.newlp.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.jdto.spring.SpringDTOBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOBinderConfig {


    @Bean
    public MapperFactory mapperFactory(){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .build();
//        mapperFactory.registerClassMap(mapperFactory.classMap(ProcessInstance.class,ProcessInstanceDTO.class).byDefault().toClassMap());
//        mapperFactory.registerClassMap(mapperFactory.classMap(ProcessDefinition.class,ProcessDefinitionDTO.class).byDefault().toClassMap());
//        mapperFactory.registerClassMap(mapperFactory.classMap(Task.class, TaskInstanceDTO.class).byDefault().toClassMap());
//        mapperFactory.registerClassMap(mapperFactory.classMap(FormDataImpl.class,TaskFormDataDTO.class).byDefault().toClassMap());

        return mapperFactory;
    }

    @Bean
    public MapperFacade mapperFacade(){
        return mapperFactory().getMapperFacade();
    }

    @Bean
    public SpringDTOBinder dtoBinder(){
        SpringDTOBinder dtoBinder = new SpringDTOBinder();
//                dtoBinder.setXmlConfig(new ClassPathResource("dtos.xml"));
        return dtoBinder;
    }
}
