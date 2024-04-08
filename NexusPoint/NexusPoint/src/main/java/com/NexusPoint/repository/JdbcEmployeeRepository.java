package com.NexusPoint.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.NexusPoint.model.*;

import java.util.List;

@Repository
public class JdbcEmployeeRepository implements employeeInterface{
    @AutoWired
    public EMPLOYEE showInfo(){
    }

    public void editUserInfo(EMPLOYEE newInfo){

    }

    public List<ITEM> itemStrSearch(String searchName){

    }

    public List<ITEM> itemFilter(Boolean depletable, String Dep, String amount){

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
