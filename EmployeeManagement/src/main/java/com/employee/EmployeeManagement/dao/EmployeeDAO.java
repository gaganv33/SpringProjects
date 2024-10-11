package com.employee.EmployeeManagement.dao;

import com.employee.EmployeeManagement.entity.Employee;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    public boolean save(Employee e);
    public boolean findIfEmployeeWithEmailExists(String email);
    public List<Employee> findAll();
    public Optional<Employee> findById(Integer id);
    public boolean deleteById(Integer id);
    public Optional<Employee> updateById(Employee e);
}
