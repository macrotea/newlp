package com.lesso.newlp.invoice.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.lesso.newlp.invoice.entity.InvoiceDetailEntity;
import com.lesso.newlp.invoice.entity.InvoiceEntity;
import com.lesso.newlp.invoice.entity.InvoiceTypeEntity;
import com.lesso.newlp.invoice.model.AuditStatus;
import com.lesso.newlp.invoice.model.SearchTerm;
import com.lesso.newlp.invoice.repository.InvoiceRepository;
import com.lesso.newlp.invoice.repository.InvoiceTypeRepository;
import com.lesso.newlp.log.model.Modified;
import com.lesso.newlp.log.repository.LogRepository;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.repository.MaterialTypeRepository;
import com.lesso.newlp.pm.entity.ClientEntity;
import com.lesso.newlp.pm.entity.IncEntity;
import com.lesso.newlp.pm.repository.MemberRepository;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceTypeRepository invoiceTypeRepository;

    @Resource
    MaterialTypeRepository materialTypeRepository;

    @Resource
    MemberRepository memberRepository;

    @Resource
    LogRepository logRepository;

    /**
     * 创建订单
     * @param invoice
     * @return
     */
    @Override
    @Transactional
    public InvoiceEntity save(InvoiceEntity invoice) {

        if(AuditStatus.DEPOT_SAVE == invoice.getAuditStatus()){
            invoice.setCreatedDate( new Date());
        }
        if(AuditStatus.ORDER_CREATOR_SAVE == invoice.getAuditStatus()){
            invoice.setCreatedDate( new Date());
        }
        if(AuditStatus.ORDER_CREATOR_SUBMIT == invoice.getAuditStatus()){
            invoice.setSubmitDate( new Date());
        }

        final InvoiceEntity finalInvoice = invoice;
        invoice.getInvoiceDetails().forEach(invoiceDetail -> {
            invoiceDetail.setInvoice(finalInvoice);
            invoiceDetail.setAmount(invoiceDetail.getIncPrice().multiply(new BigDecimal(Double.toString(invoiceDetail.getDeliveryCount()))));
        });

        InvoiceTypeEntity invoiceTypeEntity = invoiceTypeRepository.findOne(invoice.getInvoiceType().getInvoiceTypeId());


        /*生成流水号*/
        StringBuilder sequence = new StringBuilder();
        sequence.append(invoice.getInc().getIncShortName())
                .append(invoiceTypeEntity.getType() == 1 ? "R" : "C")
                .append(new DateTime().toString("yyMMdd"));


        String maxInvoiceNum = jdbcDaoSupport.getJdbcTemplate().queryForObject("SELECT max(i.invoiceNum) from INV_INVOICE i with (TABLOCKX) where i.invoiceNum like ?",new Object[]{"%"+sequence.toString()+"%"},String.class);


        if(null == maxInvoiceNum){
            sequence.append("0001");
        }else {
            sequence.append(
                    Strings.padStart(String.valueOf(Integer.parseInt(maxInvoiceNum
                            .substring(maxInvoiceNum.length() - 4, maxInvoiceNum.length())) + 1), 4, '0')
            );
        }

        invoice.setInvoiceNum(sequence.toString());

        invoice=  invoiceRepository.saveAndFlush(invoice);

//        int i = 1/0;
        return invoice;
    }

    /**
     *  根据订单Id查询
     * @param invoiceId 订单Id
     * @return
     */
    @Override
    public InvoiceEntity findById(Long invoiceId) throws Exception {


        List<InvoiceDetailEntity> list = jdbcDaoSupport.getJdbcTemplate().query("SELECT\n" +
                "\tj.*,\n" +
                "\tj.remark AS incDetailRemark,\n" +
                "\tj.active AS incDetailActive,\n" +
                "\tm.name AS materName,\n" +
                "\tm.unit AS materUnit,\n" +
                "\tm.price AS materPrice,\n" +
                "\tj.incPrice AS incPrice\n" +
                "FROM\n" +
                "\tINV_INVOICE_DETAIL j\n" +
                "LEFT JOIN INV_INVOICE i ON j.invoice_invoiceId = i.invoiceId\n" +
                "LEFT JOIN MAT_MATERIAL m ON j.material_materialNum = m.materialNum\n" +
                "WHERE\n" +
                "\tj.invoice_invoiceId = ?\n" +
                "AND j.active = 1 ", new Object[]{invoiceId}, new RowMapper<InvoiceDetailEntity>() {
            @Override
            public InvoiceDetailEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

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
                invoiceDetailEntity.setIncPrice(rs.getBigDecimal("incPrice"));

                MaterialEntity materialEntity = new MaterialEntity();
                materialEntity.setMaterialNum(rs.getString("material_materialNum"));
                materialEntity.setName(rs.getString("materName"));
                materialEntity.setUnit(rs.getString("materUnit"));
                materialEntity.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
                materialEntity.setAuxiliaryUnitTwo(rs.getString("auxiliaryUnitTwo"));
                materialEntity.setConversionRateOne(rs.getDouble("conversionRateOne"));
                materialEntity.setConversionRateTwo(rs.getDouble("conversionRateTwo"));
                materialEntity.setPrice(rs.getBigDecimal("materPrice"));
                invoiceDetailEntity.setMaterial(materialEntity);

                return invoiceDetailEntity;
            }
        });
        InvoiceEntity invoice = jdbcDaoSupport.getJdbcTemplate().queryForObject("SELECT\n" +
                "\t*\n" +
                "\n" +
                "FROM\n" +
                "\tINV_INVOICE i\n" +
                "LEFT JOIN INV_INVOICE_TYPE t ON i.invoiceType_invoiceTypeId = t.invoiceTypeId\n" +
                "LEFT JOIN PM_INC inc ON i.inc_incId = inc.incId\n" +
                "LEFT JOIN PM_CLIENT incClient ON i.client_clientId = incClient.clientId\n" +
                "WHERE\n" +
                "  \n" +
                "\ti.invoiceId = ?\n" +
                " AND i.active =1 \n" +
                "\n", new Object[]{invoiceId}, new RowMapper<InvoiceEntity>() {
            @Override
            public InvoiceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                InvoiceEntity invoice = new InvoiceEntity();
                invoice.setInvoiceId(rs.getLong("invoiceId"));
                invoice.setInvoiceNum(rs.getString("invoiceNum"));
                invoice.setCarNum(rs.getString("carNum"));
                invoice.setShift(rs.getString("shift"));
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

                return invoice;
            }
        });
        if (null != list) {
            Set<InvoiceDetailEntity> set = new HashSet<InvoiceDetailEntity>();
            set.addAll(list);
            invoice.setInvoiceDetails(set);
        }

        return invoice;
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
                }
            });
            Iterator it=set.iterator();
            while(it.hasNext()){
                InvoiceDetailEntity ide =(InvoiceDetailEntity) it.next();
                ide.setAmount(ide.getIncPrice().multiply(new BigDecimal(Double.toString(ide.getDeliveryCount()))));
                if(invoiceDataDBID.contains(ide.getInvoiceDetailId())){
                    jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE_DETAIL set active=?, amount=?,deliveryCount=?,orderCount=?,remark=?," +
                                    "invoice_invoiceId=?,material_materialNum=?,auxiliaryUnitOne=?,auxiliaryUnitTwo=?,conversionRateOne=?,conversionRateTwo=?,price=?,incPrice=?,unit=? WHERE invoiceDetailId=?",
                            ide.getActive(),ide.getAmount(),ide.getDeliveryCount(),ide.getOrderCount(),ide.getRemark(),invoice.getInvoiceId(),ide.getMaterial().getMaterialNum(),ide.getAuxiliaryUnitOne()
                            ,ide.getAuxiliaryUnitTwo(),ide.getConversionRateOne(),ide.getConversionRateTwo(),ide.getPrice(),ide.getIncPrice(),ide.getUnit(),ide.getInvoiceDetailId());
                    invoiceDataDBID.remove(ide.getInvoiceDetailId());
                }else {
                    jdbcDaoSupport.getJdbcTemplate().update("insert into INV_INVOICE_DETAIL(updated,amount,deliveryCount,orderCount,remark,invoice_invoiceId,material_materialNum,auxiliaryUnitOne,auxiliaryUnitTwo,conversionRateOne,conversionRateTwo,price,incPrice,unit)" +
                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Date(),ide.getAmount(),ide.getDeliveryCount(),ide.getOrderCount(),ide.getRemark(),
                            invoice.getInvoiceId(),ide.getMaterial().getMaterialNum(),ide.getAuxiliaryUnitOne(),
                            ide.getAuxiliaryUnitTwo(),ide.getConversionRateOne(),
                            ide.getConversionRateTwo(),ide.getPrice(),ide.getIncPrice(),ide.getUnit());
                }
            }

            if(invoiceDataDBID.size()>0){
                String inid="";
                for(Object o:invoiceDataDBID){
                    inid+=o.toString()+",";
                }
                inid=inid.substring(0,inid.length()-1);
                jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE_DETAIL set active=0 where invoiceDetailId in("+inid+")");
            }

        }
        jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE  set invoiceNum=?,inc_incId=?," +
                        "carNum=?,client_clientId=?,clientAddress=?," +
                        "receivedDate=?,auditStatus=?,remark=?,active=?,invoiceType_invoiceTypeId=?,shift=? where invoiceId=?",
                invoice.getInvoiceNum(),
                invoice.getInc().getIncId(), invoice.getCarNum(),
                invoice.getClient().getClientId(),invoice.getClientAddress(),
                invoice.getReceivedDate(),
                invoice.getAuditStatus(), invoice.getRemark(), invoice.getActive
                (), invoice.getInvoiceType().getInvoiceTypeId(),invoice.getShift(),
                invoice.getInvoiceId());
        return invoice;
    }

    @Override
    @Transactional
    public InvoiceEntity patch(Long invoiceId, InvoiceEntity invoice) throws InvocationTargetException, IllegalAccessException {
        InvoiceEntity  invoiceEntity = invoiceRepository.findOne(invoiceId);
        if (!Objects.isNull(invoice.getAuditStatus())) {
            invoiceEntity.setAuditStatus(invoice.getAuditStatus());
        }

        if (!Objects.isNull(invoice.getInvoiceDetails())) {
            final InvoiceEntity finalInvoice = invoice;
            invoiceEntity.getInvoiceDetails().forEach(a -> {
                finalInvoice.getInvoiceDetails().forEach(b -> {
                    if (a.getInvoiceDetailId().equals(b.getInvoiceDetailId())) {
                        a.setDeliveryCount(b.getDeliveryCount());
                    }
                    if (a.getInvoiceDetailId().equals(b.getInvoiceDetailId())) {
                        a.setAmount(a.getIncPrice().multiply(new BigDecimal(Double.toString(a.getDeliveryCount()))));
                    }
                });
            });
        }

        return invoiceRepository.saveAndFlush(invoiceEntity);
//        return ((InvoiceService)AopContext.currentProxy()).update(invoiceEntity);
    }

    /**
     * 删除订单
     * @param invoiceId
     */
    @Override
    public void delete(Long invoiceId) {
        jdbcDaoSupport.getJdbcTemplate().update("update INV_INVOICE set active=0 where invoiceId=?", invoiceId);
    }


    /**
     * 根据订单审核状态查询
     * @param auditStatus
     * @param pageable
     * @return
     */
    @Override
    public Page<InvoiceEntity> queryByAuditStatus(Integer auditStatus,String memberId, Pageable pageable) {

        Page<InvoiceEntity> page = invoiceRepository.findByAuditStatus(auditStatus,memberId,pageable);

        return page;
    }

    @Override
    public int queryCountByOriginalAuditStatus(int auditedStatus, String username) {
        String sql = "select count(DISTINCT inv.invoiceId) from INV_INVOICE inv\n" +
                "left join PM_INC_MEMBER_REL incm\n" +
                "on incm.inc_incId = inv.inc_incId\n" +
                "left join PM_CLIENT_MEMBER_REL clim\n" +
                "on clim.client_clientId = inv.client_clientId\n" +
                "left join LOG_OPERATION log on log.invoice_invoiceId = inv.invoiceId " +
                "where incm.member_memberId = :memberId and clim.member_memberId = :memberId and inv.active = 1 and inv.auditStatus >= :auditedStatus and (\n" +
                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\"0\"%' or \n" +
                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\""+(auditedStatus - 10)+"\"%'\n" +
                ") ";


        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("memberId",username);
        parameters.addValue("auditedStatus",auditedStatus);


        Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count;
    }

    @Override
    public Page<InvoiceEntity> searchByOriginalAuditStatuses(Integer auditedStatus, String memberId, Pageable pageable) {

        Page<InvoiceEntity> page = invoiceRepository.findByAuditedStatus(auditedStatus,memberId,pageable);


//        String sql = "select " +
//                " distinct inv.invoiceId, " +
//                "invt.type, " +
//                "invt.name, " +
//                "inv.invoiceNum, " +
//                "inc.incName, " +
//                "cli.clientName, " +
//                "inv.receivedDate, " +
//                "inv.carNum " +
//                " from INV_INVOICE inv " +
//                "left join INV_INVOICE_TYPE invt " +
//                "on invt.invoiceTypeId = inv.invoiceType_invoiceTypeId " +
//                "left join pm_inc inc " +
//                "on inc.incId = inv.inc_incId " +
//                "left join pm_client cli " +
//                "on cli.clientId = inv.client_clientId " +
//                "left join PM_INC_MEMBER_REL incm " +
//                "on incm.inc_incId = inv.inc_incId " +
//                "left join PM_CLIENT_MEMBER_REL clim " +
//                "on clim.client_clientId = inv.client_clientId " +
//                "left join LOG_OPERATION log on log.invoice_invoiceId = inv.invoiceId " +
//                "where incm.member_memberId = :memberId and clim.member_memberId = :memberId and inv.active = 1 and inv.auditStatus >= :auditedStatus and ( " +
//                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\"0\"%' or  " +
//                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\""+(auditedStatus - 10)+"\"%' " +
//                ") ";
//
//        MapSqlParameterSource parameters = new MapSqlParameterSource();
//        parameters.addValue("memberId",memberId);
//        parameters.addValue("auditedStatus",auditedStatus);
//
//        List<InvoiceEntity> list = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<InvoiceEntity>() {
//            @Override
//            public InvoiceEntity mapRow(ResultSet resultSet, int i) throws SQLException {
//                IncEntity incEntity = new IncEntity();
//                incEntity.setIncName(resultSet.getString("incName"));
//
//                ClientEntity clientEntity = new ClientEntity();
//                clientEntity.setClientName(resultSet.getString("clientName"));
//
//                InvoiceTypeEntity invoiceTypeEntity = new InvoiceTypeEntity();
//                invoiceTypeEntity.setType(resultSet.getInt("type"));
//                invoiceTypeEntity.setName(resultSet.getString("name"));
//
//
//                InvoiceEntity invoiceEntity = new InvoiceEntity();
//                invoiceEntity.setInvoiceId(resultSet.getLong("invoiceId"));
//                invoiceEntity.setInvoiceNum(resultSet.getString("invoiceNum"));
//                invoiceEntity.setReceivedDate(resultSet.getDate("receivedDate"));
//                invoiceEntity.setCarNum(resultSet.getString("carNum"));
//                invoiceEntity.setInvoiceType(invoiceTypeEntity);
//                invoiceEntity.setClient(clientEntity);
//                invoiceEntity.setInc(incEntity);
//                return invoiceEntity;
//            }
//        });
//
//        Integer total = ((InvoiceService)AopContext.currentProxy()).queryCountByOriginalAuditStatus(auditedStatus,memberId);
//        return new PageImpl<InvoiceEntity>(list, pageable, total);
        return page;
    }



    @Override
    public Page<InvoiceEntity> searchByOriginalAuditStatus(SearchTerm searchTerm, Integer auditedStatus, Pageable pageable, String memberId) {

        String logSql = " (\n" +
                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\"0\"%' or \n" +
                "log.description like '%\"modifiedVal\":\""+auditedStatus+"\",\"name\":\"auditStatus\",\"originalVal\":\""+(auditedStatus - 10)+"\"%'\n" +
                ") ";

        String countsql="select count(DISTINCT invoiceID) ";
        String selsql="select DISTINCT i.invoiceId,i.invoiceNum,i.receivedDate,t.name,t.type,t.invoiceTypeId,p.incId,p.incName,c.clientId,c.clientName,DENSE_RANK() OVER (ORDER BY i.invoiceId desc) as rownum ";
        String sql=" from  INV_INVOICE i " +
                "LEFT JOIN INV_INVOICE_TYPE t on i.invoiceType_invoiceTypeId=t.invoiceTypeId " +
                "LEFT JOIN PM_INC p on i.inc_incId =p.incId " +
                "left join PM_INC_MEMBER_REL incm on incm.inc_incId = i.inc_incId\n" +
                "left join PM_CLIENT_MEMBER_REL clim on clim.client_clientId = i.client_clientId " +
                "left join LOG_OPERATION log on log.invoice_invoiceId = i.invoiceId " +
                "LEFT JOIN PM_CLIENT c on c.clientId=i.client_clientId where 1=1 and i.active =1 and incm.member_memberId = ? and clim.member_memberId = ? and i.auditStatus >= ? and "+logSql;
        List<Object> objList=new ArrayList<Object>();

        objList.add(memberId);
        objList.add(memberId);
        objList.add(auditedStatus);

//        if(searchTerm.getAuditStatus() !=null){
//            sql+=" and i.auditStatus=?";
//            objList.add(searchTerm.getAuditStatus());
//        }
        if(searchTerm.getInvoiceTypeId()!=null){
            sql+=" and i.invoiceType_invoiceTypeId=?";
            objList.add(searchTerm.getInvoiceTypeId());
        }
        if(searchTerm.getNum()!=null&&searchTerm.getNum().length()>0){
            sql+=" and i.invoiceNum like '%'+?+'%'";
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
        if( null != searchTerm.getReceivedDateRange() && searchTerm.getReceivedDateRange().getStartDate()!=null){
            sql+=" and i.receivedDate>=?";
            objList.add(new DateTime(searchTerm.getReceivedDateRange().getStartDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if( null != searchTerm.getReceivedDateRange() && searchTerm.getReceivedDateRange().getEndDate()!=null){
            sql+=" and i.receivedDate<=?";
            objList.add(new DateTime(searchTerm.getReceivedDateRange().getEndDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
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
                invoiceEntity.setInvoiceNum(rs.getString("invoiceNum"));
//                invoiceEntity.setCarNum(rs.getString("carNum"));
//                invoiceEntity.setSubmitDate(rs.getDate("submitDate"));
//                invoiceEntity.setClientAddress(rs.getString("clientAddress"));
//                invoiceEntity.setCreatedDate(rs.getDate("createdDate"));
//                invoiceEntity.setDeliveryDate(rs.getDate("deliveryDate"));
//                invoiceEntity.setRemark(rs.getString("remark"));
                invoiceEntity.setReceivedDate(rs.getDate("receivedDate"));
//                invoiceEntity.setAuditStatus(rs.getInt("auditStatus"));
//                invoiceEntity.setActive(rs.getBoolean("active"));

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



    @Override
    public Page<InvoiceEntity> search(SearchTerm searchTerm, Pageable pageable,String memberId) {
        String countsql="select count(invoiceID) ";
        String selsql="select i.*,t.name,t.type,t.invoiceTypeId,p.incId,p.incName,c.clientId,c.clientName,ROW_NUMBER() OVER (ORDER BY i.invoiceId desc) as rownum ";
        String sql=" from  INV_INVOICE i " +
                "LEFT JOIN INV_INVOICE_TYPE t on i.invoiceType_invoiceTypeId=t.invoiceTypeId " +
                "LEFT JOIN PM_INC p on i.inc_incId =p.incId " +
                "left join PM_INC_MEMBER_REL incm on incm.inc_incId = i.inc_incId\n" +
                "left join PM_CLIENT_MEMBER_REL clim on clim.client_clientId = i.client_clientId " +
                "LEFT JOIN PM_CLIENT c on c.clientId=i.client_clientId where 1=1 and i.active =1 and incm.member_memberId = ? and clim.member_memberId = ?";
        List<Object> objList=new ArrayList<Object>();

        objList.add(memberId);
        objList.add(memberId);

        if(searchTerm.getAuditStatus() !=null){
            sql+=" and i.auditStatus=?";
            objList.add(searchTerm.getAuditStatus());
        }
        if(searchTerm.getInvoiceTypeId()!=null){
            sql+=" and i.invoiceType_invoiceTypeId=?";
            objList.add(searchTerm.getInvoiceTypeId());
        }
        if(searchTerm.getNum()!=null&&searchTerm.getNum().length()>0){
            sql+=" and i.invoiceNum like '%'+?+'%'";
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
        if( null != searchTerm.getReceivedDateRange() && searchTerm.getReceivedDateRange().getStartDate()!=null){
            sql+=" and i.receivedDate>=?";
            objList.add(new DateTime(searchTerm.getReceivedDateRange().getStartDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if( null != searchTerm.getReceivedDateRange() && searchTerm.getReceivedDateRange().getEndDate()!=null){
            sql+=" and i.receivedDate<=?";
            objList.add(new DateTime(searchTerm.getReceivedDateRange().getEndDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
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
                invoiceEntity.setInvoiceNum(rs.getString("invoiceNum"));
                invoiceEntity.setCarNum(rs.getString("carNum"));
                invoiceEntity.setShift(rs.getString("shift"));
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

    @Override
    public Integer getPreAuditStatusByInvoiceId(Long invoiceId) {
         Integer preAuditStatus = 50;

        String sql = "select log.description from LOG_OPERATION log where log.entity = 'invoice' and log.type = 'CREATE' and log.invoice_invoiceId = :invoiceId";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("invoiceId",invoiceId);

        String description = namedParameterJdbcTemplate.queryForObject(sql,parameterSource,String.class);

        List<Modified> modifieds = JSON.parseArray(description,Modified.class);
        for(Modified modified :modifieds){
            if("auditStatus".equals(modified.getName())){
                if( 50 > Integer.parseInt(modified.getModifiedVal())){
                    preAuditStatus = 60;
                    break;
                }
            }
        }

        return preAuditStatus;

    }

    @Override
    public int queryCountByAuditStatus(Set<Integer> auditStatuses, String username) {
        String sql = "select count(inv.invoiceId) from INV_INVOICE inv\n" +
                "left join PM_INC_MEMBER_REL incm\n" +
                "on incm.inc_incId = inv.inc_incId\n" +
                "left join PM_CLIENT_MEMBER_REL clim\n" +
                "on clim.client_clientId = inv.client_clientId\n" +
                "where incm.member_memberId = :memberId and clim.member_memberId = :memberId and inv.auditStatus in(:auditStatuses) and inv.active = 1";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("auditStatuses", auditStatuses);
        parameters.addValue("memberId",username);


        Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count;
    }
}
