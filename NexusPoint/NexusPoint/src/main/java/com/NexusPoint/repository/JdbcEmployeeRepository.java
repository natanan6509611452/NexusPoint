package com.NexusPoint.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.NexusPoint.model.*;

import java.util.List;
import java.util.ArrayList;


@Repository
public class JdbcEmployeeRepository implements employeeInterface{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EMPLOYEE showInfo(){
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

    public List<empBorrowList> checkBorrowLists(){

    }

    public List<ITEM> showAllItem(){

    }

    public String login(String empID){

    }

    public void logout(){
    }
}
