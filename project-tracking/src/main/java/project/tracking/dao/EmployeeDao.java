package project.tracking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tracking.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
