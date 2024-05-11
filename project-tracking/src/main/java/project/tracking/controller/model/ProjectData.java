package project.tracking.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.tracking.entity.Department;
import project.tracking.entity.Employee;
import project.tracking.entity.Project;

@Data
@NoArgsConstructor
public class ProjectData {
	private Long projectId;
	private String name;
	private String estimatedDuration;
	private String actualDuration;
	private String notes;

	private Set<String> departments = new HashSet<>();
	
	public ProjectData(Project project) {
		projectId = project.getProjectId();
		name = project.getName();
		estimatedDuration = project.getEstimatedDuration();
		actualDuration = project.getActualDuration();
		notes = project.getNotes();
		
		for (Department department : project.getDepartments()) {
			departments.add(new String(department.getName()));
		}
	}

	@Data
	@NoArgsConstructor
	public static class DepartmentData {
		private Long departmentId;
		private String name;
		private String departmentEmail;
		private Set<EmployeeData> employees = new HashSet<>();
		private Set<ProjectData> projects = new HashSet<>();

		public DepartmentData(Department department) {
			departmentId = department.getDepartmentId();
			name = department.getName();
			departmentEmail = department.getDepartmentEmail();

			for (Employee employee : department.getEmployees()) {
				employees.add(new EmployeeData(employee));
			}

			for (Project project : department.getProjects()) {
				projects.add(new ProjectData(project));
			}
		}

		@Data
		@NoArgsConstructor
		public static class EmployeeData {
			private Long employeeId;
			private String name;
			private String position;
			private String phoneNumber;
			private String departmentName;

			public EmployeeData(Employee employee) {
				employeeId = employee.getEmployeeId();
				name = employee.getName();
				position = employee.getPosition();
				phoneNumber = employee.getPhoneNumber();
				departmentName = employee.getDepartment().getName();
			}

		}

	}

}
