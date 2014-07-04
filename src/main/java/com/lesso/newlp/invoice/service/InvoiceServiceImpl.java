package com.lesso.newlp.invoice.service;

import com.lesso.newlp.invoice.entity.InvoiceDetailEntity;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import com.lesso.newlp.invoice.model.AuditStatus;
import com.lesso.newlp.invoice.model.SearchTerm;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.repository.InvoiceTypeRepository;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.repository.MaterialTypeRepository;
import com.lesso.newlp.pm.entity.ClientEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Sean on 6/20/2014.
 */
@Service
@SuppressWarnings("unchecked")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    JdbcDaoSupport jdbcDaoSupport;

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceTypeRepository invoiceTypeRepository;

    @Resource
    MaterialTypeRepository materialTypeRepository;

    /**
     * 创建订单
     * @param invoice
     * @return
     */
    @Override
    public InvoiceEntity save(InvoiceEntity invoice) {
        final InvoiceEntity finalInvoice = invoice;
        invoice.getInvoiceDetails().forEach(invoiceDetail -> {
            invoiceDetail.setInvoice(finalInvoice);
            invoiceDetail.setAmount(invoiceDetail.getPrice().multiply(new BigDecimal(Double.toString(invoiceDetail.getOrderCount()))));
        });

        if(AuditStatus.ORDER_CREATOR_SAVE == invoice.getAuditStatus()){
            invoice.setCreatedDate( new Date());
        }
        if(AuditStatus.ORDER_CREATOR_SUBMIT == invoice.getAuditStatus()){
            invoice.setSubmitDate( new Date());
        }

        invoice=  invoiceRepository.save(invoice);

//        invoice.getInvoiceDetails().forEach(invoiceDetail -> {
//            StringBuilder str = new StringBuilder();
//            str.append(invoice.getInc().getIncShortName())
//                    .append( invoiceDetail.getMaterial().getMaterialType().getShortName())
//                    .append(invoice.getInvoiceType().getType() == 1? "R": "C")
//                    .append(new DateTime().toString("yyyyMMdd"))
//                    .append(invoice.getInvoiceId());
//        });
        return invoice;
    }

    /**
     *  根据订单Id查询
     * @param invoiceId 订单Id
     * @return
     */
    @Override
    public InvoiceEntity findById(Long invoiceId) throws Exception {

        List<InvoiceDetailEntity> list = jdbcDaoSupport.getJdbcTemplate().query("SELECT * ,j.remark as incDetailRemark,j.active as incDetailActive,m.name as materName,m.unit as materUnit,m.price as materPrice FROM INV_INVOICE i\n" +
                "LEFT JOIN INV_INVOICE_TYPE t ON i.invoiceType_invoiceTypeId=t.invoiceTypeId \n" +
                "LEFT JOIN INV_INVOICE_DETAIL j ON i.invoiceId=j.invoice_invoiceId \n" +
                "LEFT JOIN PM_INC inc on i.inc_incId = inc.incId \n" +
                "left join PM_CLIENT incClient on i.client_clientId = incClient.clientId\n" +
                "left join MAT_MATERIAL m ON j.material_materialNum = m.materialNum \n" +
                "WHERE i.invoiceId = ? and i.active = 1", new Object[]{invoiceId}, new RowMapper<InvoiceDetailEntity>() {
            @Override
            public InvoiceDetailEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                InvoiceEntity invoice = new InvoiceEntity();
                invoice.setInvoiceId(rs.getLong("invoiceId"));
                invoice.setNum(rs.getString("num"));
                invoice.setCarNum(rs.getString("carNum"));
                invoice.setSubmitDate(rs.getDate("submitDate"));
                invoice.setClientAddress(rs.getString("clientAddress"));
                invoice.setCreatedDate(rs.getDate("createdDate"));
                invoice.setDeliveryDate(rs.getDate("deliveryDate"));
                invoice.setRemark(rs.getString("remark"));
                invoice.setReceivedDate(rs.getDate("receivedDate"));
                invoice.setAuditStatus(rs.getInt("auditStatus"));
                invoice.setActive(rs.getBoolean("active"));
                IncEntity incEntity = new IncEntity();
                incEntity.setIncId(rs.getLong("incId"));
                incEntity.setIncName(rs.getString("incName"));
                incEntity.setIncShortName(rs.getString("incShortName"));
                invoice.setInc(incEntity);
                ClientEntity clientEntity = new ClientEntity();
                clientEntity.setClientId(rs.getLong("clientId"));
                clientEntity.setClientName(rs.getString("clientName"));
                clientEntity.setClientNum(rs.getString("clientNum"));
                invoice.setClient(clientEntity);
                InvoiceTypeEntity invoiceTypeEntity = new InvoiceTypeEntity();
                invoiceTypeEntity.setInvoiceTypeId(rs.getLong("invoiceTypeId"));
                invoiceTypeEntity.setType(rs.getInt("type"));
                invoiceTypeEntity.setName(rs.getString("name"));
                invoiceTypeEntity.setActive(rs.getBoolean("active"));
                invoice.setInvoiceType(invoiceTypeEntity);
                invoice.setInvoiceDetails(new HashSet<InvoiceDetailEntity>());
                InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
                invoiceDetailEntity.setInvoiceDetailId(rs.getLong("invoiceDetailId"));
                invoiceDetailEntity.setOrderCount(rs.getDouble("orderCount"));
                invoiceDetailEntity.setDeliveryCount(rs.getDouble("deliveryCount"));
                invoiceDetailEntity.setAmount(rs.getBigDecimal("amount"));
                invoiceDetailEntity.setRemark(rs.getString("incDetailRemark"));
                invoiceDetailEntity.setActive(rs.getBoolean("incDetailActive"));
                invoiceDetailEntity.setUnit(rs.getString("unit"));
                invoiceDetailEntity.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
                invoiceDetailEntity.setAuxiliaryUnitTwo(rs.getString("auxiliaryUnitTwo"));
                invoiceDetailEntity.setConversionRateOne(rs.getDouble("conversionRateOne"));
                invoiceDetailEntity.setConversionRateTwo(rs.getDouble("conversionRateTwo"));
                invoiceDetailEntity.setPrice(rs.getBigDecimal("price"));
                MaterialEntity materialEntity = new MaterialEntity();
                materialEntity.setMaterialNum(rs.getString("materialNum"));
                materialEntity.setName(rs.getString("materName"));
                materialEntity.setUnit(rs.getString("materUnit"));
                materialEntity.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
                materialEntity.setAuxiliaryUnitTwo(rs.getString("auxiliaryUnitTwo"));
                materialEntity.setConversionRateOne(rs.getDouble("conversionRateOne"));
                materialEntity.setConversionRateTwo(rs.getDouble("conversionRateTwo"));
                materialEntity.setPrice(rs.getBigDecimal("materPrice"));
                invoiceDetailEntity.setMaterial(materialEntity);
                invoiceDetailEntity.setInvoice(invoice);
                return invoiceDetailEntity;
            }
        });
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        if (list != null) {
            invoiceEntity = list.get(0).getInvoice();
            Set<InvoiceDetailEntity> set = new HashSet<InvoiceDetailEntity>();
            set.addAll(list);
            invoiceEntity.setInvoiceDetails(set);
        }
        return  invoiceEntity;
