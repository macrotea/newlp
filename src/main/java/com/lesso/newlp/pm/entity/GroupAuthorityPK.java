package com.lesso.newlp.pm.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Sean on 8/12/2014.
 */
@Embeddable
public class GroupAuthorityPK implements Serializable {

    @Column(nullable = false, insertable = false, updatable = false)
    String authority;

    @Column(nullable = false, insertable = false, updatable = false)
    String group_groupId;
}
