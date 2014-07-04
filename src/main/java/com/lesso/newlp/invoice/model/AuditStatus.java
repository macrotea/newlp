package com.lesso.newlp.invoice.model;

/**
 * Created by Sean on 6/19/2014.
 */
public class AuditStatus {

//    10	客户下单保存未发送	客户下单
//    20	客户下单保存已发送,开单部未接收	客户下单
    public final static int ORDER_CREATOR_SAVE = 30;//    30	开单部手工开单保存未审核	开单部
    public final static int ORDER_CREATOR_SUBMIT = 40;//    40	开单部已接收或已审核,仓库未接收	开单部	开单部计划单接收后直接转到开单部审核列表
    public final static int DEPOT_SAVE = 50;//    50	仓管部内部出入库单据保存未审核或接收未审核	仓管部
    public final static int DEPOT_ADJUST = 60;//    60	仓管部非销售单审核	仓管部	仓库发货单接收后,如果需要调整数量,只可调整实发数量
    public final static int DEPOT_SUBMIT = 70;//    70	仓管部审核审核并发送打单部	打单部	仓库发货单接收后,如果需要调整数量,只可调整实发数量
    public final static int ORDER_HANDLER_ACCEPT= 70;//    80	打单部接收未审核	打单部
    public final static int ORDER_HANDLER_AUDIT = 70;//    90	打单部审核	打单部

}
