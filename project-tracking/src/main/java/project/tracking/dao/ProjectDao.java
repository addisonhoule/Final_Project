package project.tracking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tracking.entity.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {

}
