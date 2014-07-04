package com.lesso.newlp.material.vo;

import java.math.BigDecimal;

/**
 * Created by Sean on 6/18/2014.
 */
public class MaterialVO {
    String num;                                   //物料编码
    String name;                                 //物料名称
    String unit;                                   //主计量单位
    String auxiliaryUnitOne; //辅计量单位1
    String auxiliaryUnitTwo; //辅计量单位1
    Double conversionRateOne;//换算率1
    Double conversionRateTwo;//换算率2
    BigDecimal price;                             //单价
}
