package org.lab.bugreport.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lab.bugreport.controller.dto.BugReportRequestDto;
import org.lab.bugreport.controller.dto.BugReportResponseDto;
import org.lab.bugreport.service.BugReportRequestHandler;
import org.lab.common.dto.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class BugReportController {

    private final BugReportRequestHandler bugReportRequestHandler;

    // посмотреть список отчетов об ошибках, которые ему надо исправить - all users
    @GetMapping("/assigned-bug-reports")
    public ApiResponse<List<BugReportResponseDto>> getAssignedBugReports() {
        return ApiResponse.success(bugReportRequestHandler.getCurrentBugReports());
    }

    // Создание сообщений об ошибках - тестировщик, разработчик
    @PreAuthorize("""
        hasAuthority('PROJECT:' + #projectName + ':DEVELOPER') 
        or 
        hasAuthority('PROJECT:' + #projectName + ':TESTER')
    """)
    @PostMapping("/projects/{projectName}/bugs")
    public ApiResponse<BugReportResponseDto> createBugReport(
            @PathVariable String projectName,
            @RequestBody @Valid BugReportRequestDto requestDto) {
        return ApiResponse.success(bugReportRequestHandler.createBugReport(projectName, requestDto));
    }

    // Исправление сообщений об ошибках - разработчик
    @PreAuthorize("hasAuthority('PROJECT:' + #projectName + ':DEVELOPER')")
    @PutMapping("/projects/{projectName}/bugs/{bugId}/fix")
    public void fixBug(
            @PathVariable String projectName,
            @PathVariable Long bugId) {
        bugReportRequestHandler.fixBug(bugId);
    }

    // Тестирование проекта - тестировщик
    @PreAuthorize("hasAuthority('PROJECT:' + #projectName + ':TESTER')")
    @PutMapping("/projects/{projectName}/bugs/{bugId}/verify")
    public void verifyBugFix(
            @PathVariable String projectName,
            @PathVariable Long bugId
    ) {
        bugReportRequestHandler.verifyBugFix(bugId);
    }

    // Проверка исправления сообщений об ошибках - тестировщик
    @PreAuthorize("hasAuthority('PROJECT:' + #projectName + ':TESTER')")
    @PutMapping("/projects/{projectName}/bugs/{bugId}/close")
    public void closeBug(
            @PathVariable String projectName,
            @PathVariable Long bugId) {
        bugReportRequestHandler.closeBug(bugId);
    }
}
