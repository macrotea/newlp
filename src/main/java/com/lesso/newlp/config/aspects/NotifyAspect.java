package com.lesso.newlp.config.aspects;

import com.lesso.newlp.config.ext.ApplicationContextUtils;
import com.lesso.newlp.config.interceptors.PresenceChannelInterceptor;
import com.lesso.newlp.home.entity.PanelEntity;
import com.lesso.newlp.home.service.HomeService;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@Aspect
public class NotifyAspect {

    Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    private static final String WEBSOCKET_TOPIC_NOTIFY = "/topic/notify";


    @AfterReturning(
            pointcut = "execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.save(..))",
            returning = "invoiceEntity")
    public void notifySaveAfterReturning(JoinPoint joinPoint, InvoiceEntity invoiceEntity) {
        logger.debug("notifySaveAfterReturning() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());
        logger.debug("Method returned value is : " + invoiceEntity);

        this.notifyAllConnectedUsers();

        logger.debug("******");

    }

    @AfterReturning(
            pointcut = "execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.update(..))",
            returning = "invoiceEntity")
    public void notifyUpdateAfterReturning(JoinPoint joinPoint, InvoiceEntity invoiceEntity) {
        logger.debug("notifyUpdateAfterReturning() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());
        logger.debug("Method returned value is : " + invoiceEntity);

        this.notifyAllConnectedUsers();

        logger.debug("******");

    }

    @Around("execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.patch(..))")
    public Object notifyPatchAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("notifyPatchAround() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());


        logger.debug("Around before is running!");

        Object retVal = joinPoint.proceed(); //continue on the intercepted method
        this.notifyAllConnectedUsers();

        logger.debug("Around after is running!");
        logger.debug("******");

        return retVal;
    }

    @Around("execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.delete(..))")
    public Object notifyDeleteAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("notifyDeleteAround() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());


        logger.debug("Around before is running!");

        Object retVal = joinPoint.proceed(); //continue on the intercepted method
        this.notifyAllConnectedUsers();

        logger.debug("Around after is running!");
        logger.debug("******");

        return retVal;
    }



    private void notifyAllConnectedUsers() {
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();

        SimpMessagingTemplate template = applicationContext.getBean("brokerMessagingTemplate", SimpMessagingTemplate.class);
        HomeService homeService = applicationContext.getBean("homeServiceImpl", HomeService.class);

        //send to  users connected by websocket
        PresenceChannelInterceptor presenceChannelInterceptor = applicationContext.getBean("presenceChannelInterceptor", PresenceChannelInterceptor.class);
        for (String username : presenceChannelInterceptor.connectedUsers) {
            List<PanelEntity> panels = homeService.queryPanels(username);
            template.convertAndSendToUser(username, WEBSOCKET_TOPIC_NOTIFY, panels);
        }
    }

}
