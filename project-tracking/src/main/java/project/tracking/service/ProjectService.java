package project.tracking.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.tracking.controller.model.ProjectData;
import project.tracking.controller.model.ProjectData.DepartmentData;
import project.tracking.controller.model.ProjectData.DepartmentData.EmployeeData;
import project.tracking.dao.DepartmentDao;
import project.tracking.dao.EmployeeDao;
import project.tracking.dao.ProjectDao;
import project.tracking.entity.Department;
import project.tracking.entity.Employee;
import project.tracking.entity.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;

//	SAVE ************************************************

	@Transactional(readOnly = false)
	public ProjectData saveProject(ProjectData projectData) {
		Project project = findOrCreateProject(projectData.getProjectId());
		copyProjectFields(project, projectData);

		Project dbProject = projectDao.save(project);
		return new ProjectData(dbProject);
	}

	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long departmentId, EmployeeData employeeData) {
		Department department = findDepartmentById(departmentId);
		Employee employee = findOrCreateEmployee(departmentId, employeeData.getEmployeeId());
		copyEmployeeFields(employee, employeeData);

		employee.setDepartment(department);
		department.getEmployees().add(employee);

		Employee dbEmp = employeeDao.save(employee);
		return new EmployeeData(dbEmp);
	}

	public EmployeeData saveEmployeeById(Long employeeId, EmployeeData employeeData) {
		Employee employee = findOrCreateEmployee(employeeData.getEmployeeId());
		copyEmployeeFields(employee, employeeData);

		Employee dbEmp = employeeDao.save(employee);
		return new EmployeeData(dbEmp);
	}

	@Transactional(readOnly = false)
	public ProjectData saveProject(Long departmentId, ProjectData projectData) {
		Department department = findDepartmentById(departmentId);
		Project project = findOrCreateProject(projectData.getProjectId());
		copyProjectFields(project, projectData);

		project.getDepartments().add(department);
		department.getProjects().add(project);

		Project dbProject = projectDao.save(project);
		return new ProjectData(dbProject);
	}

	@Transactional(readOnly = false)
	public DepartmentData saveDepartment(Long departmentId, DepartmentData departmentData) {
		Department department = findOrCreateDepartment(departmentData.getDepartmentId());
		copyDepartmentFields(department, departmentData);

		Department dbDept = departmentDao.save(department);
		return new DepartmentData(dbDept);
	}

	@Transactional(readOnly = false)
	public DepartmentData saveDepartment(DepartmentData departmentData) {
		Department department = findOrCreateDepartment(departmentData.getDepartmentId());
		copyDepartmentFields(department, departmentData);

		Department dbDept = departmentDao.save(department);
		return new DepartmentData(dbDept);
	}

//	COPY ************************************************

	private void copyProjectFields(Project project, ProjectData projectData) {
		project.setProjectId(projectData.getProjectId());
		project.setName(projectData.getName());
		project.setEstimatedDuration(projectData.getEstimatedDuration());
		project.setActualDuration(projectData.getActualDuration());
		project.setNotes(projectData.getNotes());
	}

	private void copyDepartmentFields(Department department, DepartmentData departmentData) {
		department.setDepartmentId(departmentData.getDepartmentId());
		department.setDepartmentEmail(departmentData.getDepartmentEmail());
		department.setName(departmentData.getName());
	}

	private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
		employee.setEmployeeId(employeeData.getEmployeeId());
		employee.setName(employeeData.getName());
		employee.setPosition(employeeData.getPosition());
		employee.setPhoneNumber(employeeData.getPhoneNumber());
	}

//	FIND OR CREATE ************************************************

	private Project findOrCreateProject(Long projectId) {
		Project project;

		if (Objects.isNull(projectId)) {
			project = new Project();
		} else {
			project = findProjectById(projectId);
		}
		return project;
	}

	private Department findOrCreateDepartment(Long departmentId) {
		Department department;

		if (Objects.isNull(departmentId)) {
			department = new Department();
		} else {
			department = findDepartmentById(departmentId);
		}
		return department;
	}

	private Employee findOrCreateEmployee(Long departmentId, Long employeeId) {
		Employee employee;

		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(departmentId, employeeId);
		}
		return employee;
	}

	private Employee findOrCreateEmployee(Long employeeId) {
		Employee employee;

		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(employeeId);
		}
		return employee;
	}

//	FIND BY ID ************************************************

	private Project findProjectById(Long projectId) {
		return projectDao.findById(projectId)
				.orElseThrow(() -> new NoSuchElementException("Project with ID=" + projectId + " does not exist."));
	}

	private Department findDepartmentById(Long departmentId) {
		return departmentDao.findById(departmentId).orElseThrow(
				() -> new NoSuchElementException("Department with ID=" + departmentId + " does not exist."));
	}

	private Employee findEmployeeById(Long departmentId, Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));
	}

//	RETRIEVE ************************************************

	public List<ProjectData> retrieveAllProjects() {
		List<Project> projects = projectDao.findAll();
		List<ProjectData> result = new LinkedList<>();

		for (Project project : projects) {
			ProjectData pd = new ProjectData(project);

			pd.getDepartments().clear();

			result.add(pd);
		}

		return result;
	}

	@Transactional(readOnly = true)
	public List<EmployeeData> retrieveAllEmployees() {
		List<Employee> employees = employeeDao.findAll();
		List<EmployeeData> result = new LinkedList<>();

		for (Employee employee : employees) {
			EmployeeData ed = new EmployeeData(employee);

			result.add(ed);

//			result.add(new EmployeeData(employee));
		}

		return result;
//		
//		
//			//@formatter:off
//			return employeeDao.findAll().stream().map(EmployeeData::new).toList();
//				//@formatter:on

	}

	@Transactional(readOnly = true)
	public List<DepartmentData> retrieveAllDepartments() {
		List<Department> departments = departmentDao.findAll();
		List<DepartmentData> result = new LinkedList<>();

		for (Department department : departments) {
			DepartmentData dd = new DepartmentData(department);

			dd.getEmployees().clear();

			result.add(dd);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public DepartmentData retrieveDepartmentById(Long departmentId) {
		return new DepartmentData(findDepartmentById(departmentId));
	}

	@Transactional(readOnly = true)
	public ProjectData retrieveProjectById(Long projectId) {
		return new ProjectData(findProjectById(projectId));
	}

	@Transactional(readOnly = true)
	public List<EmployeeData> retrieveEmployeesByDepartment(Long departmentId) {
		List<Employee> employees = employeeDao.findAll();
		List<EmployeeData> result = new LinkedList<>();

		for (Employee employee : employees) {
			EmployeeData ed = new EmployeeData(employee);
			if (employee.getDepartment().getDepartmentId() == departmentId) {
				result.add(ed);
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<ProjectData> retrieveProjectsByDepartment(Long departmentId) {
		List<Project> projects = projectDao.findAll();
		List<ProjectData> result = new LinkedList<>();
		List<Department> departments = departmentDao.findAll();

		for (Project project : projects) {
			ProjectData pd = new ProjectData(project);
			for (Department department : departments) {
				if (department.getDepartmentId() == departmentId) {
					result.add(pd);
				}
			}
		}

		return result;
	}

//	DELETE ************************************************

	public void deleteProjectById(Long projectId) {
		Project project = findProjectById(projectId);
		projectDao.delete(project);
	}

	public void deleteDepartmentById(Long departmentId) {
		Department department = findDepartmentById(departmentId);
		departmentDao.delete(department);
	}

	public void deleteEmployeeById(Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		employeeDao.delete(employee);
	}

}
