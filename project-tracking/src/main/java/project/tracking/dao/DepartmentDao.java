package project.tracking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tracking.entity.Department;

public interface DepartmentDao extends JpaRepository<Department, Long> {

}
