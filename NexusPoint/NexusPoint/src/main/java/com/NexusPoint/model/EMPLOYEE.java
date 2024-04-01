package com.NexusPoint.model;

import java.sql.Date;
import java.sql.Blob;

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

    public String getEmpID() {
        return this.empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getFname() {
        return this.Fname;
    }

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

    public String getMname() {
        return this.Mname;
    }

    public void setMname(String Mname) {
        this.Mname = Mname;
    }

    public String getLname() {
        return this.Lname;
    }

    public void setLname(String Lname) {
        this.Lname = Lname;
    }

    public Date getEmpBDate() {
        return this.empBDate;
    }

    public void setEmpBDate(Date empBDate) {
        this.empBDate = empBDate;
    }

    public String getHouseNo() {
        return this.HouseNo;
    }

    public void setHouseNo(String HouseNo) {
        this.HouseNo = HouseNo;
    }

    public String getRoad() {
        return this.Road;
    }

    public void setRoad(String Road) {
        this.Road = Road;
    }

    public String getSubDistrict() {
        return this.SubDistrict;
    }

    public void setSubDistrict(String SubDistrict) {
        this.SubDistrict = SubDistrict;
    }

    public String getDistrict() {
        return this.District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public String getProvince() {
        return this.Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getCountry() {
        return this.Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public int getPostcode() {
        return this.Postcode;
    }

    public void setPostcode(int Postcode) {
        this.Postcode = Postcode;
    }

    public String getEmpTel() {
        return this.empTel;
    }

    public void setEmpTel(String empTel) {
        this.empTel = empTel;
    }

    public String getEmpMail() {
        return this.empMail;
    }

    public void setEmpMail(String empMail) {
        this.empMail = empMail;
    }

    public String getEmpRole() {
        return this.empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpIDPass() {
        return this.empIDPass;
    }

    public void setEmpIDPass(String empIDPass) {
        this.empIDPass = empIDPass;
    }

    public Blob getEmpPhoto() {
        return this.empPhoto;
    }

    public void setEmpPhoto(Blob empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getDepID() {
        return this.depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

}
