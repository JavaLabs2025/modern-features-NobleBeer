package org.lab.milestone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lab.milestone.controller.dto.ChangeMilestoneStatusRequestDto;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MilestoneRequestHandler {

    private final MilestoneSearchService milestoneSearchService;
    private final MilestoneService milestoneService;

    public void changeStatus(String milestoneName, ChangeMilestoneStatusRequestDto requestDto) {
        var searchParams = MilestoneSearchService.MilestoneSearchParams.builder()
                .name(milestoneName)
                .build();
        var milestoneOpt = milestoneSearchService.findOne(searchParams);
        if (milestoneOpt.isEmpty()) {
            return;
        }

        var milestone = milestoneOpt.get();
        // add строковые шаблоны условие закрытия
        milestoneService.changeStatus(milestone, requestDto.getStatus());
    }
}
