package com.NexusPoint.model;

import java.time.LocalDate;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ITEM {
    private String itemID;
    private String itemName;
    private String itemAmount;
    private int cost;
    private LocalDate purchaseDate;
    private Boolean itemType;
    private String empID; // FK
    private String depID; // FK
    private int stutus; //-1 for late, 0 for normal, 1 for borrowed
}
