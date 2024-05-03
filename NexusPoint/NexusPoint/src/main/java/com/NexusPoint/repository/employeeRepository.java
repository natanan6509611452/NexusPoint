package com.NexusPoint.repository;

import com.NexusPoint.model.*;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface employeeRepository {
    public EMPLOYEE fetchEmployee(String empID);
    public List<EMPLOYEE> fetchAllEmployee();
    public EMPLOYEE editEmployeeInfo(EMPLOYEE newInfo);
    public List<ITEM> itemStrSearch(String searchName);
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount);
    public List<EMPBORROWLIST> checkBorrowLists(String empID);
    public List<ITEM> fetchAllItem();
    public List<ITEM> fetchSomeItem(int start, int rows);
    public EMPLOYEE authenticate(USER user);
    public void insertPhoto(EMPPHOTO empphoto) throws SQLException;
    public void insertItemPhoto(ITEMPHOTO itemphoto) throws SQLException;
    public void editPass(String newPass, String empID);
    public void borrowItem(String empID, String itemID, int amounts) throws Exception;

    public void checkBorrowStatus();

    public DEPARTMENT checkDepartment(String deptID);
    public ITEM fetchItem(String itemID);
    public List<BORROW_ITEM_DATA> fetchBorrowStatus(String empID);
    public void insertIm(String empID) throws SQLException;

    public ITEM editItemInfo(ITEM newInfo);

    public void deleteItem(String itemID);

    void deleteEmployee(String empID);

    List<ITEM> fetchItemByName(String itemName);

    List<ITEM> fetchItemByID(String itemID);

    List<EMPLOYEE> fetchEmpByID(String empID);

    List<EMPLOYEE> fetchEmpByName(String empName);

    void addEmployee(EMPLOYEE newEmp) throws SQLException;

    void addItem(INDEPLETABLE newItem) throws SQLException;
    void addItem(DEPLETABLE newItem) throws SQLException;

}
