package org.lab.projectmember.persistence.repository;

import org.lab.projectmember.model.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long>, JpaSpecificationExecutor<ProjectMemberEntity> {
    List<ProjectMemberEntity> findAllByUser_Id(Long id);

}
