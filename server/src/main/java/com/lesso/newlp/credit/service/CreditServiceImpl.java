package com.lesso.newlp.credit.service;

import com.lesso.newlp.credit.entity.CreditEntity;
import com.lesso.newlp.credit.model.SearchTerm;
import com.lesso.newlp.credit.repository.CreditRepository;
import com.lesso.newlp.pm.entity.ClientEntity;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sean on 7/3/2014.
 */
@Service
public class CreditServiceImpl implements CreditService {
    @Resource
    JdbcDaoSupport jdbcDaoSupport;

    @Resource
    CreditRepository creditRepository;

    @Override
    public CreditEntity save(CreditEntity creditEntity) {
        return creditRepository.save(creditEntity);
    }

    @Override
    public CreditEntity findById(Long creditId) throws Exception {
        CreditEntity creditEntity = jdbcDaoSupport.getJdbcTemplate().queryForObject("SELECT *  FROM CRE_CREDIT i\n" +
                "left join PM_CLIENT p on i.client_clientId = p.clientId\n" +
                "WHERE i.creditId = ? and i.active = 1", new Object[]{creditId}, new RowMapper<CreditEntity>() {
            @Override
            public CreditEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CreditEntity creditEntity = new CreditEntity();
                creditEntity.setCreditId(rs.getLong("creditId"));
                creditEntity.setActive(rs.getBoolean("active"));
                creditEntity.setType(rs.getInt("type"));
                creditEntity.setAmount(rs.getBigDecimal("amount"));
                creditEntity.setPercent(rs.getDouble("percent_"));
                creditEntity.setCreditAmount(rs.getBigDecimal("creditAmount"));
                creditEntity.setInsertDate(rs.getDate("insertDate"));
                creditEntity.setValidDate(rs.getDate("validDate"));
                creditEntity.setExpiryDate(rs.getDate("expiryDate"));
                creditEntity.setDescription(rs.getString("description"));

                ClientEntity clientEntity=new ClientEntity();
                clientEntity.setClientId(rs.getLong("clientId"));
                clientEntity.setClientNum(rs.getString("clientNum"));
                clientEntity.setClientName(rs.getString("clientName"));
                creditEntity.setClient(clientEntity);

                return creditEntity ;
            }
        });
        return creditEntity;
    }

    @Override
    public CreditEntity update(CreditEntity creditEntity) {
        creditEntity = creditRepository.saveAndFlush(creditEntity);
        return  creditEntity;
    }

    @Override
    public void delete(Long creditId) {
        jdbcDaoSupport.getJdbcTemplate().update("update CRE_CREDIT set active=0 where creditId=?",new Object[]{creditId});
    }

    @Override
    public Page<CreditEntity> search(SearchTerm searchTerm, Pageable pageable, String memberId) {
        String sql = "select i.creditId,i.active,p.clientId,p.clientName,p.clientNum,i.validDate,i.expiryDate,i.insertDate,i.type,i.description, \n" +
                "i.amount,i.percent_,i.creditAmount,\n" +
                "ROW_NUMBER () OVER (ORDER BY i.creditId DESC) AS rowNum from CRE_CREDIT i\n" +
                "left join PM_CLIENT p on i.client_clientId = p.clientId\n" +
                "left join PM_INC_MEMBER_REL incm on incm.inc_incId = i.inc_incId " +
                "left join PM_CLIENT_MEMBER_REL clim on clim.client_clientId = i.client_clientId " +
                "where i.active =1 and clim.member_memberId = ? and incm.member_memberId = ?";
        List<Object> objList=new ArrayList<Object>();

        objList.add(memberId);
        objList.add(memberId);

        if(searchTerm.getClientId() !=null){
            sql+=" and i.client_clientId = ?";
            objList.add(searchTerm.getClientId());
        }
        if(searchTerm.getCreditId() !=null){
            sql+=" and i.creditId = ?";
            objList.add(searchTerm.getCreditId());
        }
        if(searchTerm.getType()!=null){
            sql+=" and i.type = ?";
            objList.add(searchTerm.getType());
        }
        if(searchTerm.getAmount()!=null){
            sql+=" and i.amount = ?";
            objList.add(searchTerm.getAmount());
        }
        if(searchTerm.getPercent()!=null){
            sql+=" and i.percent = ?";
            objList.add(searchTerm.getPercent());
        }
        if(searchTerm.getCreditAmount()!=null){
            sql+=" and i.creditAmount = ?";
            objList.add(searchTerm.getCreditAmount());
        }
        if(searchTerm.getInsertDate()!=null){
            sql+=" and i.insertDate = ?";
            objList.add(new DateTime(searchTerm.getInsertDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if(null != searchTerm.getValidDateRange() && null != searchTerm.getValidDateRange().getStartDate()){
            sql+=" and i.validDate >= ?";
            objList.add(new DateTime(searchTerm.getValidDateRange().getStartDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if(null != searchTerm.getValidDateRange() && null != searchTerm.getValidDateRange().getEndDate()){
            sql+=" and i.validDate <= ?";
            objList.add(new DateTime(searchTerm.getValidDateRange().getEndDate()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
        }
        if(searchTerm.getDescription()!=null){
            sql+=" and i.description = ?";
            objList.add(searchTerm.getDescription());
        }
        int start =pageable.getPageNumber()*pageable.getPageSize();
        int end=(pageable.getPageNumber()+1)*pageable.getPageSize()+1;
        String sqlList="select * from ( "+sql+" )a where a.rowNum>"+start+" and a.rowNum <"+end+" ";

        List<CreditEntity> creditEntityList=jdbcDaoSupport.getJdbcTemplate().query(sqlList,objList.toArray(),new RowMapper<CreditEntity>() {
            @Override
            public CreditEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CreditEntity creditEntity=new CreditEntity();
                creditEntity.setCreditId(rs.getLong("creditId"));
                creditEntity.setActive(rs.getBoolean("active"));
                creditEntity.setType(rs.getInt("type"));
                creditEntity.setAmount(rs.getBigDecimal("amount"));
                creditEntity.setPercent(rs.getDouble("percent_"));
                creditEntity.setCreditAmount(rs.getBigDecimal("creditAmount"));
                creditEntity.setInsertDate(rs.getDate("insertDate"));
                creditEntity.setValidDate(rs.getDate("validDate"));
                creditEntity.setExpiryDate(rs.getDate("expiryDate"));
                creditEntity.setDescription(rs.getString("description"));

                ClientEntity clientEntity=new ClientEntity();
                clientEntity.setClientId(rs.getLong("clientId"));
                clientEntity.setClientNum(rs.getString("clientNum"));
                clientEntity.setClientName(rs.getString("clientName"));
                creditEntity.setClient(clientEntity);

                return creditEntity;
            }
        });
        long dbCount=jdbcDaoSupport.getJdbcTemplate().queryForObject("SELECT count(DISTINCT a.rowNum) FROM( "+sql+" )a",objList.toArray(), Long.class);
        return new PageImpl<CreditEntity>(creditEntityList,pageable,dbCount);

    }

    @Override
    public CreditEntity patch(Long creditId, CreditEntity credit) {
        CreditEntity creditEntity = creditRepository.findOne(creditId);

        if (!Objects.isNull(credit.getClient())) {
            creditEntity.setClient(credit.getClient());
        }

        if (!Objects.isNull(credit.getValidDate())) {
            creditEntity.setValidDate(credit.getValidDate());
        }

        if (!Objects.isNull(credit.getExpiryDate())) {
            creditEntity.setExpiryDate(credit.getExpiryDate());
        }

        if (!Objects.isNull(credit.getType())) {
            creditEntity.setType(credit.getType());
        }

        if (!Objects.isNull(credit.getDescription())) {
            creditEntity.setDescription(credit.getDescription());
        }

        if (!Objects.isNull(credit.getAmount())) {
            creditEntity.setAmount(credit.getAmount());
        }

        if (!Objects.isNull(credit.getPercent())) {
            creditEntity.setPercent(credit.getPercent());
        }

        if (!Objects.isNull(credit.getCreditAmount())) {
            creditEntity.setCreditAmount(credit.getCreditAmount());
        }

        credit = creditRepository.save(creditEntity);

        return credit;
    }
}
