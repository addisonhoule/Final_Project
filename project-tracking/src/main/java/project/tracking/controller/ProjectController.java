package project.tracking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.tracking.controller.model.ProjectData;
import project.tracking.controller.model.ProjectData.DepartmentData;
import project.tracking.controller.model.ProjectData.DepartmentData.EmployeeData;
import project.tracking.service.ProjectService;

@RestController
@RequestMapping("/project_tracking")
@Slf4j
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	
//	POST ************************************************
	
	@PostMapping("/project")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProjectData createProject(@RequestBody ProjectData projectData) {
		log.info("Creating project {}", projectData);
		return projectService.saveProject(projectData);
	}
	
	@PostMapping("/department/{departmentId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData createEmployee(@PathVariable Long departmentId, @RequestBody EmployeeData employeeData) {
		log.info("Adding employee {}.", employeeData);
		return projectService.saveEmployee(departmentId, employeeData);
	}
	
	@PostMapping("/department/{departmentId}/project")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProjectData createProject(@PathVariable Long departmentId, @RequestBody ProjectData projectData) {
		log.info("Adding project {} to department with ID={}.", projectData, departmentId);
		return projectService.saveProject(departmentId, projectData);
	}
	
	@PostMapping("/department/{departmentId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DepartmentData createDepartment(@PathVariable Long departmentId, @RequestBody DepartmentData departmentData) {
		log.info("Adding department {}.", departmentData);
		return projectService.saveDepartment(departmentId, departmentData);
	}
	
	@PostMapping("/department")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DepartmentData createDepartment(@RequestBody DepartmentData departmentData) {
		log.info("Adding department {}.", departmentData);
		return projectService.saveDepartment(departmentData);
	}
	
//	GET ************************************************
	
	@GetMapping("/project")
	public List<ProjectData> retrieveAllProjects() {
		log.info("Retrieving all projects.");
		return projectService.retrieveAllProjects();
	}
	
	@GetMapping("/employee")
	public List<EmployeeData> retrieveAllEmployees() {
		log.info("Retrieving all employees.");
		return projectService.retrieveAllEmployees();
	}
	
	@GetMapping("/department")
	public List<DepartmentData> retrieveAllDepartments() {
		log.info("Retrieving all departments.");
		return projectService.retrieveAllDepartments();
	}
	
	@GetMapping("/department/{departmentId}")
	public DepartmentData retrieveDepartmentById(@PathVariable Long departmentId) {
		log.info("Retrieving department with ID={}", departmentId);
		return projectService.retrieveDepartmentById(departmentId);
	}
	@GetMapping("/project/{projectId}")
	public ProjectData retrieveProjectById(@PathVariable Long projectId) {
		log.info("Retrieving project with ID={}", projectId);
		return projectService.retrieveProjectById(projectId);
	}
	@GetMapping("/department/{departmentId}/employee")
	public List<EmployeeData> retrieveEmployeesByDepartment(@PathVariable Long departmentId) {
		log.info("Retrieving employees from department with ID={}", departmentId);
		return projectService.retrieveEmployeesByDepartment(departmentId);
	}
	@GetMapping("/department/{departmentId}/project")
	public List<ProjectData> retrieveProjectsByDepartment(@PathVariable Long departmentId) {
		log.info("Retrieving projects from department with ID={}", departmentId);
		return projectService.retrieveProjectsByDepartment(departmentId);
	}
	
//	PUT ************************************************
	
	@PutMapping("/project/{projectId}")
	public ProjectData updateProjectData(@PathVariable Long projectId, @RequestBody ProjectData projectData) {
		projectData.setProjectId(projectId);
		log.info("Updating Project {}", projectData);
		return projectService.saveProject(projectData);
	}
	
	@PutMapping("/department/{departmentId}")
	public DepartmentData updateDepartmentData(@PathVariable Long departmentId, @RequestBody DepartmentData departmentData) {
		departmentData.setDepartmentId(departmentId);
		log.info("Updating Department {}", departmentData);
		return projectService.saveDepartment(departmentId, departmentData);
	}
	
	@PutMapping("/employee/{employeeId}")
	public EmployeeData updateEmployeeData(@PathVariable Long employeeId, @RequestBody EmployeeData employeeData) {
		employeeData.setEmployeeId(employeeId);
		log.info("Updating Employee {}", employeeData);
		return projectService.saveEmployeeById(employeeId, employeeData);
	}
	
//	DELETE ************************************************
	
	@DeleteMapping("/project/{projectId}")
	public Map<String, String> deleteProjectById(@PathVariable Long projectId) {
		log.info("Deleting project with ID={}", projectId);
		projectService.deleteProjectById(projectId);
		return Map.of("message", "Deletion of project with ID=" + projectId + " was successful");
	}
	
	@DeleteMapping("/department/{departmentId}")
	public Map<String, String> deleteDepartmentById(@PathVariable Long departmentId) {
		log.info("Deleting project with ID={}", departmentId);
		projectService.deleteDepartmentById(departmentId);
		return Map.of("message", "Deletion of department with ID=" + departmentId + " was successful");
	}
	
	@DeleteMapping("/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(@PathVariable Long employeeId) {
		log.info("Deleting employee with ID={}", employeeId);
		projectService.deleteEmployeeById(employeeId);
		return Map.of("message", "Deletion of employee with ID=" + employeeId + " was successful");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
