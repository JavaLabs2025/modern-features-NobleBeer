package org.lab.project.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.lab.infrastructure.persistence.CommonSpecifications;
import org.lab.project.model.ProjectEntity;
import org.springframework.data.jpa.domain.Specification;

import static org.lab.infrastructure.persistence.CommonSpecifications.fieldEquals;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectSpecifications {

    public static Specification<ProjectEntity> emptySpec() {
        return CommonSpecifications.emptySpec();
    }

    public static Specification<ProjectEntity> nameEquals(String value) {
        return fieldEquals("name", value);
    }
}
