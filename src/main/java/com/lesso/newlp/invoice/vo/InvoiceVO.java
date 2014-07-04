package com.lesso.newlp.invoice.vo;

import java.util.Date;

/**
 * Created by Sean on 6/17/2014.
 */
public class InvoiceVO{

    Integer id;
    String num;
    String companyId;               //公司ID
    String carNum;                   //车号
    String clientNum;              //客户ID
    String clientAddress;       //联系地址
    Date createdDate;           //创建日期
    Date submitDate;            //下单日期
    Date deliveryDate;         //发货日期
    Date receivedDate;        //交货日期

    //    10	客户下单保存未发送	客户下单
//    20	客户下单保存已发送,开单部未接收	客户下单
//    30	开单部手工开单保存未审核	开单部
//    40	开单部已接收或已审核,仓库未接收	开单部	开单部计划单接收后直接转到开单部审核列表
//    50	仓管部内部出入库单据保存未审核或接收未审核	仓管部
//    60	仓管部非销售单审核	仓管部	仓库发货单接收后,如果需要调整数量,只可调整实发数量
//    70	仓管部审核审核并发送打单部	打单部	仓库发货单接收后,如果需要调整数量,只可调整实发数量
//    80	打单部接收未审核	打单部
//    90	打单部审核	打单部
    Integer auditStatus;         //审核状态
    String remark;                   //备注
    Boolean active = true;                     //默认为1，0为删除

    InvoiceTypeVO invoiceType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public InvoiceTypeVO getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceTypeVO invoiceType) {
        this.invoiceType = invoiceType;
    }
}
