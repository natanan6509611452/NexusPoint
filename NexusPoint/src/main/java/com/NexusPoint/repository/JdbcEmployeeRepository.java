package com.NexusPoint.repository;

import com.NexusPoint.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Repository
public class JdbcEmployeeRepository implements employeeRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public EMPLOYEE authenticate(USER user) {
        EMPLOYEE emp;
        String sql = "SELECT * FROM EMPLOYEE WHERE empID = ? AND empIDPass = ?";
        try {
            emp = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(EMPLOYEE.class), user.getUsername(), user.getPassword());
            return emp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public EMPLOYEE fetchEmployee(String empID) {
        String sql = "SELECT * FROM [dbo].[EMPLOYEE] WHERE empID = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(EMPLOYEE.class), empID);
    }

    @Override
    public List<EMPLOYEE> fetchAllEmployee() {
        return jdbc.query("SELECT * FROM EMPLOYEE", new BeanPropertyRowMapper<>(EMPLOYEE.class));
    }

    @Override
    public EMPLOYEE editEmployeeInfo(EMPLOYEE newInfo) {
        String sql = "UPDATE EMPLOYEE\n" +
                "SET FName = ?, " +
                "MName = ?, " +
                "LName = ?, " +
                "empBdate = ?, " +
                "HouseNo = ?, " +
                "Road = ?, " +
                "SubDistrict = ?, " +
                "District = ?, " +
                "Province = ?, " +
                "Country = ?, " +
                "Postcode = ?, " +
                "empTel = ?, " +
                "empMail = ?, " +
                "empRole = ? " +
                "WHERE empID = ?;";
        jdbc.update(sql, newInfo.getFname(), newInfo.getMname(), newInfo.getLname(),
                newInfo.getEmpBDate(), newInfo.getHouseNo(), newInfo.getRoad(), newInfo.getSubDistrict(),
                newInfo.getDistrict(), newInfo.getProvince(), newInfo.getCountry(), newInfo.getPostcode(),
                newInfo.getEmpTel(), newInfo.getEmpMail(), newInfo.getEmpRole(), newInfo.getEmpID());
        return jdbc.queryForObject("SELECT * FROM EMPLOYEE WHERE empID = ?", new BeanPropertyRowMapper<>(EMPLOYEE.class), newInfo.getEmpID());
    }

