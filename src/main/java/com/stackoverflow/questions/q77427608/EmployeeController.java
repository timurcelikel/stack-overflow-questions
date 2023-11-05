package com.stackoverflow.questions.q77427608;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeController {

	EmployeeRepository employeeRepository;

	public EmployeeController(final EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@PostMapping("employees")
	public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employee) {
		List<Employee> newUsers = new ArrayList<>();
		for (Employee emp : employee) {
			if (emp.getAddresses() != null) {
				for (Address address : emp.getAddresses()) {
					address.setEmployee(emp);
				}
			}
			Employee newUser = employeeRepository.save(emp);
			newUsers.add(newUser);
		}

		return new ResponseEntity<>(newUsers, HttpStatus.OK);
	}

}
