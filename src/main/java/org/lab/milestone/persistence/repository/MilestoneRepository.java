package org.lab.milestone.persistence.repository;

import org.lab.milestone.model.MilestoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MilestoneRepository extends JpaRepository<MilestoneEntity, Long>, JpaSpecificationExecutor<MilestoneEntity> {
}
