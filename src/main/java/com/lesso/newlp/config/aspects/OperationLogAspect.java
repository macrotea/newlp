package com.lesso.newlp.config.aspects;

import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.service.InvoiceService;
import com.lesso.newlp.log.entity.OperationLogEntity;
import com.lesso.newlp.log.repository.LogRepository;
import com.lesso.newlp.pm.repository.IncRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Aspect
public class OperationLogAspect {

    Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);


    @Resource
    LogRepository logRepository;

    @Resource
    IncRepository incRepository;

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceService invoiceService;


    @AfterReturning(
            pointcut = "execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.save(..))",
            returning= "invoiceEntity")
    public void logSaveAfterReturning(JoinPoint joinPoint, InvoiceEntity invoiceEntity) {
        logger.debug("logUpdateAfterReturning() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());
        logger.debug("Method returned value is : " + invoiceEntity);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationLogEntity logEntity = new OperationLogEntity();
        logEntity.setEntity("invoice");
        logEntity.setMemberId(user.getUsername());
        logEntity.setDescription("创建订单;");
        logEntity.setIncId(invoiceEntity.getInc().getIncId());
        logEntity.setRelId(invoiceEntity.getInvoiceId());
        logEntity.setOperationDate(new Date());
        logRepository.save(logEntity);

        logger.debug("******");

    }


    @Around("execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.update(..))")
    public Object logUpdateAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("logUpdateAround() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());

        InvoiceEntity invoiceUpdated = (InvoiceEntity) joinPoint.getArgs()[0];
        InvoiceEntity invoiceEntity = invoiceService.findById(invoiceUpdated.getInvoiceId());

        logger.debug("Around before is running!");
        Object retVal = joinPoint.proceed(); //continue on the intercepted method
        logger.debug("Around after is running!");
        logger.debug("******");

        StringBuilder description  = new StringBuilder("更新订单;");
        if (!Objects.isNull(invoiceUpdated.getAuditStatus())) {
            description.append("订单状态:").append(invoiceEntity.getAuditStatus()).append("  to ").append(invoiceUpdated.getAuditStatus()).append(";");
        }

        if (!Objects.isNull(invoiceUpdated.getInvoiceDetails())) {
            final InvoiceEntity finalInvoice = invoiceUpdated;
            invoiceEntity.getInvoiceDetails().forEach(a -> finalInvoice.getInvoiceDetails().forEach(b -> {
                if (a.getInvoiceDetailId().equals(b.getInvoiceDetailId())) {
                    if(!a.getDeliveryCount().equals(b.getDeliveryCount())){
                        description.append("订单明细:").append(a.getInvoiceDetailId()).append(a.getDeliveryCount()).append("  to ").append(b.getDeliveryCount()).append(";");
                    }
                }
            }));
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationLogEntity logEntity = new OperationLogEntity();
        logEntity.setEntity("invoice");
        logEntity.setMemberId(user.getUsername());
        logEntity.setDescription(description.toString());
        logEntity.setIncId(invoiceEntity.getInc().getIncId());
        logEntity.setRelId(invoiceEntity.getInvoiceId());
        logEntity.setOperationDate(new Date());
        logRepository.save(logEntity);
        return retVal;
    }


    @Around("execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.patch(..))")
    public Object logPatchAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("logPatchAround() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());

        InvoiceEntity invoiceUpdated = (InvoiceEntity) joinPoint.getArgs()[1];
        InvoiceEntity invoiceEntity = invoiceService.findById((Long) joinPoint.getArgs()[0]);

        logger.debug("Around before is running!");

        Object retVal = joinPoint.proceed(); //continue on the intercepted method

        logger.debug("Around after is running!");
        logger.debug("******");

        StringBuilder description  = new StringBuilder("更新订单;");
        if (!Objects.isNull(invoiceUpdated.getAuditStatus())) {
            description.append("订单状态:").append(invoiceEntity.getAuditStatus()).append("  to ").append(invoiceUpdated.getAuditStatus()).append(";");
        }

        if (!Objects.isNull(invoiceUpdated.getInvoiceDetails())) {
            final InvoiceEntity finalInvoice = invoiceUpdated;
            invoiceEntity.getInvoiceDetails().forEach(a -> finalInvoice.getInvoiceDetails().forEach(b -> {
                if (a.getInvoiceDetailId().equals(b.getInvoiceDetailId())) {
                    if(!a.getDeliveryCount().equals(b.getDeliveryCount())){
                        description.append("实发数量:").append(a.getInvoiceDetailId()).append(a.getDeliveryCount()).append("  to ").append(b.getDeliveryCount()).append(";");
                    }
                }
            }));
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationLogEntity logEntity = new OperationLogEntity();
        logEntity.setEntity("invoice");
        logEntity.setMemberId(user.getUsername());
        logEntity.setDescription(description.toString());
        logEntity.setIncId(invoiceEntity.getInc().getIncId());
        logEntity.setRelId(invoiceEntity.getInvoiceId());
        logEntity.setOperationDate(new Date());
        logRepository.save(logEntity);
        return retVal;
    }


    @Around("execution(* com.lesso.newlp.invoice.service.InvoiceServiceImpl.delete(..))")
    public Object logDeleteAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("logPatchAround() is running!");
        logger.debug("hijacked : " + joinPoint.getSignature().getName());

        InvoiceEntity invoiceEntity = invoiceRepository.findOne((Long) joinPoint.getArgs()[0]);

        logger.debug("Around before is running!");

        Object retVal = joinPoint.proceed(); //continue on the intercepted method

        logger.debug("Around after is running!");
        logger.debug("******");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationLogEntity logEntity = new OperationLogEntity();
        logEntity.setEntity("invoice");
        logEntity.setMemberId(user.getUsername());
        logEntity.setDescription("删除订单");
        logEntity.setIncId(invoiceEntity.getInc().getIncId());
        logEntity.setRelId(invoiceEntity.getInvoiceId());
        logEntity.setOperationDate(new Date());
        logRepository.save(logEntity);
        return retVal;
    }
}
