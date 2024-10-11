package com.employee.EmployeeManagement.controller;

import com.employee.EmployeeManagement.entity.Employee;
import com.employee.EmployeeManagement.exception.EmployeeEmailExistsException;
import com.employee.EmployeeManagement.exception.EmployeeNotFoundException;
import com.employee.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/test")
    public String test() {
        return "message: test";
    }

    @PostMapping("/employees")
    public String save(@RequestBody Employee e) {
        boolean isInserted = employeeService.save(e);
        if(!isInserted) {
            throw new EmployeeEmailExistsException(e.getEmail() + " all ready exists that corresponds to another user.");
        }
        return "Employee successfully added.";
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable("employeeId") Integer employeeId) {
        Optional<Employee> result = employeeService.findById(employeeId);
        if(result.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id not found: " + employeeId);
        }
        return result.get();
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteById(@PathVariable("employeeId") Integer employeeId) {
        boolean isRemoved = employeeService.deleteById(employeeId);
        if(!isRemoved) {
            throw new EmployeeNotFoundException("Employee with id not found: " + employeeId);
        }
        return "Employee is removed successfully.";
    }

    @PatchMapping("/employees/{employeeId}")
    public Employee update(@RequestBody Employee employee, @PathVariable("employeeId") Integer employeeId) {
        employee.setId(employeeId);
        Optional<Employee> e = employeeService.updateById(employee);
        if(e.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id not found: " + employeeId);
        }
        return e.get();
    }
}
