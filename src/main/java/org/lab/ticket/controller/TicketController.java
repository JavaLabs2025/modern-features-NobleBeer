package org.lab.ticket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lab.common.dto.ApiResponse;
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
@RequestMapping("/projects/{projectName}/{milestoneName}/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRequestHandler ticketRequestHandler;

    @GetMapping("/my")
    public ApiResponse<TicketResponseDto> getMyTickets(
            @PathVariable String projectName,
            @PathVariable String milestoneName
    ) {
        return ApiResponse.success(ticketRequestHandler.getMyTickets(projectName, milestoneName));
    }

    @PostMapping
    public void createTicket(
            @PathVariable String projectName,
            @PathVariable String milestoneName,
            @RequestBody @Valid CreateTicketRequestDto requestDto
    ) {
        ticketRequestHandler.createTicket(projectName, milestoneName, requestDto);
    }

    @PostMapping("/{ticketId}/assignees")
    public void assignDeveloper(
            @PathVariable String projectName,
            @PathVariable String milestoneName,
            @PathVariable Long ticketId,
            @RequestBody @Valid AssignTicketDeveloperRequestDto requestDto
    ) {
        ticketRequestHandler.assignDeveloper(projectName, milestoneName, ticketId, requestDto);
    }

    @PutMapping("/{ticketId}/review")
    public void reviewTicket(
            @PathVariable String projectName,
            @PathVariable String milestoneName,
            @PathVariable Long ticketId,
            @RequestBody @Valid ReviewTicketRequestDto requestDto
    ) {
        ticketRequestHandler.moveTicketToDone(projectName, milestoneName, ticketId, requestDto);
    }

    @PutMapping("/{ticketId}/status")
    public void updateTicketStatus(
            @PathVariable String projectName,
            @PathVariable String milestoneName,
            @PathVariable Long ticketId,
            @RequestBody @Valid UpdateTicketStatusRequestDto requestDto
    ) {
        ticketRequestHandler.moveTicketInProgress(projectName, milestoneName, ticketId, requestDto);
    }

    // посмотреть список заданий (ticket), который был им выдан - all users

    // создание нового тикета - менеджер проекта, тимлидер

    // привязка разработчика к тикету - менеджер проекта, тимлидер

    // проверка выполнения тикета - менеджер проекта, тимлидер

    // выполнение тикетов - тимлидер, разработчик
}
