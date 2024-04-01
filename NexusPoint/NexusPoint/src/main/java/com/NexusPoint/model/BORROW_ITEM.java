package com.NexusPoint.model;

import java.sql.Date;

public class BORROW_ITEM {
    private String empID; // FK
    private String itemID; // FK
    private Date BorrowDate;
    private Date ReturnDate;
    private int BorrowAmount;
    private String BorrowStatus;

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getItemID() {
        return this.itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public Date getBorrowDate() {
        return this.BorrowDate;
    }

    public void setBorrowDate(Date BorrowDate) {
        this.BorrowDate = BorrowDate;
    }

    public Date getReturnDate() {
        return this.ReturnDate;
    }

    public void setReturnDate(Date ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public int getBorrowAmount() {
        return this.BorrowAmount;
    }

    public void setBorrowAmount(int BorrowAmount) {
        this.BorrowAmount = BorrowAmount;
    }

    public String getBorrowStatus() {
        return this.BorrowStatus;
    }

    public void setBorrowStatus(String BorrowStatus) {
        this.BorrowStatus = BorrowStatus;
    }

}
