package com.NexusPoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.NexusPoint.model.*;
import com.NexusPoint.repository.employeeRepository;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value={"employee"})
public class NexusPointController {
    @Autowired
    private employeeRepository empDao;

    @GetMapping(value = "/fetchEmployee")
    @ResponseBody
    public ResponseEntity<EMPLOYEE> fetchEmployee(@RequestParam String id) {
        try {
            EMPLOYEE data = empDao.fetchEmployee(id.replace("\"", ""));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EMPLOYEE> login(@RequestBody USER user) {
        try {
            EMPLOYEE data = empDao.authenticate(user);
            /*String a = data.getEmpPhoto();
            byte[] byteArray = hexStringToByteArray(a);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            File outputFile = new File("C:/Users/penci/Downloads/beep.jpg");
            BufferedImage image = ImageIO.read(inputStream);
            ImageIO.write(image, "jpg", outputFile);*/
            if (data == null) {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/insert")
    public void insert(@RequestParam String empID) throws SQLException {
        empDao.insertPhoto(empID);
    }

    @GetMapping(value = "/fetchAllItem")
    public ResponseEntity<List<ITEM>> fetchAllItem() throws SQLException {
        try {
            List<ITEM> data = empDao.fetchAllItem();
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/fetchSomeItem")
    public ResponseEntity<List<ITEM>> fetchSomeItem(@RequestParam int start, @RequestParam int rows) throws SQLException {
        try {
            List<ITEM> data = empDao.fetchSomeItem(start, rows);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/borrowItem", method = RequestMethod.POST)
    public HttpStatus borrowItem(@RequestBody ITEM_REQUEST request) {
        try {
            System.out.println(request.getItemAmount() + " " + request.getItemID());
            empDao.borrowItem(request.getEmpID(), request.getItemID(), request.getItemAmount());
            System.out.println("Borrow Successful");
            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping(value = "/checkBorrowStatus")
    public HttpStatus checkBorrowStatus() {
        try {
            empDao.checkBorrowStatus();
            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }


   /* @RequestMapping(value = "/addDrop", method = RequestMethod.POST)
    public void addUser(@RequestBody addDropData addDropData) {
        userDao.saveAddDrop(addDropData);
    }

    @RequestMapping(value = "/dropW", method = RequestMethod.POST)
    public void addUser(@RequestBody DropWData a) {
        userDao.saveDropW(a);
    }
    @RequestMapping(value = "/regCross", method = RequestMethod.POST)
    public void addUser(@RequestBody regCrossData a) {
        userDao.saveRegCross(a);
    }
    @RequestMapping(value = "/dropOut", method = RequestMethod.POST)
    public void addUser(@RequestBody dropOutData a) {
        userDao.saveDropout(a);
    }
    @RequestMapping(value = "/other", method = RequestMethod.POST)
    public void addUser(@RequestBody otherData a) {
        userDao.saveOther(a);
    }



    @GetMapping
    public List<addDropData> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/getAddDropDataById")
    @ResponseBody
    public ResponseEntity<List<addDropData>> getAddDropDataById(@RequestParam String id) {
        try {
            List<addDropData> data = userDao.getAddDropDataById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDropWDataById")
    @ResponseBody
    public ResponseEntity<List<DropWData>> getDropWDataById(@RequestParam String id) {
        try {
            List<DropWData> data = userDao.getDropWDataById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDropOutDataById")
    @ResponseBody
    public ResponseEntity<List<dropOutData>> getDropOutDataById(@RequestParam String id) {
        try {
            List<dropOutData> data = userDao.getDropOutDataById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOtherDataById")
    @ResponseBody
    public ResponseEntity<List<otherData>> getOtherDataById(@RequestParam String id) {
        try {
            List<otherData> data = userDao.getOtherDataById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRegCrossDataById")
    @ResponseBody
    public ResponseEntity<List<regCrossData>> getRegCrossDataById(@RequestParam String id) {
        try {
            List<regCrossData> data = userDao.getRegCrossDataById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/
    
}