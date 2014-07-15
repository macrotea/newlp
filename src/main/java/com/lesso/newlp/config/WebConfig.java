package com.lesso.newlp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.bind.support.AuthenticationPrincipalArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserDTO: Sean
 * Date: 7/2/13
 * Time: 8:18 AM
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.lesso.newlp.**.controller",
                "com.lesso.newlp.api.v1.*"
        },
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class)
        }
)
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableAspectJAutoProxy(proxyTargetClass = true)
//public class WebConfig extends WebMvcConfigurerAdapter {
public class WebConfig extends DelegatingWebMvcConfiguration {


    private static final String MESSAGE_SOURCE = "classpath:messages";

    private static final String RESOURCES_HANDLER = "/app/";
    private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        final Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
        mediaTypes.put("html", MediaType.TEXT_HTML);
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        configurer.
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaTypes(mediaTypes);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        converters.add(converter);
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        converters.add(fastJsonHttpMessageConverter);

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.registerModule(new Jackson2HalModule());
    }




//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
//        exceptionHandlerExceptionResolver.afterPropertiesSet();
//
//        exceptionResolvers.add(exceptionHandlerExceptionResolver);
//        exceptionResolvers.add(new ResponseStatusExceptionResolver());
//        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
//    }


    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        final List<View> defaultViews = new ArrayList<View>();

        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        mappingJackson2JsonView.setObjectMapper(objectMapper());

        defaultViews.add(mappingJackson2JsonView);
        resolver.setDefaultViews(defaultViews);
        resolver.setOrder(1);
        return resolver;
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

    @Bean
    public ViewResolver viewResolver() {
        VelocityViewResolver resolver = new VelocityViewResolver();
        resolver.setPrefix("");
//        resolver.setViewClass(org.springframework.web.servlet.view.velocity.VelocityLayoutView.class);
        resolver.setSuffix(".vm");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(false);
        resolver.setOrder(2);
        resolver.setToolboxConfigLocation("/WEB-INF/classes/toolbox.xml");
        return resolver;
    }

    @Bean
    public VelocityConfig velocityConfig() {
        VelocityConfigurer cfg = new VelocityConfigurer();
        cfg.setResourceLoaderPath("/WEB-INF/velocity/");
        Map<String, Object> velocityPropertiesMap = new HashMap<String, Object>();
        velocityPropertiesMap.put("input.encoding", "UTF-8");
        velocityPropertiesMap.put("output.encoding", "UTF-8");
        velocityPropertiesMap.put("contentType", "text/html;charset=UTF-8");
        cfg.setVelocityPropertiesMap(velocityPropertiesMap);
        return cfg;
    }

//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
//        resolver.setDefaultErrorView("/error/default-error");
//        resolver.setDefaultStatusCode(500);
//        return resolver;
//    }


    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {

//        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping() {
//
//            @Override
//            protected boolean isHandler(Class<?> beanType) {
//                return !(beanType == null || ClassUtils.getPackageName(beanType).startsWith("org.springframework.data")) && super.isHandler(beanType);
//            }
//        };

        RequestMappingHandlerMapping handlerMapping =super.requestMappingHandlerMapping();

        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setRemoveSemicolonContent(false);
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());

        return handlerMapping;
    }

    @Override
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
        handlerMapping.setOrder(-1);
        return handlerMapping;
    }

//    @Override
//    public Validator getValidator() {
//        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
//        validator.setValidationMessageSource(messageSource());
//        return validator;
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
        argumentResolvers.add(new SortHandlerMethodArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
//        CurrentUserArgumentResolver currentUserWebArgumentResolver = new CurrentUserArgumentResolver();
//        argumentResolvers.add(currentUserWebArgumentResolver);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/app/").addResourceLocations("/app/**");
        registry.addResourceHandler("/dist/").addResourceLocations("/dist/**");
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserAgentInterceptor());
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
