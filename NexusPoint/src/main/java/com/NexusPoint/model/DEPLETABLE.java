package com.NexusPoint.model;

import java.sql.Date;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter

public class DEPLETABLE extends ITEM{
    private Date restockDate;

}
