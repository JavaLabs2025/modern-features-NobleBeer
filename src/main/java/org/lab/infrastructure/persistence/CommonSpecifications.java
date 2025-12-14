package org.lab.infrastructure.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonSpecifications {

    public static <T> Specification<T> emptySpec() {
        return (r, q, b) -> b.conjunction();
    }

    public static <T> Specification<T> fieldEquals(String fieldName, Object value) {
        return (r, q, b) -> b.equal(r.get(fieldName), value);
    }
}
