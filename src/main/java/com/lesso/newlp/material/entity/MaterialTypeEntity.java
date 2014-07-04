package com.lesso.newlp.material.entity;

import com.lesso.newlp.core.entity.AbstractTimestampEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sean on 6/17/2014.
 */
@Entity
@Table(name = "MAT_MATERIAL_TYPE", schema = "DBO",catalog = "NEWLP")
public class MaterialTypeEntity extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long materialTypeId;
    String name;
    String shortName;
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除

//    @JsonIgnore
//    @RestResource(exported = false)
//    @OneToMany(mappedBy = "materialType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    Set<MaterialEntity> materials;


    public Long getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Long materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
