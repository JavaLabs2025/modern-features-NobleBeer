package org.lab.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lab.common.dto.ApiResponse;
import org.lab.project.service.ProjectRequestHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRequestHandler projectRequestHandler;

    @GetMapping
    public ApiResponse<ProjectResponseDto> getProjects() {
        return ApiResponse.success(projectRequestHandler.getMyProjects());
    }

    @PostMapping
    public void createProject(
            @RequestBody @Valid CreateProjectRequestDto requestDto
    ) {
        projectRequestHandler.createProject(requestDto);
    }

    @PostMapping("/{projectName}/members")
    public void addProjectMember(
            @PathVariable String projectName,
            @RequestBody @Valid AddProjectMemberRequestDto requestDto
    ) {
        projectRequestHandler.addProjectMember(projectName, requestDto);
    }

    @PutMapping("/{projectName}/assign-role")
    public void assignRole(
            @PathVariable String projectName,
            @RequestBody @Valid AssignRoleRequestDto requestDto
    ) {
        projectRequestHandler.assignRole(projectName, requestDto);
    }

    //просмотреть все проекты в которых они участвуют - all users

    //создать новый проект - all users

    //назначение тимлидера к проекту - менеджер проекта

    //добавление разработчика к проекту - менеджер проекта

    //добавление тестировщика к проекту - менеджер проекта

}
