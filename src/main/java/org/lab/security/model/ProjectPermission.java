package org.lab.security.model;

import org.lab.projectmember.model.ProjectRole;

public record ProjectPermission(Long projectId, String projectName, ProjectRole role) {
}
