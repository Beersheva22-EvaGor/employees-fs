package employees.spring.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import employees.spring.model.Employee;
import employees.spring.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin
public class EmployeeController {
	private final EmployeeService service;
	
	@PostMapping
	public Employee addEmployee(@RequestBody @Valid Employee employee) {
		Long id = employee.getId();
		if (id != null) {
			log.warn("Employee contains not null id from client", id);
			employee.setId(null);
		}
		Employee res = service.addEmployee(employee);
		log.debug("Employee with id {} was added", employee.getId());
		return res;
	}

	@GetMapping
	public List<Employee> getEmployees() {
		List<Employee> empls = service.getEmployees();
		log.debug("All employees are received");
		return empls;
	}


	@DeleteMapping("{id}")
	public void deleteEmployee(@PathVariable Long id) {
			service.deleteEmployee(id);
			log.debug("Employee with id {} has been removed", id);		
	}

	@PutMapping("{id}")
	public Employee updateEmployee(@PathVariable int id, @RequestBody @Valid Employee empl) {
		if (empl.getId() != id) {
			throw new IllegalArgumentException("ID doesn't exist");
		}
		Employee emplUpdated = service.updateEmployee(empl);
		log.debug("Employee with id {} was updated", id);
		return emplUpdated;
	}

}
