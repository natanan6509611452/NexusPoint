package com.NexusPoint.model;

import java.sql.Date;
import java.sql.Blob;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class EMPLOYEE {
    private String empID;
    private String Fname;
    private String Mname;
    private String Lname;
    private Date empBDate;
    private String HouseNo;
    private String Road;
    private String SubDistrict;
    private String District;
    private String Province;
    private String Country;
    private int Postcode;
    private String empTel;
    private String empMail;
    private String empRole;
    private String empIDPass;
    private Blob empPhoto;
    private String depID; // FK

   
}
