package io.github.minhyeon.ScheduleManagementProject.controller;

import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleDeleteDto;
import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleRequestDto;
import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleResponseDto;
import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleUpdateDto;
import io.github.minhyeon.ScheduleManagementProject.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/schedules", "/schedules/"})
public class ScheduleController {
    private final ScheduleService scheduleService;

    private ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByUpdatedFromToAndByCreatedBy(
        @RequestParam("updatedFrom") String updatedFrom,
        @RequestParam("updatedTo") String updatedTo,
        @RequestParam("createdBy") String createdBy
    ) {
        return new ResponseEntity<>(scheduleService.getSchedulesByUpdatedFromToAndByCreatedBy(
                LocalDate.parse(updatedFrom).atStartOfDay(),
                LocalDate.parse(updatedTo).atStartOfDay(),
                createdBy
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@RequestBody ScheduleUpdateDto updateDto) {
        return new ResponseEntity<ScheduleResponseDto> (
            scheduleService.updateSchedule(
                updateDto.getId(),
                updateDto.getPassword(),
                updateDto.getNewToDo(),
                updateDto.getNewCreatedBy()
            ).orElseThrow(() -> new RuntimeException("wrong password or wrong id")),
        HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@RequestBody ScheduleDeleteDto deleteDto) {
        return new ResponseEntity<ScheduleResponseDto> (
                scheduleService.deleteSchedule(
                        deleteDto.getId(),
                        deleteDto.getPassword()
                ).orElseThrow(() -> new RuntimeException("wrong password or wrong id")),
        HttpStatus.OK);
    }
}
