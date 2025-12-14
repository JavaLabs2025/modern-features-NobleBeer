package org.lab.milestone.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.lab.infrastructure.persistence.CommonSpecifications;
import org.lab.milestone.model.MilestoneEntity;
import org.springframework.data.jpa.domain.Specification;

import static org.lab.infrastructure.persistence.CommonSpecifications.fieldEquals;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MilestoneSpecifications {

    public static Specification<MilestoneEntity> emptySpec() {
        return CommonSpecifications.emptySpec();
    }

    public static Specification<MilestoneEntity> nameEquals(String value) {
        return fieldEquals("name", value);
    }

}
