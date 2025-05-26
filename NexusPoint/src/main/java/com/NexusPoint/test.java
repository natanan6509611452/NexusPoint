package com.NexusPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;

public class test {
    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String[] args) throws SQLException {
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
        String sql = "INSERT INTO EMPLOYEE(empPhoto) VALUE(?)";
        //jdbc.update(sql, im);
    }
}

