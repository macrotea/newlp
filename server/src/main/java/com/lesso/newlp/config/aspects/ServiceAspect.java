package com.lesso.newlp.config.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * UserDTO: Sean
 * Date: 7/3/13
 * Time: 4:32 AM
 */
@Aspect
public class ServiceAspect {
    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.lesso.newlp.*.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.lesso.newlp.*.service..*)")
    public void inServiceLayer() {}

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotatedClass() {}

}
