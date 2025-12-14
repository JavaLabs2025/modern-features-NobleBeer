package org.lab.bugreport.persistence.repository;

import org.lab.bugreport.model.BugReportEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugReportRepository extends JpaRepository<BugReportEntity, Long>, JpaSpecificationExecutor<BugReportEntity> {

    default List<BugReportEntity> findAll(Specification<BugReportEntity> searchSpecification, int limit) {
        return findBy(searchSpecification, q -> q.limit(limit).all());
    }

    default Optional<BugReportEntity> findFirst(Specification<BugReportEntity> searchSpecification, Sort sort) {
        return findBy(searchSpecification, q -> q.sortBy(sort).first());
    }
}
