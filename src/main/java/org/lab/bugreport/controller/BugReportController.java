package org.lab.bugreport.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lab.bugreport.controller.dto.BugReportRequestDto;
import org.lab.bugreport.controller.dto.BugReportResponseDto;
import org.lab.bugreport.service.BugReportRequestHandler;
import org.lab.common.dto.ApiResponse;
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

    @GetMapping("/bugs/assigned-bug-reports")
    public ApiResponse<List<BugReportResponseDto>> getAssignedBugReports() {
        return ApiResponse.success(bugReportRequestHandler.getCurrentBugReports());
    }

    @PostMapping("/projects/{projectName}/bugs")
    public ApiResponse<BugReportResponseDto> createBugReport(
            @PathVariable String projectName,
            @RequestBody @Valid BugReportRequestDto requestDto) {
        return ApiResponse.success(bugReportRequestHandler.createBugReport(projectName, requestDto));
    }

    @PutMapping("/projects/{projectName}/bugs/{bugId}/fix")
    public void fixBug(
            @PathVariable String projectName,
            @PathVariable Long bugId) {
        bugReportRequestHandler.fixBug(bugId);
    }

    @PutMapping("/projects/{projectName}/{bugId}/verify")
    public void verifyBugFix(
            @PathVariable String projectName,
            @PathVariable Long bugId
    ) {
        bugReportRequestHandler.verifyBugFix(bugId);
    }

    @PutMapping("/projects/{projectName}/{bugId}/close")
    public void closeBug(
            @PathVariable String projectName,
            @PathVariable Long bugId) {
        bugReportRequestHandler.closeBug(bugId);
    }
    // посмотреть список отчетов об ошибках, которые ему надо исправить - all users

    // Создание сообщений об ошибках - тестировщик, разработчик

    // Исправление сообщений об ошибках - разработчик

    // Тестирование проекта - тестировщик

    // Проверка исправления сообщений об ошибках - тестировщик
}
