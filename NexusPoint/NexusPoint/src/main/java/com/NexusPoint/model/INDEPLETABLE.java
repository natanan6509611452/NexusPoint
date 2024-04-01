package com.NexusPoint.model;

import java.sql.Date;

public class INDEPLETABLE extends ITEM {
    private Date maintenanceDate;

    public Date getMaintenanceDate() {
        return this.maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
}