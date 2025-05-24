package io.github.minhyeon.ScheduleManagementProject.service;

import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleRequestDto;
import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> getSchedulesByUpdatedFromToAndByCreatedBy(LocalDateTime UpdatedFrom, LocalDateTime UpdateTo, String CreatedBy);
    ScheduleResponseDto getScheduleById(Long id);
    Optional<ScheduleResponseDto> updateSchedule(Long id, String password, String NewToDo, String NewCreatedBy);
    Optional<ScheduleResponseDto> deleteSchedule(Long id, String password);
}
