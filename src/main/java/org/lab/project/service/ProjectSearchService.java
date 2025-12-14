package org.lab.project.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.project.model.ProjectEntity;
import org.lab.project.persistence.ProjectSpecifications;
import org.lab.project.persistence.repository.ProjectRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectSearchService {

    private final ProjectRepository projectRepository;

    public Optional<ProjectEntity> findOne(ProjectSearchParams searchParams) {
        return projectRepository.findOne(searchParams.buildSpec());
    }

    @Builder
    @EqualsAndHashCode
    public static class ProjectSearchParams {
        private String name;

        Specification<ProjectEntity> buildSpec() {
            var spec = ProjectSpecifications.emptySpec();

            if (name != null) {
                spec = spec.and(ProjectSpecifications.nameEquals(name));
            }
            return spec;
        }
    }
}
