package com.employee.EmployeeManagement.service;

import com.employee.EmployeeManagement.dao.EmployeeDAO;
import com.employee.EmployeeManagement.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    @Transactional
    public boolean save(Employee e) {
        return employeeDAO.save(e);
    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    @Transactional
    public Optional<Employee> findById(Integer id) {
        return employeeDAO.findById(id);
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        return employeeDAO.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Employee> updateById(Employee e) {
        return employeeDAO.updateById(e);
    }
}