    @Override
    public List<ITEM> itemStrSearch(String searchName) {
        String sql = "SELECT * FROM EMPLOYEE WHERE FName LIKE '%?%' ";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class), searchName);
    }

    /*@Override
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount) {
        int isDepletable = depletable ? 1 : 0;
        String sql = "SELECT * FROM BORROW_ITEM WHERE itemType = ?, depID = ?, itemAmount = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class));
    }

    @Override
    public List<BORROW_ITEM> checkBorrowLists() {
        String sql = "SELECT * FROM BORROW_ITEM";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(BORROW_ITEM.class));
    }*/
  
    @Override
    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount){
        List<ITEM> itemList;
        String QueryStr = "SELECT itemName, ITEM.itemAmount - COALESCE(SUM(BORROW.BorrowAmount), 0) AS availableAmount ";
        if (depletable) {
            QueryStr += "FROM Depletable JOIN Borrow ON Depletable.itemID = Borrow.itemID WHERE itemType = 1 ";
        } else {
            QueryStr += "FROM Indepletable JOIN Borrow ON Indepletable.itemID = Borrow.itemID WHERE itemType = 0 ";
        }
        if (amount != null && !amount.isEmpty() && Integer.parseInt(amount) >= 0) {
            QueryStr += "AND (availableAmount >= " + amount + ")";
        }
        itemList = jdbc.query(QueryStr, new BeanPropertyRowMapper<>(ITEM.class));
        return itemList;
    }

    @Override
    public List<EMPBORROWLIST> checkBorrowLists(String empID) {
        String queryStr = "SELECT itemID, BorrowStatus, DATEDIFF(returnDate, CURDATE()) * 35 AS penaltyAmount FROM BORROW_ITEM WHERE empID = ?";
        return jdbc.query(queryStr, BeanPropertyRowMapper.newInstance(EMPBORROWLIST.class), empID);
    }


    @Override
    public List<ITEM> fetchAllItem() {
        String sql = "SELECT * FROM ITEM";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class));
    }

    @Override
    public List<ITEM> fetchSomeItem(int start, int rows) {
        String sql = "SELECT * FROM ITEM ORDER BY itemID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class), start, rows);
    }

    @Override
    public void insertPhoto(EMPPHOTO empPhoto) throws SQLException {
        String base64 = empPhoto.getEmpPhoto();
        byte[] b = Base64.getDecoder().decode(base64);
        Blob im = new javax.sql.rowset.serial.SerialBlob(b);
        String sql = "UPDATE EMPLOYEE\n" +
                "SET empPhoto = ? WHERE empID = ?";
        jdbc.update(sql, b, empPhoto.getEmpID());

    }

    @Override
    public void insertItemPhoto(ITEMPHOTO itemphoto) throws SQLException {
        String base64 = itemphoto.getItemPhoto();
        byte[] b = Base64.getDecoder().decode(base64);
        Blob im = new javax.sql.rowset.serial.SerialBlob(b);
        String sql = "UPDATE ITEM\n" +
                "SET itemPhoto = ? WHERE itemID = ?";
        jdbc.update(sql, b, itemphoto.getItemID());
    }

    @Override
    public void insertIm(String empID) throws SQLException {
        File imageFile = new File("C:/Users/penci/Downloads/download.png");
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = Files.readAllBytes(imageFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        };
        int length = imageBytes.length;
        System.out.println(length);
        Blob im = new javax.sql.rowset.serial.SerialBlob(imageBytes);
        String sql = "UPDATE EMPLOYEE\n" +
                "SET empPhoto = ? WHERE empID = ?";
        jdbc.update(sql, im, empID);
    }

    @Override
    public void editPass(String newPass, String empID) {
        String sql = "UPDATE EMPLOYEE\n" +
                "SET empIDPass = ? WHERE empID = ?";
        jdbc.update(sql, newPass, empID);
    }

    @Override
    public void borrowItem(String empID, String itemID, int amounts) throws Exception {
        String retrieveData = "SELECT * FROM ITEM WHERE itemID = ?";
        String updateAmount = "UPDATE ITEM SET itemAmount = ? WHERE itemID = ?";
        String addBorrow = "INSERT INTO BORROW_ITEM VALUES(?, ?, ?, ?, ?, ?)";
        BORROW_ITEM borrowItem = new BORROW_ITEM(empID, itemID, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(1)), amounts, "Normal");
        System.out.println(itemID);
        ITEM item = jdbc.queryForObject(retrieveData, new BeanPropertyRowMapper<>(ITEM.class), itemID);
        if (item.getItemAmount() >= amounts) {
            jdbc.update(updateAmount, item.getItemAmount() - amounts, itemID);
            jdbc.update(addBorrow, borrowItem.getEmpID(), borrowItem.getItemID(), borrowItem.getBorrowDate(), borrowItem.getReturnDate(), borrowItem.getBorrowAmount(), borrowItem.getBorrowStatus());
        }
        else {
            throw new Exception("Not enough item");
        }
    }

    @Override
    public void checkBorrowStatus() {
        String sql = "UPDATE BORROW_ITEM\n" +
                "SET BorrowStatus = 'Late Returned'\n" +
                "WHERE BorrowStatus = 'Normal' AND DATEDIFF(DAY, GETDATE(), ReturnDate) < 0;";
        jdbc.update(sql);
        sql = "UPDATE BORROW_ITEM\n" +
                "SET BorrowStatus = 'Normal'\n" +
                "WHERE BorrowStatus = 'Late Returned' AND DATEDIFF(DAY, GETDATE(), ReturnDate) >= 0;";
        jdbc.update(sql);
    }

    @Override
    public DEPARTMENT checkDepartment(String deptID) {
        String sql = "SELECT * FROM DEPARTMENT WHERE depID = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(DEPARTMENT.class), deptID);
    }

    @Override
    public ITEM fetchItem(String itemID) {
        String sql = "SELECT * FROM ITEM WHERE itemID = ?";
        return jdbc.queryForObject(sql,new BeanPropertyRowMapper<>(ITEM.class), itemID);
    }

    @Override
    public List<BORROW_ITEM_DATA> fetchBorrowStatus(String empID) {
        String sql = "SELECT BORROW_ITEM.empID, BorrowDate, ReturnDate, BorrowAmount, BorrowStatus, ITEM.* FROM BORROW_ITEM\n" +
                "LEFT JOIN item ON BORROW_ITEM.itemID = item.itemID\n" +
                "WHERE BORROW_ITEM.empID = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(BORROW_ITEM_DATA.class), empID);
    }

    @Override
    public ITEM editItemInfo(ITEM newInfo) {
        String sql = "UPDATE ITEM\n" +
                "SET itemName = ?, " +
                "itemAmount = ?, " +
                "cost = ?, " +
                "purchaseDate = ?, " +
                "itemType = ?, " +
                "depID = ? " +
                "WHERE itemID = ?;";
        jdbc.update(sql, newInfo.getItemName(), newInfo.getItemAmount(), newInfo.getCost(), newInfo.getPurchaseDate(), newInfo.getItemType(), newInfo.getDepID(), newInfo.getItemID());
        return jdbc.queryForObject("SELECT * FROM ITEM WHERE itemID = ?", new BeanPropertyRowMapper<>(ITEM.class), newInfo.getItemID());
    }

    @Override
    public void deleteItem(String itemID) {
        String sql = "DELETE FROM ITEM WHERE itemID = ?;";
        jdbc.update(sql, itemID);
    }

    @Override
    public void deleteEmployee(String empID) {
        String sql = "DELETE FROM EMPLOYEE WHERE empID = ?;";
        jdbc.update(sql, empID);
    }

    @Override
    public List<ITEM> fetchItemByName(String itemName) {
        String sql = "SELECT * FROM ITEM WHERE itemName LIKE CONCAT('%',?,'%');";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class), itemName);
    }

    @Override
    public List<ITEM> fetchItemByID(String itemID) {
        String sql = "SELECT * FROM ITEM WHERE itemID = ?;";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ITEM.class), itemID);
    }

    @Override
    public List<EMPLOYEE> fetchEmpByID(String empID) {
        String sql = "SELECT * FROM EMPLOYEE WHERE empID = ?;";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(EMPLOYEE.class), empID);
    }

    @Override
    public List<EMPLOYEE> fetchEmpByName(String empName) {
        String sql = "SELECT * FROM EMPLOYEE WHERE fname LIKE CONCAT('%',?,'%') or mname LIKE CONCAT('%',?,'%') or lname LIKE CONCAT('%',?,'%');";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(EMPLOYEE.class), empName, empName, empName);
    }
    @Override
    public void addEmployee(EMPLOYEE newEmp) throws SQLException {
        String base64 = newEmp.getEmpPhoto();
        byte[] b = Base64.getDecoder().decode(base64);
        Blob im = new javax.sql.rowset.serial.SerialBlob(b);
        System.out.println(im);
        String sql = "INSERT INTO EMPLOYEE\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbc.update(sql, newEmp.getEmpID(), newEmp.getFname(), newEmp.getMname(), newEmp.getLname(), newEmp.getEmpBDate(), newEmp.getHouseNo(), newEmp.getRoad(), newEmp.getSubDistrict(), newEmp.getProvince(), newEmp.getCountry(), newEmp.getPostcode(), newEmp.getEmpTel(), newEmp.getEmpMail(), newEmp.getEmpRole(), newEmp.getEmpIDPass(), im,newEmp.getDepID(), newEmp.getDistrict());
    }
    @Override
    public void addItem(INDEPLETABLE newItem) throws SQLException {
        String base64 = newItem.getItemPhoto();
        byte[] b = Base64.getDecoder().decode(base64);
        Blob im = new javax.sql.rowset.serial.SerialBlob(b);
        System.out.println(im);
        String sql = "INSERT INTO ITEM\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbc.update(sql, newItem.getItemID(), newItem.getItemName(), newItem.getItemAmount(), newItem.getCost(), newItem.getPurchaseDate(), newItem.getItemType(), newItem.getEmpID(), newItem.getDepID(), im);
        sql = "INSERT INTO INDEPLETABLE VALUES(?, ?)";
        jdbc.update(sql, newItem.getItemID(), newItem.getMaintenanceDate());
    }
    @Override
    public void addItem(DEPLETABLE newItem) throws SQLException {
        String base64 = newItem.getItemPhoto();
        byte[] b = Base64.getDecoder().decode(base64);
        Blob im = new javax.sql.rowset.serial.SerialBlob(b);
        System.out.println(im);
        String sql = "INSERT INTO ITEM\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbc.update(sql, newItem.getItemID(), newItem.getItemName(), newItem.getItemAmount(), newItem.getCost(), newItem.getPurchaseDate(), newItem.getItemType(), newItem.getEmpID(), newItem.getDepID(), im);
        sql = "INSERT INTO DEPLETABLE VALUES(?, ?)";
        jdbc.update(sql, newItem.getItemID(), newItem.getRestockDate());
    }
}
