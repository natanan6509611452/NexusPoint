package com.NexusPoint.model;

import java.sql.Date;

public class DEPLETABLE extends ITEM{
    private Date restockDate;

    public Date getRestockDate() {
        return this.restockDate;
    }

    public void setRestockDate(Date restockDate) {
        this.restockDate = restockDate;
    }

}
