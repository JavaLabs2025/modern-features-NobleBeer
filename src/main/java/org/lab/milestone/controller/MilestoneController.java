package org.lab.milestone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/projects/{projectName}")
@RequiredArgsConstructor
public class MilestoneController {
    // создание нового майлстоуна - менеджер проекта
    // изменение статуса майлстоуна - менеджер проекта

    private final MilestoneRequestHandler milestoneRequestHandler;

    @PostMapping("/{milestoneName}")
    public void changeMilestoneStatus(
            @PathVariable String projectName,
            @PathVariable String milestoneName,
            @Valid @RequestBody ChangeMilestoneStatusRequestDto requestDto) {
        milestoneRequestHandler.changeStatus(projectName, milestoneName, requestDto);
    }

    @PostMapping("/create")
    public void createMilestone(
            @PathVariable String projectName,
            @Valid @RequestBody CreateNewMilestoneRequestDto requestDto) {
        milestoneRequestHandler.createNewMilestone(projectName, requestDto);
    }
}
