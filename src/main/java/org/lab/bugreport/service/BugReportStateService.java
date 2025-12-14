package org.lab.bugreport.service;

import lombok.RequiredArgsConstructor;
import org.lab.bugreport.model.BugReportEntity;
import org.lab.bugreport.model.BugStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BugReportStateService {

    private final BugReportService bugReportService;

    private static final Set<BugStatus> ALLOWED_FOR_FIX_STATUSES = Set.of(BugStatus.NEW);
    private static final Set<BugStatus> ALLOWED_FOR_TEST_STATUSES = Set.of(BugStatus.FIXED);
    private static final Set<BugStatus> ALLOWED_FOR_CLOSE_STATUSES = Set.of(BugStatus.TESTED);

    public void tryFix(BugReportEntity bugReport) {
        var targetStatus = BugStatus.FIXED;

        if (targetStatus == bugReport.getStatus()) {
            return;
        }

        if (!ALLOWED_FOR_FIX_STATUSES.contains(bugReport.getStatus())) {
            throw new IllegalStateException("Смена статуса невозможна");
        }

        bugReportService.changeStatus(bugReport, targetStatus);
    }

    public void tryTest(BugReportEntity bugReport) {
        var targetStatus = BugStatus.TESTED;

        if (targetStatus == bugReport.getStatus()) {
            return;
        }

        if (!ALLOWED_FOR_TEST_STATUSES.contains(bugReport.getStatus())) {
            throw new IllegalStateException("Смена статуса невозможна");
        }

        bugReportService.changeStatus(bugReport, targetStatus);
    }

    public void tryClose(BugReportEntity bugReport) {
        var targetStatus = BugStatus.CLOSED;

        if (targetStatus == bugReport.getStatus()) {
            return;
        }

        if (!ALLOWED_FOR_CLOSE_STATUSES.contains(bugReport.getStatus())) {
            throw new IllegalStateException("Смена статуса невозможна");
        }

        bugReportService.changeStatus(bugReport, targetStatus);
    }
}
