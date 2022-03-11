package com.springboot.employeedirectory.service;

import com.springboot.employeedirectory.entity.Employee;

import java.util.List;


public interface EmployeeService {

    public List<Employee> findAll();

    public Employee findById(int theId);

    public void save(Employee theEmployee);

    public void deleteById(int theId);

}
