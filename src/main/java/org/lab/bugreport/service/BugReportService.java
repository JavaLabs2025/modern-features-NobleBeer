package org.lab.bugreport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.bugreport.model.BugReportEntity;
import org.lab.bugreport.model.BugStatus;
import org.lab.bugreport.persistence.repository.BugReportRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BugReportService {

    private final BugReportRepository bugReportRepository;

    public void save(BugReportEntity bugReport) {
        bugReportRepository.save(bugReport);
        log.info("Сохранен отчет об ошибке");
    }

    public void changeStatus(BugReportEntity bugReport, BugStatus status) {
        bugReport.setStatus(status);
        log.info("Статус отчета об ошибке изменен на {}", status);
    }
}
