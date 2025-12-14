package org.lab.bugreport.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.bugreport.model.BugReportEntity;
import org.lab.bugreport.persistence.BugReportSpecifications;
import org.lab.bugreport.persistence.repository.BugReportRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BugReportSearchService {

    private final BugReportRepository bugReportRepository;

    public List<BugReportEntity> findAll(BugReportSearchParams searchParams) {
        return bugReportRepository.findAll(searchParams.buildSpec());
    }

    public Optional<BugReportEntity> findOne(BugReportSearchParams searchParams) {
        return bugReportRepository.findOne(searchParams.buildSpec());
    }

    @Builder
    @EqualsAndHashCode
    public static class BugReportSearchParams {
        private Long id;
        private String assignedUsername;

        Specification<BugReportEntity> buildSpec() {
            var spec = BugReportSpecifications.emptySpec();

            if (id != null) {
                spec = spec.and(BugReportSpecifications.idEquals(id));
            }
            if (assignedUsername != null) {
                spec = spec.and(BugReportSpecifications.assignedUsernameEquals(assignedUsername));
            }
            return spec;
        }
    }
}
