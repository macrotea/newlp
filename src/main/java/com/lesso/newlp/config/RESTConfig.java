package com.lesso.newlp.config;

import com.lesso.newlp.invoice.entity.InvoiceDetailEntity;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import com.lesso.newlp.material.entity.MaterialAttributeEntity;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.entity.MaterialTypeEntity;
import com.lesso.newlp.pm.entity.ClientEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import com.lesso.newlp.pm.entity.MemberEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

/**
 * Created by Sean on 7/14/2014.
 */
@Configuration
public class RESTConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        messageConverters.add(fastJsonHttpMessageConverter);
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(objectMapper());
//        messageConverters.add(converter);
    }

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(MaterialEntity.class);
        config.exposeIdsFor(MaterialTypeEntity.class);
        config.exposeIdsFor(MaterialAttributeEntity.class);
        config.exposeIdsFor(InvoiceEntity.class);
        config.exposeIdsFor(InvoiceTypeEntity.class);
        config.exposeIdsFor(InvoiceDetailEntity.class);
        config.exposeIdsFor(IncEntity.class);
        config.exposeIdsFor(ClientEntity.class);
        config.exposeIdsFor(MemberEntity.class);
    }
}
