package com.lesso.newlp.config.ext;

import com.lesso.newlp.auth.model.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

/**
 * User: Sean
 * Date: 11/20/13
 * Time: 12:54 PM
 */
public class CurrentUserArgumentResolver  implements HandlerMethodArgumentResolver {

    public CurrentUserArgumentResolver () {
        System.out.println("Ready");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        Annotation[] annotations = parameter.getParameterAnnotations();

//        if (parameter.getParameterType().equals(User.class)) {
//            for (Annotation annotation : annotations) {
//                if (CurrentUser.class.isInstance(annotation)) {
//                    Principal principal = webRequest.getUserPrincipal();
//                    return ((Authentication) principal).getPrincipal();
//                }
//            }
//        }
        Principal principal = webRequest.getUserPrincipal();
        return ((Authentication) principal).getPrincipal();
    }
}
