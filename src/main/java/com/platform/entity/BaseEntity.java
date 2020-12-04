package com.platform.entity;

import java.util.Date;

/**
 * Created by luxx on 2017/11/2 0002.
 */
public class BaseEntity {

    private Date created;
    private Date updated;

    public BaseEntity() {
        this.created = new Date();
        this.updated = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
        this.updated = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
