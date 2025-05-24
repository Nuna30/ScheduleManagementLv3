package io.github.minhyeon.ScheduleManagementProject.repository;

import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleResponseDto;
import io.github.minhyeon.ScheduleManagementProject.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    public ScheduleResponseDto saveSchedule(Schedule schedule);
    public List<Schedule> getSchedulesByUpdatedFromToAndByCreatedBy(LocalDateTime updatedFrom, LocalDateTime updatedTo, String createdBy);
    public Schedule getScheduleById(Long id);
    public int updateScheduleById(Long id, Schedule newSchedule);
    public int deleteScheduleById(Long id);
}
