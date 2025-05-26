package com.NexusPoint.model;

import java.sql.Date;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class INDEPLETABLE extends ITEM {
    private Date maintenanceDate;
}