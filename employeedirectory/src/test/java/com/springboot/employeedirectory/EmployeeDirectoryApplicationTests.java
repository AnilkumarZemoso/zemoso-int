package com.springboot.employeedirectory;

import com.springboot.employeedirectory.dao.EmployeeRepository;
import com.springboot.employeedirectory.entity.Employee;
import com.springboot.employeedirectory.service.EmployeeService;
import com.springboot.employeedirectory.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@ExtendWith({MockitoExtension.class})
@SpringBootTest
class EmployeeDirectoryApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Mock
	private List<Employee> employeeList;
	@Mock
	private Employee employee1;
	@Mock
	private Employee employee2;


	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		employeeService=new EmployeeServiceImpl(this.employeeRepository);
		employee1=new Employee(100,"Anil","M","anilm@gmail.com");
		employee2=new Employee(101,"AK","Mondyagu","akm@gmail.com");

		employeeList=new ArrayList<>();
		employeeList.add(employee1);
		employeeList.add(employee2);

	}

	@Test
	public void getEmployees() {

		Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);

		assertEquals(2,employeeService.findAll().size());
	}

	@Test
	public void getEmployeeById() {

		Mockito.when(employeeRepository.findById(100)).thenReturn(Optional.of(employee1));

		assertEquals("M",employeeService.findById(100).getLastName());
	}

	@Test
	public void saveEmployee() {

		Employee employee3=new Employee("AK","M","akm@Gmail.com");

		employeeRepository.save(employee3);
		Mockito.verify(employeeRepository,times(1)).save(employee3);
	}

	@Test
	public void deleteById() {
		employeeRepository.deleteById(100);
		Mockito.verify(employeeRepository,times(1)).deleteById(100);
	}


}
