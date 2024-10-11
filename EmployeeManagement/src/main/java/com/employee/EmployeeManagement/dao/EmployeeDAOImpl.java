package com.employee.EmployeeManagement.dao;

import com.employee.EmployeeManagement.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private final EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean save(Employee e) {
        boolean isPresent = findIfEmployeeWithEmailExists(e.getEmail());
        if(isPresent) {
             return false;
        }
        entityManager.persist(e);
        return true;
    }

    @Override
    public boolean findIfEmployeeWithEmailExists(String email) {
        TypedQuery<Employee> query = entityManager.createQuery(
                "from Employee where email=:email", Employee.class
        );
        query.setParameter("email", email);
        List<Employee> list = query.getResultList();
        return (!list.isEmpty());
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query = entityManager.createQuery(
                "select e from Employee e", Employee.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        return Optional.ofNullable(employee);
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<Employee> result = findById(id);
        if(result.isEmpty()) {
            return false;
        }
        entityManager.remove(result.get());
        return true;
    }

    @Override
    public Optional<Employee> updateById(Employee e) {
        Optional<Employee> employee = findById(e.getId());
        if(employee.isEmpty()) {
            return employee;
        }
        Employee dbEmployee = employee.get();
        if(e.getFirstName() != null) {
            dbEmployee.setFirstName(e.getFirstName());
        }
        if(e.getLastName() != null) {
            dbEmployee.setLastName(e.getLastName());
        }
        if(e.getEmail() != null) {
            dbEmployee.setEmail(e.getEmail());
        }
        if(e.getYearsOfExperience() != null) {
            dbEmployee.setYearsOfExperience(e.getYearsOfExperience());
        }
        entityManager.merge(dbEmployee);
        return Optional.of(dbEmployee);
    }
}
