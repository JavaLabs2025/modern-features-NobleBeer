package org.lab.bugreport.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.lab.bugreport.model.BugReportEntity;
import org.lab.infrastructure.persistence.CommonSpecifications;
import org.springframework.data.jpa.domain.Specification;

import static org.lab.infrastructure.persistence.CommonSpecifications.fieldEquals;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BugReportSpecifications {

    public static Specification<BugReportEntity> emptySpec() {
        return CommonSpecifications.emptySpec();
    }

    public static Specification<BugReportEntity> idEquals(Long value) {
        return fieldEquals("id", value);
    }

    public static Specification<BugReportEntity> assignedUsernameEquals(String value) {
        return fieldEquals("assignedUser.username", value);
    }
}
