package org.lab.milestone.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.milestone.model.MilestoneEntity;
import org.lab.milestone.persistence.MilestoneSpecifications;
import org.lab.milestone.persistence.repository.MilestoneRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneSearchService {

    private final MilestoneRepository milestoneRepository;

    public Optional<MilestoneEntity> findOne(MilestoneSearchParams searchParams) {
        return milestoneRepository.findOne(searchParams.buildSpec());
    }

    @Builder
    @EqualsAndHashCode
    public static class MilestoneSearchParams {
        private String name;

        Specification<MilestoneEntity> buildSpec() {
            var spec = MilestoneSpecifications.emptySpec();

            if (name != null) {
                spec = spec.and(MilestoneSpecifications.nameEquals(name));
            }
            return spec;
        }
    }
}
