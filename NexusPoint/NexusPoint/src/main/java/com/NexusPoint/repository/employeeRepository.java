package com.NexusPoint.repository;

import com.NexusPoint.model.*;

import java.sql.SQLException;
import java.util.List;

public interface employeeRepository {
    public EMPLOYEE fetchEmployee(String empID);
    public List<EMPLOYEE> fetchAllEmployee();
    public void editUserInfo(EMPLOYEE newInfo);
    public List<ITEM> itemStrSearch(String searchName);
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount);
    public List<EMPBORROWLIST> checkBorrowLists(String empID);
    public List<ITEM> fetchAllItem();
    public List<ITEM> fetchSomeItem(int start, int rows);
    public EMPLOYEE authenticate(USER user);
    public void insertPhoto(String empID) throws SQLException;
    public void editPass(String newPass, String empID);
    public void borrowItem(String empID, String itemID, int amounts) throws Exception;

    public void checkBorrowStatus();
}
