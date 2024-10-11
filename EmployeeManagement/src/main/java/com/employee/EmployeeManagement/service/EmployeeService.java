package com.employee.EmployeeManagement.service;

import com.employee.EmployeeManagement.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public boolean save(Employee e);
    public List<Employee> findAll();
    public Optional<Employee> findById(Integer id);
    public boolean deleteById(Integer id);
    public Optional<Employee> updateById(Employee e);
}
