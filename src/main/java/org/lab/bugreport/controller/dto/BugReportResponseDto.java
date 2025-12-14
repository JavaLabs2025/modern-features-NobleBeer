package org.lab.bugreport.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.lab.bugreport.model.BugStatus;

import java.time.Instant;

@Getter
@Setter
@Builder
public class BugReportResponseDto {
    private Long id;
    private String projectName;
    private String title;
    private String description;
    private BugStatus status;
    private Long createdBy;
    private String assignedUsername;
    private Instant createdAt;
}
