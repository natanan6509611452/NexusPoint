package com.NexusPoint.model;

import java.time.LocalDate;

public class ITEM {
    private String itemID;
    private String itemName;
    private String itemAmount;
    private int cost;
    private LocalDate purchaseDate;
    private Boolean itemType;
    private String empID; // FK
    private String depID; // FK

    public String getItemID() {
        return this.itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return this.itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Boolean getItemType() {
        return this.itemType;
    }

    public void setItemType(Boolean itemType) {
        this.itemType = itemType;
    }

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getDepID() {
        return this.depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

}
