package org.lab.bugreport.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.bugreport.controller.dto.BugReportRequestDto;
import org.lab.bugreport.controller.dto.BugReportResponseDto;
import org.lab.bugreport.model.BugReportEntity;
import org.lab.bugreport.model.BugStatus;
import org.lab.project.service.ProjectSearchService;
import org.lab.security.service.CustomUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BugReportRequestHandler {

    private final BugReportService bugReportService;
    private final ProjectSearchService projectSearchService;
    private final BugReportStateService bugReportStateService;
    private final BugReportSearchService bugReportSearchService;
    private final CustomUserDetailsService userDetailsService;

    public List<BugReportResponseDto> getCurrentBugReports() {
        var currentUser = userDetailsService.getAuthenticatedUser();
        var searchParams = BugReportSearchService.BugReportSearchParams.builder()
                .assignedUsername(currentUser.getUsername())
                .build();

        var bugs = bugReportSearchService.findAll(searchParams);
        return bugs.stream().map(bug -> BugReportResponseDto.builder()
                .id(bug.getId())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .status(bug.getStatus())
                .build()).toList();
    }

    public BugReportResponseDto createBugReport(String projectName, BugReportRequestDto bugReportRequestDto) {
        var currentUser = userDetailsService.getAuthenticatedUser();

        var searchParams = ProjectSearchService.ProjectSearchParams.builder()
                .name(projectName)
                .build();

        var projectOpt = projectSearchService.findOne(searchParams);
        if (projectOpt.isEmpty()) {
            return null;
        }

        var project = projectOpt.get();

        var bugReport = new BugReportEntity();
        bugReport.setCreatedBy(currentUser.getId());
        bugReport.setProject(project);
        bugReport.setTitle(bugReportRequestDto.getTitle());
        bugReport.setDescription(bugReportRequestDto.getDescription());
        bugReport.setStatus(BugStatus.NEW);
        bugReportService.save(bugReport);

        return BugReportResponseDto.builder().id(bugReport.getId()).build();
    }

    public void fixBug(Long bugId) {
        var bugReportOpt = findBugReport(bugId);
        if (bugReportOpt.isEmpty()) {
            return;
        }

        var bugReport = bugReportOpt.get();
        bugReportStateService.tryFix(bugReport);
    }

    public void verifyBugFix(Long bugId) {
        var bugReportOpt = findBugReport(bugId);
        if (bugReportOpt.isEmpty()) {
            return;
        }

        var bugReport = bugReportOpt.get();
        bugReportStateService.tryTest(bugReport);
    }

    public void closeBug(Long bugId) {
        var bugReportOpt = findBugReport(bugId);
        if (bugReportOpt.isEmpty()) {
            return;
        }

        var bugReport = bugReportOpt.get();
        bugReportStateService.tryClose(bugReport);
    }

    private Optional<BugReportEntity> findBugReport(Long id) {
        var searchParams = BugReportSearchService.BugReportSearchParams.builder()
                .id(id)
                .build();

        return bugReportSearchService.findOne(searchParams);
    }
}
