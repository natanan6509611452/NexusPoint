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
    @GetMapping(value = "/fetchAllEmployee")
    @ResponseBody
    public ResponseEntity<List<EMPLOYEE>> fetchAllEmployee() {
        try {
            List<EMPLOYEE> data = empDao.fetchAllEmployee();
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

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(@RequestBody EMPPHOTO empPhoto) throws SQLException {
        empDao.insertPhoto(empPhoto);
    }

    @GetMapping(value = "/insertIm")
    public void insertIm(@RequestParam String empID) throws SQLException {
        empDao.insertIm(empID);
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

    @GetMapping(value = "/fetchItem")
    public ResponseEntity<ITEM> fetchItem(@RequestParam String itemID) throws SQLException {
        try {
            ITEM data = empDao.fetchItem(itemID);
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

    @GetMapping(value = "/checkDepartment")
    public ResponseEntity<DEPARTMENT> checkDepartment(@RequestParam String deptID) {
        try {
            DEPARTMENT data = empDao.checkDepartment(deptID);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/fetchBorrowStatus")
    public ResponseEntity<List<BORROW_ITEM_DATA>> fetchBorrowStatus(@RequestParam String empID) {
        try {
            List<BORROW_ITEM_DATA> data = empDao.fetchBorrowStatus(empID);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/editEmployeeInfo", method = RequestMethod.POST)
    public ResponseEntity<EMPLOYEE> editEmployeeInfo(@RequestBody EMPLOYEE emp) {
        try {
            EMPLOYEE newEmpData = empDao.editEmployeeInfo(emp);
            return new ResponseEntity<>(newEmpData, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/editItemInfo", method = RequestMethod.POST)
    public ResponseEntity<ITEM> editEmployeeInfo(@RequestBody ITEM item) {
        try {
            ITEM newEmpData = empDao.editItemInfo(item);
            return new ResponseEntity<>(newEmpData, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/insertItemPhoto", method = RequestMethod.POST)
    public void insertItemPhoto(@RequestBody ITEMPHOTO itemphoto) throws SQLException {
        empDao.insertItemPhoto(itemphoto);
    }
    @GetMapping(value = "/deleteItem")
    public void deleteItem(@RequestParam String itemID) throws SQLException {
        empDao.deleteItem(itemID);
    }

    @GetMapping(value = "/deleteEmployee")
    public void deleteEmployee(@RequestParam String empID) throws SQLException {
        empDao.deleteEmployee(empID);
    }

    @GetMapping(value = "/fetchEmpByID")
    public ResponseEntity<List<EMPLOYEE>> fetchEmpByID(@RequestParam String empID) throws SQLException {
        try {
            List<EMPLOYEE> a = empDao.fetchEmpByID(empID);
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/fetchEmpByName")
    public ResponseEntity<List<EMPLOYEE>> fetchEmpByName(@RequestParam String empName) throws SQLException {
        try {
            List<EMPLOYEE> a = empDao.fetchEmpByName(empName);
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/fetchItemByID")
    public ResponseEntity<List<ITEM>> fetchItemByID(@RequestParam String itemID) throws SQLException {
        try {
            List<ITEM> a = empDao.fetchItemByID(itemID);
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/fetchItemByName")
    public ResponseEntity<List<ITEM>> fetchItemByName(@RequestParam String itemName) throws SQLException {
        try {
            List<ITEM> a = empDao.fetchItemByName(itemName);
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public HttpStatus addEmployee(@RequestBody EMPLOYEE newEmp) throws SQLException {
        try {
            empDao.addEmployee(newEmp);
            return HttpStatus.OK ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }
    @RequestMapping(value = "/addIndepItem", method = RequestMethod.POST)
    public HttpStatus addItem(@RequestBody INDEPLETABLE newItem) throws SQLException {
        try {
            empDao.addItem(newItem);
            return HttpStatus.OK ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }
    @RequestMapping(value = "/addDepItem", method = RequestMethod.POST)
    public HttpStatus addItem(@RequestBody DEPLETABLE newItem) throws SQLException {
        try {
            empDao.addItem(newItem);
            return HttpStatus.OK ;
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