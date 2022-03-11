package com.springboot.employeedirectory.controller;

import java.util.List;
import java.util.stream.Collectors;


import com.springboot.employeedirectory.EmployeeDirectoryApplication;
import com.springboot.employeedirectory.dto.EmployeeDto;
import com.springboot.employeedirectory.entity.Employee;
import com.springboot.employeedirectory.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private ModelMapper modelMapper;

    private EmployeeService employeeService;

    Logger log = LoggerFactory.getLogger(EmployeeDirectoryApplication.class);

    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        List<EmployeeDto> theEmployees= employeeService.findAll()
                                            .stream()
                                            .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                                            .collect(Collectors.toList());
        theModel.addAttribute("employees", theEmployees);

        return "list-employees";
    }

    @GetMapping("/showFormToAdd")
    public String showFormToAdd(Model theModel) {

        EmployeeDto theEmployee = new EmployeeDto();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormToUpdate")
    public String showFormToUpdate(@RequestParam("employeeId") int theId,
                                    Model theModel) {

        Employee theEmployee = employeeService.findById(theId);

        EmployeeDto employeeDto= convertToDto(theEmployee);

        theModel.addAttribute("employee", employeeDto);

        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") @Valid EmployeeDto employeeDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.debug("Has errors from Validation");
            return "employees/employee-form";
        }
        else {
            Employee theEmployee = convertToEntity(employeeDto);
            employeeService.save(theEmployee);

            return "redirect:/employees/list";
        }
    }

    @GetMapping("/error")
    public String errorFromSave() {
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {

        employeeService.deleteById(theId);

        return "redirect:/employees/list";

    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
}