//        return  invoiceRepository.findOne(invoiceId);
    }


    /**
     *更新订单
     * @param invoice
     * @return
     */
    @Override
    @Transactional
    public InvoiceEntity update(InvoiceEntity invoice) throws InvocationTargetException, IllegalAccessException {
        Set set=invoice.getInvoiceDetails();
        if(set!=null){
            List invoiceDataDBID=jdbcDaoSupport.getJdbcTemplate().query("select invoiceDetailId from INV_INVOICE_DETAIL where invoice_InvoiceId=?",new Object[]{invoice.getInvoiceId()},new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    return rs.getLong("invoiceDetailId");
                };
            });
            Iterator it=set.iterator();
            while(it.hasNext()){
                InvoiceDetailEntity ide =(InvoiceDetailEntity) it.next();
                if(invoiceDataDBID.contains(ide.getInvoiceDetailId())){
                    jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE_DETAIL set active=?, amount=?,deliveryCount=?,orderCount=?,remark=?," +
                                    "invoice_invoiceId=?,material_materialNum=?,auxiliaryUnitOne=?,auxiliaryUnitTwo=?,conversionRateOne=?,conversionRateTwo=?,price=?,unit=? WHERE invoiceDetailId=?",
                            new Object[]{ide.getActive(),ide.getAmount(),ide.getDeliveryCount(),ide.getOrderCount(),ide.getRemark(),invoice.getInvoiceId(),ide.getMaterial().getMaterialNum(),ide.getAuxiliaryUnitOne()
                                    ,ide.getAuxiliaryUnitTwo(),ide.getConversionRateOne(),ide.getConversionRateTwo(),ide.getPrice(),ide.getUnit(),ide.getInvoiceDetailId()});
                    invoiceDataDBID.remove(ide.getInvoiceDetailId());
                }else {
                    jdbcDaoSupport.getJdbcTemplate().update("insert into INV_INVOICE_DETAIL(updated,amount,deliveryCount,orderCount,remark,invoice_invoiceId,material_materialNum,auxiliaryUnitOne,auxiliaryUnitTwo,conversionRateOne,conversionRateTwo,price,unit)" +
                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[]{new Date(),ide.getAmount(),ide.getDeliveryCount(),ide.getOrderCount(),ide.getRemark(),
                                    invoice.getInvoiceId(),ide.getMaterial().getMaterialNum(),ide.getAuxiliaryUnitOne() ,
                                    ide.getAuxiliaryUnitTwo(),ide.getConversionRateOne(),
                                    ide.getConversionRateTwo(),ide.getPrice(),ide.getUnit()});
                }
            };

            if(invoiceDataDBID.size()>0){
                String inid="";
                for(Object o:invoiceDataDBID){
                    inid+=o.toString()+",";
                }
                inid=inid.substring(0,inid.length()-1);
                jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE_DETAIL set active=0 where invoiceDetailId in("+inid+")");
            }

        }
        jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE  set num=?,inc_incId=?," +
                        "carNum=?,client_clientId=?,clientAddress=?," +
                        "receivedDate=?,auditStatus=?,remark=?,active=?,invoiceType_invoiceTypeId=?where invoiceId=?",
                new Object[]{invoice.getNum(),
                        invoice.getInc().getIncId(), invoice.getCarNum(),
                        invoice.getClient().getClientId(),invoice.getClientAddress(),
                        invoice.getReceivedDate(),
                        invoice.getAuditStatus(), invoice.getRemark(), invoice.getActive
                        (), invoice.getInvoiceType().getInvoiceTypeId(),
                        invoice.getInvoiceId()});
        return invoice;
    }

    /**
     * 删除订单
     * @param invoiceId
     */
    @Override
    public void delete(Long invoiceId) {
        jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE set active=0 where invoiceId=?",new Object[]{invoiceId});
    }


    /**
     * 根据订单审核状态查询
     * @param auditStatus
     * @param pageable
     * @return
     */
    @Override
    public Page<InvoiceEntity> queryByAuditStatus(Integer auditStatus, Pageable pageable) {

        List<InvoiceDetailEntity> list = jdbcDaoSupport.getJdbcTemplate().query("SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "      ROW_NUMBER () OVER (ORDER BY i.invoiceId DESC) AS rowNum,\n" +
                "\t\t\ti.invoiceId,\n" +
                "\t\t\ti.num,\n" +
                "\t\t\ti.auditStatus,\n" +
                "      t.invoiceTypeId,\n" +
                "\t\t\tt.name,\n" +
                "\t\t\tt.type,\n" +
                "\t\t\tinc.incId,\n" +
                "\t\t\tinc.incName,\n" +
                "\t\t\tincClient.clientId,\n" +
                "\t\t\tincClient.clientName,\n" +
                "\t\t\ti.receivedDate,\n" +
                "\t\t\ti.carNum\n" +
                "\t\tFROM\n" +
                "\t\t\tINV_INVOICE i\n" +
                "\t\tLEFT JOIN INV_INVOICE_TYPE t ON i.invoiceType_invoiceTypeId = t.invoiceTypeId\n" +
                "\t\tLEFT JOIN PM_INC inc ON i.inc_incId = inc.incId\n" +
                "\t\tLEFT JOIN PM_CLIENT incClient ON i.client_clientId = incClient.clientId\n" +
                "\t\t\n" +
                "\t\tWHERE\n" +
                "\t\t\ti.auditStatus = ? and i.active =1\n" +
                "\t) a\n" +
                "WHERE  a.rowNum > "+pageable.getPageNumber() * pageable.getPageSize() +" AND a.rowNum <"+((pageable.getPageNumber() + 1)*pageable.getPageSize()+1), new Object[]{auditStatus}, new RowMapper<InvoiceDetailEntity>() {
            @Override
            public InvoiceDetailEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                InvoiceEntity invoice = new InvoiceEntity();
                invoice.setInvoiceId(rs.getLong("invoiceId"));
                invoice.setNum(rs.getString("num"));
                invoice.setCarNum(rs.getString("carNum"));
//                invoice.setSubmitDate(rs.getDate("submitDate"));
//                invoice.setClientAddress(rs.getString("clientAddress"));
//                invoice.setCreatedDate(rs.getDate("createdDate"));
//                invoice.setDeliveryDate(rs.getDate("deliveryDate"));
//                invoice.setRemark(rs.getString("remark"));
                invoice.setReceivedDate(rs.getDate("receivedDate"));
                invoice.setAuditStatus(rs.getInt("auditStatus"));
//                invoice.setActive(rs.getBoolean("active"));
                IncEntity incEntity = new IncEntity();
                incEntity.setIncId(rs.getLong("incId"));
                incEntity.setIncName(rs.getString("incName"));
//                incEntity.setIncShortName(rs.getString("incShortName"));
                invoice.setInc(incEntity);
                ClientEntity clientEntity = new ClientEntity();
                clientEntity.setClientId(rs.getLong("clientId"));
                clientEntity.setClientName(rs.getString("clientName"));
//                clientEntity.setClientNum(rs.getString("clientNum"));
                invoice.setClient(clientEntity);
                InvoiceTypeEntity invoiceTypeEntity = new InvoiceTypeEntity();
                invoiceTypeEntity.setInvoiceTypeId(rs.getLong("invoiceTypeId"));
                invoiceTypeEntity.setType(rs.getInt("type"));
                invoiceTypeEntity.setName(rs.getString("name"));
//                invoiceTypeEntity.setActive(rs.getBoolean("active"));
                invoice.setInvoiceType(invoiceTypeEntity);
                invoice.setInvoiceDetails(new HashSet<InvoiceDetailEntity>());
                InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
//                invoiceDetailEntity.setInvoiceDetailId(rs.getLong("invoiceDetailId"));
//                invoiceDetailEntity.setOrderCount(rs.getDouble("orderCount"));
//                invoiceDetailEntity.setDeliveryCount(rs.getDouble("deliveryCount"));
//                invoiceDetailEntity.setAmount(rs.getBigDecimal("amount"));
//                invoiceDetailEntity.setRemark(rs.getString("incDetailRemark"));
//                invoiceDetailEntity.setActive(rs.getBoolean("incDetailActive"));
//                invoiceDetailEntity.setUnit(rs.getString("unit"));
//                invoiceDetailEntity.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
//                invoiceDetailEntity.setAuxiliaryUnitTwo(rs.getString("auxiliaryUnitTwo"));
//                invoiceDetailEntity.setConversionRateOne(rs.getDouble("conversionRateOne"));
//                invoiceDetailEntity.setConversionRateTwo(rs.getDouble("conversionRateTwo"));
//                invoiceDetailEntity.setPrice(rs.getBigDecimal("price"));
                MaterialEntity materialEntity = new MaterialEntity();
//                materialEntity.setMaterialNum(rs.getString("materialNum"));
//                materialEntity.setName(rs.getString("materName"));
//                materialEntity.setUnit(rs.getString("materUnit"));
//                materialEntity.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
//                materialEntity.setAuxiliaryUnitTwo(rs.getString("auxiliaryUnitTwo"));
//                materialEntity.setConversionRateOne(rs.getDouble("conversionRateOne"));
//                materialEntity.setConversionRateTwo(rs.getDouble("conversionRateTwo"));
//                materialEntity.setPrice(rs.getBigDecimal("materPrice"));
                invoiceDetailEntity.setMaterial(materialEntity);
                invoiceDetailEntity.setInvoice(invoice);
                return invoiceDetailEntity;
            }
        });

        HashMap hashMap = new HashMap();
        List<InvoiceEntity> invoiceEntityList = new ArrayList<InvoiceEntity>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (hashMap.containsKey(list.get(i).getInvoice().getInvoiceId())) {
                    InvoiceEntity invoiceEntity1 = (InvoiceEntity)hashMap.get(list.get(i).getInvoice().getInvoiceId());
                    Set<InvoiceDetailEntity> set1 = invoiceEntity1.getInvoiceDetails();
                    set1.add(list.get(i));
                }
                else {
                    hashMap.put(list.get(i).getInvoice().getInvoiceId(),list.get(i).getInvoice());
                    Set<InvoiceDetailEntity> set2 = list.get(i).getInvoice().getInvoiceDetails();
                    set2.add(list.get(i));
                    list.get(i).getInvoice().setInvoiceDetails(set2);
                    invoiceEntityList.add(list.get(i).getInvoice());
                }
            }
        }

        long total = jdbcDaoSupport.getJdbcTemplate().queryForObject(
                "SELECT count(DISTINCT rowNum) FROM (\n" +
                        "SELECT\n" +
                        "ROW_NUMBER () OVER (ORDER BY invoiceId DESC) AS rowNum,*\n" +
                        "FROM\n" +
                        "INV_INVOICE\n" +
                        ") i\n" +
                        "LEFT JOIN INV_INVOICE_TYPE t ON i.invoiceType_invoiceTypeId=t.invoiceTypeId\n" +
                        "LEFT JOIN INV_INVOICE_DETAIL j ON i.invoiceId=j.invoice_invoiceId\n" +
                        "LEFT JOIN PM_INC inc on i.inc_incId = inc.incId\n" +
                        "left join PM_CLIENT incClient on i.client_clientId = incClient.clientId\n" +
                        "left join MAT_MATERIAL m ON j.material_materialNum = m.materialNum \n" +
                        "WHERE i.auditStatus=? and i.active =1 ",new Object[]{auditStatus},Long.class);

        return new PageImpl<InvoiceEntity>(invoiceEntityList,pageable,total);
    }

    @Override
    public Page<InvoiceEntity> search(SearchTerm searchTerm, Pageable pageable) {
        String countsql="select count(invoiceID) ";
        String selsql="select i.*,t.name,t.type,t.invoiceTypeId,p.incId,p.incName,c.clientId,c.clientName,ROW_NUMBER() OVER (ORDER BY i.invoiceId desc) as rownum ";
        String sql=" from  INV_INVOICE i " +
                "LEFT JOIN INV_INVOICE_TYPE t on i.invoiceType_invoiceTypeId=t.invoiceTypeId " +
                "LEFT JOIN PM_INC p on i.inc_incId =p.incId " +
                "LEFT JOIN PM_CLIENT c on c.clientId=i.client_clientId where 1=1 and i.active =1";
        List<Object> objList=new ArrayList<Object>();
        if(searchTerm.getAuditStatus() !=null){
            sql+=" and i.auditStatus=?";
            objList.add(searchTerm.getAuditStatus());
        }
        if(searchTerm.getInvoiceTypeId()!=null){
            sql+=" and i.invoiceType_invoiceTypeId=?";
            objList.add(searchTerm.getInvoiceTypeId());
        }
        if(searchTerm.getNum()!=null&&searchTerm.getNum().length()>0){
            sql+=" and i.num like '%?%'";
            objList.add(searchTerm.getNum());
        }
        if(searchTerm.getIncId()!=null){
            sql+=" and i.inc_incId=?";
            objList.add(searchTerm.getIncId());
        }
        if(searchTerm.getClientId()!=null){
            sql+=" and i.client_clientId=?";
            objList.add(searchTerm.getClientId());
        }
        if(searchTerm.getStartDateOfReceived()!=null){
            sql+=" and i.receivedDate>=?";
            objList.add(new DateTime(searchTerm.getStartDateOfReceived()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if(searchTerm.getEndDateOfReceived()!=null){
            sql+=" and i.receivedDate<=?";
            objList.add(new DateTime(searchTerm.getEndDateOfReceived()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        long dbCount=jdbcDaoSupport.getJdbcTemplate().queryForObject(countsql+sql,objList.toArray(),Long.class);
        int srart =pageable.getPageNumber()*pageable.getPageSize()+1;
        int end=(pageable.getPageNumber()+1)*pageable.getPageSize();
        sql="select * from ("+selsql+sql+") as inc where inc.rownum BETWEEN "+srart+" and "+end+"";
        List<InvoiceEntity> invoiceEntityList=jdbcDaoSupport.getJdbcTemplate().query(sql,objList.toArray(),new RowMapper<InvoiceEntity>() {
            @Override
            public InvoiceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                InvoiceEntity invoiceEntity=new InvoiceEntity();
                invoiceEntity.setInvoiceId(rs.getLong("invoiceId"));
                invoiceEntity.setNum(rs.getString("num"));
                invoiceEntity.setCarNum(rs.getString("carNum"));
                invoiceEntity.setSubmitDate(rs.getDate("submitDate"));
                invoiceEntity.setClientAddress(rs.getString("clientAddress"));
                invoiceEntity.setCreatedDate(rs.getDate("createdDate"));
                invoiceEntity.setDeliveryDate(rs.getDate("deliveryDate"));
                invoiceEntity.setRemark(rs.getString("remark"));
                invoiceEntity.setReceivedDate(rs.getDate("receivedDate"));
                invoiceEntity.setAuditStatus(rs.getInt("auditStatus"));
                invoiceEntity.setActive(rs.getBoolean("active"));

                InvoiceTypeEntity invoiceTypeEntity=new InvoiceTypeEntity();
                invoiceTypeEntity.setInvoiceTypeId(rs.getLong("invoiceTypeId"));
                invoiceTypeEntity.setName(rs.getString("name"));
                invoiceTypeEntity.setType(rs.getInt("type"));
                invoiceEntity.setInvoiceType(invoiceTypeEntity);

                IncEntity incEntity=new IncEntity();
                incEntity.setIncId(rs.getLong("incId"));
                incEntity.setIncName(rs.getString("incName"));
                invoiceEntity.setInc(incEntity);

                ClientEntity clientEntity=new ClientEntity();
                clientEntity.setClientId(rs.getLong("clientId"));
                clientEntity.setClientName(rs.getString("clientName"));
                invoiceEntity.setClient(clientEntity);
                return invoiceEntity;
            }
        });
        return new PageImpl<InvoiceEntity>(invoiceEntityList,pageable,dbCount);
    }
}
