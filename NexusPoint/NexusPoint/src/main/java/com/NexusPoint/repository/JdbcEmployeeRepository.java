package com.NexusPoint.repository;

import com.NexusPoint.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public void editUserInfo(EMPLOYEE newInfo) {
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
                "empRole = ?, " +
                "empIDPass = ?, " +
                "empPhoto = ?, " +
                "WHERE empID = ?;";
        jdbc.update(sql, newInfo.getFname(), newInfo.getMname(), newInfo.getLname(),
                newInfo.getEmpBDate(), newInfo.getHouseNo(), newInfo.getRoad(), newInfo.getSubDistrict(),
                newInfo.getDistrict(), newInfo.getProvince(), newInfo.getCountry(), newInfo.getPostcode(),
                newInfo.getEmpTel(), newInfo.getEmpMail(), newInfo.getEmpRole(), newInfo.getEmpIDPass(), newInfo.getEmpPhoto());
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
    public void insertPhoto(String empID) throws SQLException {
        File imageFile = new File("C:/Users/penci/Downloads/keyboard.jpg");
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = Files.readAllBytes(imageFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        };
        int length = imageBytes.length;
        System.out.println(length);
        Blob im = new javax.sql.rowset.serial.SerialBlob(imageBytes);
        String sql = "UPDATE ITEM\n" +
                "SET itemPhoto = ? WHERE itemID = ?";
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
    public List<BORROW_ITEM_DATA> fetchBorrowStatus(String empID) {
        String sql = "SELECT BORROW_ITEM.empID, BorrowDate, ReturnDate, BorrowAmount, BorrowStatus, ITEM.* FROM BORROW_ITEM\n" +
                "LEFT JOIN item ON BORROW_ITEM.itemID = item.itemID\n" +
                "WHERE BORROW_ITEM.empID = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(BORROW_ITEM_DATA.class), empID);
    }


}
