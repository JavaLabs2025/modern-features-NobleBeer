package org.lab.milestone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.milestone.model.MilestoneEntity;
import org.lab.milestone.model.MilestoneStatus;
import org.lab.milestone.persistence.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneService {

    private MilestoneRepository milestoneRepository;

    public void changeStatus(MilestoneEntity milestone, MilestoneStatus status) {

    }
}
