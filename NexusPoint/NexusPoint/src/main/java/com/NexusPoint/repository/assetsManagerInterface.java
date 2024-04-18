package com.NexusPoint.repository;

import com.NexusPoint.model.*;
import java.util.List;

public interface assetsManagerInterface extends employeeInterface{
    public void addItem(ITEM newItem);
    public void editItem(ITEM newItem);
    public void removeItem(String itemID);
    
    public List<BORROW_ITEM> showAllBorrowList();
    public List<BORROW_ITEM> filterBorrowList(Boolean depletable, Boolean borrow, Boolean Normal, Boolean Late);
    public void editBorrowStat(BORROW_ITEM newStat);
    public List<BORROW_ITEM> searchBorrowStat(String str);
    
    public void addAcc(EMPLOYEE newEmp);
    public void removeAcc();
    public void editInfo(EMPLOYEE newEmp);

    public List<EMPLOYEE> searchAcc(String str);
    public List<EMPLOYEE> filterAcc(String empRole);
    public List<EMPLOYEE> showAllAcc();
}