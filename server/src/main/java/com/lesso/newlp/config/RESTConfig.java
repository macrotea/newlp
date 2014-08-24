package com.lesso.newlp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.lesso.newlp.invoice.entity.InvoiceDetailEntity;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import com.lesso.newlp.material.entity.MaterialAttributeEntity;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.entity.MaterialTypeEntity;
import com.lesso.newlp.pm.entity.ClientEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import com.lesso.newlp.pm.entity.MemberEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

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
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(objectMapper());
//        converters.add(converter);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        converters.add(converter);
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

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID,true);
        objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH,true);

//        objectMapper.enable(MapperFeature.USE_STATIC_TYPING);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        StdDateFormat dateFormat = new StdDateFormat();
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(dateFormat);
        objectMapper.getDeserializationConfig().with(dateFormat);
        objectMapper.getSerializationConfig().with(dateFormat);
        return objectMapper;
    }


}
