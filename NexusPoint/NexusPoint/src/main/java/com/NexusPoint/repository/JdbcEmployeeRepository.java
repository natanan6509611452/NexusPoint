package com.NexusPoint.repository;

import com.NexusPoint.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
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
        File imageFile = new File("C:/Users/penci/Downloads/download.jpg");
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
}
