package com.NexusPoint.repository;

import com.NexusPoint.model.*;
import java.util.List;

public interface employeeInterface {
    public EMPLOYEE showInfo();
    public void editUserInfo(EMPLOYEE newInfo);
    public List<ITEM> itemStrSearch(String searchName);
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount);
    public List<empBorrowList> checkBorrowLists(String empID);
    public List<ITEM> showAllItem();
    public String login(String empID);
    public void logout();
    public boolean editPass(String newPass);
}
