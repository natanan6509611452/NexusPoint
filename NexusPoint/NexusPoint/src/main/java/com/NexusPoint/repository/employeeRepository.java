package com.NexusPoint.repository;

import com.NexusPoint.model.*;

import java.sql.SQLException;
import java.util.List;

public interface employeeRepository {
    public EMPLOYEE showInfo(String empID);
    public void editUserInfo(EMPLOYEE newInfo);
    public List<ITEM> itemStrSearch(String searchName);
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount);
    public List<BORROW_ITEM> checkBorrowLists();
    public List<ITEM> showAllItem();
    public EMPLOYEE authenticate(USER user);
    public void insertPhoto() throws SQLException;
}
