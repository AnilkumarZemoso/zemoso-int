package com.springboot.employeedirectory.service;

import java.util.List;
import java.util.Optional;

import com.springboot.employeedirectory.EmployeeDirectoryApplication;
import com.springboot.employeedirectory.dao.EmployeeRepository;
import com.springboot.employeedirectory.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger log = LoggerFactory.getLogger(EmployeeDirectoryApplication.class);

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        log.debug("Requesting all Employee data");
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee findById(int theId) {

        log.debug("Requested Employee with ID:"+theId);

        Optional<Employee> result = employeeRepository.findById(theId);

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
            log.debug("Result obtained:"+theEmployee);
        }
        else {
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theEmployee;
    }

    @Override
    public void save(Employee theEmployee) {
        log.debug("Saving a Employee");
        employeeRepository.save(theEmployee);
        log.debug("Saved Employee:"+theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        log.debug("Deleting a Employee with Id:"+theId);
        employeeRepository.deleteById(theId);
        log.debug("Employee Deleted:"+theId);
    }

}
