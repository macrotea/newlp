package com.lesso.newlp.material.service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.material.model.SearchTerm;
import com.lesso.newlp.material.repository.MaterialRepository;
import com.lesso.newlp.material.vo.MaterialVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sean on 7/29/2014.
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Resource
    MaterialRepository materialRepository;

    @Resource
    JdbcDaoSupport jdbcDaoSupport;

    @Resource
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Page<MaterialEntity> search(SearchTerm searchTerm, Pageable pageable, String username) {
        Page<MaterialEntity> materialEntities;

        if(null == searchTerm.getType() && null == searchTerm.getQueryString()){
            materialEntities = materialRepository.search(pageable);
        }else if(null == searchTerm.getType()){
            materialEntities = materialRepository.search(searchTerm.getQueryString(),pageable);
        } else if(null == searchTerm.getQueryString()){
            materialEntities = materialRepository.search(searchTerm.getType(),pageable);
        }else{
            materialEntities = materialRepository.search(searchTerm.getQueryString(),searchTerm.getType(),pageable);
        }

        return materialEntities;
    }

    @Override
    public void delete(String materialNum) {
        jdbcDaoSupport.getJdbcTemplate().update("update MAT_MATERIAL set active=0 where materialNum=?", materialNum);
    }

    @Override
    public Page<MaterialVO> findByMaterialNumAndIncId(SearchTerm searchTerm, Pageable pageable, Long incId, Boolean isRelatedInc) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("incId",incId);

        String sqlTotal =  "SELECT\n" +
                "DISTINCT count(*) as total\n";

        String sqlSelect , sql = null;

        if(isRelatedInc){
             sqlSelect = "SELECT\n" +
                    "  cm.companyMaterialId,\n" +
                    "  m.materialNum,\n" +
                    "  m.name,\n" +
                    "  m.length,\n" +
                    "  m.unit,\n" +
                    "  m.auxiliaryUnitOne,\n" +
                    "  m.conversionRateOne,\n" +
                    "  m.productTypeOne,\n" +
                    "  m.productTypeTwo,\n" +
                    "  m.price,\n" +
                    "  cm.price inc_price,\n" +
                    "  cm.active,\n" +
                    "  cm.inventory,\n" +
                    "  ROW_NUMBER () OVER (ORDER BY materialNum DESC) AS rowNum\n";

             sql ="FROM\n" +
                    "  MAT_COMPANY_MATERIAL cm\n" +
                    "  LEFT JOIN MAT_MATERIAL m ON m.materialNum = cm.material_materialNum\n" +
                    "WHERE m.active=1 and cm.inc_incId = :incId";


            if(searchTerm.getActive()!=null){
                sql+=" and cm.active=:active ";
                parameterSource.addValue("active", searchTerm.getActive());
            }
        }else{
            sqlSelect = "SELECT\n" +
                    "\tm.materialNum,\n" +
                    "\tm.name,\n" +
                    "\tm.length,\n" +
                    "\tm.unit,\n" +
                    "\tm.auxiliaryUnitOne,\n" +
                    "\tm.conversionRateOne,\n" +
                    "\tm.productTypeOne,\n" +
                    "\tm.productTypeTwo,\n" +
                    "\tm.price,\n" +
                    "\tROW_NUMBER () OVER (ORDER BY materialNum DESC) AS rowNum";

            sql =" FROM\n" +
                    "\tMAT_MATERIAL m\n" +
                    "WHERE\n" +
                    "\t m.active =1 and NOT EXISTS (\n" +
                    "\t\tSELECT\n" +
                    "\t\t\t1\n" +
                    "\t\tFROM\n" +
                    "\t\t\tMAT_COMPANY_MATERIAL\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tM.materialNum = material_materialNum AND inc_incId = :incId\n" +
                    "\t)";

        }

        if(!Strings.isNullOrEmpty(searchTerm.getQueryString())){
            sql+=" and (m.materialNum like :queryString OR m.name like :queryString) ";
            parameterSource.addValue("queryString", "%"+searchTerm.getQueryString()+"%");
        }

        if(null != searchTerm.getType()){
            sql+=" and m.materialType_materialTypeId = :type ";
            parameterSource.addValue("type", searchTerm.getType());
        }

        int start =pageable.getPageNumber()*pageable.getPageSize();
        int end=(pageable.getPageNumber()+1)*pageable.getPageSize()+1;
        String sqlList="select * from ( "+sqlSelect+sql+" )a where a.rowNum>"+start+" and a.rowNum <"+end+" ";



        List<MaterialVO> list = namedParameterJdbcTemplate.query(sqlList, parameterSource, (rs, i) -> {
            MaterialVO m = new MaterialVO();
            m.setMaterialNum(rs.getString("materialNum"));
            m.setName(rs.getString("name"));
            m.setLength(rs.getDouble("length"));
            m.setUnit(rs.getString("unit"));
            m.setAuxiliaryUnitOne(rs.getString("auxiliaryUnitOne"));
            m.setConversionRateOne(rs.getDouble("conversionRateOne"));
            m.setProductTypeOne(rs.getString("productTypeOne"));
            m.setProductTypeTwo(rs.getString("productTypeTwo"));
            m.setPrice(rs.getBigDecimal("price"));
            if(isRelatedInc){
                m.setIncMaterialId(rs.getLong("companyMaterialId"));
                m.setIncPrice(rs.getBigDecimal("inc_price"));
                m.setActive(rs.getBoolean("active"));
                m.setInventory(rs.getDouble("inventory"));
            }

            return m;
        });



        Integer total = namedParameterJdbcTemplate.queryForObject(sqlTotal+sql, parameterSource, (rs, i) -> rs.getInt("total"));

        return new PageImpl<MaterialVO>(list,pageable,total);
    }

    @Override
    public MaterialVO update(Long incMaterialId, MaterialVO materialVO) {
        String sql = "update MAT_COMPANY_MATERIAL set active = :active,inventory = :inventory,price=:inc_price where companyMaterialId =:incMaterialId";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("active",materialVO.getActive());
        parameterSource.addValue("inventory",materialVO.getInventory());
        parameterSource.addValue("incMaterialId",incMaterialId);
        parameterSource.addValue("inc_price",materialVO.getIncPrice());

        namedParameterJdbcTemplate.update(sql,parameterSource);
        return materialVO;
    }

    @Override
    public MaterialVO addIncMaterial(String materialNum, Long incId) {
        String sql = "insert INTO MAT_COMPANY_MATERIAL(inventory,inc_incId,material_materialNum,price)\n" +
                "select '0',:incId,m.materialNum,m.price from MAT_MATERIAL m where rtrim(ltrim(m.materialNum)) = :materialNum";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("materialNum",materialNum);
        parameterSource.addValue("incId",incId);

        namedParameterJdbcTemplate.update(sql, parameterSource);
        return null;
    }

    @Override
    public List<String> findUsedMaterialNums(List<String> materialNums) {
        String nums =Joiner.on("','").join(materialNums);
        String sql = "select material_materialNum from MAT_COMPANY_MATERIAL  where material_materialNum in('"+nums+"')";
        List<String> usedMaterialNums =  jdbcDaoSupport.getJdbcTemplate().query(sql, (rs, i) -> rs.getString("material_materialNum"));
        return usedMaterialNums;
    }

}
