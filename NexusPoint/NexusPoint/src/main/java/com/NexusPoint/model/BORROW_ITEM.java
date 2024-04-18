package com.NexusPoint.model;

import java.sql.Date;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BORROW_ITEM {
    private String empID; // FK
    private String itemID; // FK
    private Date BorrowDate;
    private Date ReturnDate;
    private int BorrowAmount;
    private String BorrowStatus;

}
