package com.lesso.newlp.material.entity;

import com.lesso.newlp.core.entity.AbstractTimestampEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sean on 6/17/2014.
 */
@Entity
@Table(name = "MAT_MATERIAL_ATTRIBUTE", schema = "DBO",catalog = "NEWLP")
public class MaterialAttributeEntity extends AbstractTimestampEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long materialAttributeId;
    String name;
    @Column(columnDefinition ="bit NULL DEFAULT ((1))")
    Boolean active=true;                     //默认为1，0为删除

//    @JsonIgnore
//    @RestResource(exported = false)
//    @OneToMany(mappedBy = "materialAttribute",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    Set<MaterialEntity> materials;


    public Long getMaterialAttributeId() {
        return materialAttributeId;
    }

    public void setMaterialAttributeId(Long materialAttributeId) {
        this.materialAttributeId = materialAttributeId;
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
}
