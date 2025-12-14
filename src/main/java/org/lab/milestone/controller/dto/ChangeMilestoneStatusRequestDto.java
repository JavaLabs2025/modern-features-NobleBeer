package org.lab.milestone.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.lab.milestone.model.MilestoneStatus;

@Getter
@Builder
@Jacksonized
public class ChangeMilestoneStatusRequestDto {
    private MilestoneStatus status;
}
