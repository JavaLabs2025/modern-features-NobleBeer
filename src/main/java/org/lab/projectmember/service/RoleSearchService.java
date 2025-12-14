package org.lab.projectmember.service;

import lombok.RequiredArgsConstructor;
import org.lab.persistence.repository.RoleRepository;
import org.lab.projectmember.model.ProjectMemberEntity;
import org.lab.projectmember.model.ProjectRole;

@RequiredArgsConstructor
public class RoleSearchService {

    private final RoleRepository repository;

    public ProjectMemberEntity findOne(ProjectRole role) {
        return repository.findByName(role.name());
    }
}
