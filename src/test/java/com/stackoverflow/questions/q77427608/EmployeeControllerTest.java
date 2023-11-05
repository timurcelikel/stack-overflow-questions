package com.stackoverflow.questions.q77427608;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeControllerTest {

	@Autowired
	EmployeeController employeeController;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testEmployeeAddressPersist() throws JsonProcessingException {

		List<Employee> employees = getEmployees();

		final ResponseEntity<List<Employee>> responseEmployees =
				employeeController.createEmployees(employees);

		List<Employee> savedEmployees = responseEmployees.getBody();

		assertThat(savedEmployees).hasSize(2);
		assertThat(savedEmployees.get(0).getAddresses().get(0).getEmployee()).isNotNull();
	}

	@Test
	void testEmployeeAddressBrokenBidirectionalPersist() throws JsonProcessingException {

		List<Employee> employees = getEmployees();

		final ResponseEntity<List<Employee>> responseEmployees =
				employeeController.createEmployeesBrokenBiDirectionalSync(employees);

		List<Employee> savedEmployees = responseEmployees.getBody();

		assertThat(savedEmployees).hasSize(2);
		assertThat(savedEmployees.get(0).getAddresses().get(0).getEmployee()).isNull();
	}

	private List<Employee> getEmployees() throws JsonProcessingException {

		String employeesJson =
				"""
							[{
								"name": "test-user433242",
								"salary": "94457020",
								"position": "developer-244332",
									"addresses": [
										{        \s
										   "street": "Ne22w st 444331",
											"city": "Ne22w york city 443321",
										   "state": "N22ew york state 43321"
										  \s
										 },
										 {        \s
										   "street": "texas st 211332",
										   "city": "texas city 22332",
										   "state": "texas state 23332"  \s
										 }
								]
							},
							{
								"name": "test-user2133323",
								"salary": "94777030",
								"position": "developer33-21332",
									"addresses": [
										{        \s
										   "street": "New3 st 211332",
										   "city": "New york city 22332",
										   "state": "Ne33w york state 23332"  \s
										 },
													 {        \s
										   "street": "buffaol st 211332",
										   "city": "buffalo city 22332",
										   "state": "buffalo state 23332"  \s
										 }
								]
							}]
						""";

		return Arrays.asList(objectMapper.readValue(employeesJson, Employee[].class));
	}
}
