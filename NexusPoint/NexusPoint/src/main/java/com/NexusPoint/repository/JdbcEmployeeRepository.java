package com.NexusPoint.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.NexusPoint.model.*;

import java.util.List;

import javax.management.Query;

import java.util.ArrayList;


@Repository
public class JdbcEmployeeRepository implements employeeInterface{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EMPLOYEE showInfo(){
        Connection conn = getDatabaseConnection();
        EMPLOYEE employeeInfo = new EMPLOYEE();
        String empID = this.getEmpID();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM EMPLOYEE WHERE empID = ?");
        statement.setString(1, empID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            employeeInfo.setEmpID(resultSet.getString("empID"));
            employeeInfo.setFname(resultSet.getString("Fname"));
            employeeInfo.setMname(resultSet.getString("Mname"));
            employeeInfo.setLname(resultSet.getString("Lname"));
            employeeInfo.setEmpBDate(resultSet.getDate("empBDate"));
            employeeInfo.setHouseNo(resultSet.getString("HouseNo"));
            employeeInfo.setRoad(resultSet.getString("Road"));
            employeeInfo.setSubDistrict(resultSet.getString("SubDistrict"));
            employeeInfo.setDistrict(resultSet.getString("District"));
            employeeInfo.setProvince(resultSet.getString("Province"));
            employeeInfo.setCountry(resultSet.getString("Country"));
            employeeInfo.setPostcode(resultSet.getInt("Postcode"));
            employeeInfo.setEmpTel(resultSet.getString("empTel"));
            employeeInfo.setEmpMail(resultSet.getString("empMail"));
            employeeInfo.setEmpRole(resultSet.getString("empRole"));
            employeeInfo.setDepID(resultSet.getString("depID"));
        }
        console.log("Employee ID: " + empID);
        console.log("First Name: " + employeeInfo.getFname());
        console.log("Middle Name: " + employeeInfo.getMname());
        console.log("Last Name: " + employeeInfo.getLname());
        console.log("Birth Date: " + employeeInfo.getEmpBDate());
        console.log("Address: " + employeeInfo.getHouseNo() + " " + employeeInfo.getRoad() + ", " + employeeInfo.getSubDistrict() + ", " + employeeInfo.getDistrict() + ", " + employeeInfo.getProvince() + ", " + employeeInfo.getCountry() + " " + employeeInfo.getPostcode());
        console.log("Phone NO.: " + employeeInfo.getEmpTel());
        console.log("Email: " + employeeInfo.getEmpMail());
        console.log("Role: " + employeeInfo.getEmpRole());
        console.log("Department ID: " + employeeInfo.getDepID());

        return employeeInfo;
    }

    public void editUserInfo(EMPLOYEE newInfo){
        
    }

    public List<ITEM> itemStrSearch(String searchName){

    }

    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount){
        List<ITEM> itemList = new ArrayList<>();
        String QueryStr = "SELECT itemName, ITEM.itemAmount - COALESCE(SUM(BORROW.BorrowAmount), 0) AS availableAmount ";
        if (depletable) {
            QueryStr += "FROM Depletable JOIN Borrow ON Depletable.itemID = Borrow.itemID WHERE itemType = 1 ";
        } else {
            QueryStr += "FROM Indepletable JOIN Borrow ON Indepletable.itemID = Borrow.itemID WHERE itemType = 0 ";
        }
        if (amount != null && !amount.isEmpty() && Integer.parseInt(amount) >= 0) {
            QueryStr += "AND (availableAmount >= " + amount + ")";
        }
        itemList = jdbcTemplate.query(QueryStr, new BeanPropertyRowMapper<>(ITEM.class));
        return itemList;
    }

    public List<empBorrowList> checkBorrowLists(String empID) {
        String queryStr = "SELECT itemID, BorrowStatus, DATEDIFF(returnDate, CURDATE()) * 35 AS penaltyAmount FROM BORROW_ITEM WHERE empID = ?";
        return jdbcTemplate.query(queryStr, BeanPropertyRowMapper.newInstance(empBorrowList.class), empID);
    }

    public List<ITEM> showAllItem(){

    }

    public String login(String empID){

    }

    public void logout(){
    }
}
