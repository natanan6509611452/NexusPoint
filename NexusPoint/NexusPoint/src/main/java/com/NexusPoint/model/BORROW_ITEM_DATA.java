package com.NexusPoint.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BORROW_ITEM_DATA {
    private String empID; // FK
    private String itemID; // FK
    private Date BorrowDate;
    private Date ReturnDate;
    private int BorrowAmount;
    private String BorrowStatus;
    private String itemName;
    private int itemAmount;
    private int cost;
    private LocalDate purchaseDate;
    private Boolean itemType;
    private String depID; // FK
    private String itemPhoto;

}
