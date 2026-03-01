package com.hdfc.aerospikeintegration.controller;

import com.aerospike.client.Record;
import com.hdfc.aerospikeintegration.models.Employee;
import com.hdfc.aerospikeintegration.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    // CREATE
    @PostMapping("/test")
    public String create(@RequestBody Employee emp) {
        logger.info("request received for save data {}",emp.toString());
        service.save(emp);
        logger.info("data saved Successfully");
        return "Employee Saved Successfully";
    }

    // READ
    @GetMapping("/{id}")
    public Record get(@PathVariable String id) {
        logger.info("request received for get data for id: {}",id);
        Record emp=service.getById(id);
        logger.info("Data retrieved from Aerospike Db : {}",emp.toString());
        return emp;
    }

    // UPDATE
    @PutMapping
    public String update(@RequestBody Employee emp) {
        service.update(emp);
        return "Employee Updated Successfully";
    }

    @PatchMapping("/{id}/flag")
    public String updateActive(@PathVariable String id,
                               @RequestParam boolean flag) {

        service.updateActiveFlag(id, flag);
        return " flag updated only";
    }
    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Employee Deleted Successfully";
    }
}