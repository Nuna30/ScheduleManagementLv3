package io.github.minhyeon.ScheduleManagementProject.service;

import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleRequestDto;
import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleResponseDto;
import io.github.minhyeon.ScheduleManagementProject.entity.Schedule;
import io.github.minhyeon.ScheduleManagementProject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(requestDto.getToDo(), requestDto.getCreatedBy(), requestDto.getPassword(), now, now);
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getSchedulesByUpdatedFromToAndByCreatedBy(LocalDateTime updatedFrom, LocalDateTime updatedTo, String createdBy) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByUpdatedFromToAndByCreatedBy(updatedFrom, updatedTo, createdBy);
        List<ScheduleResponseDto> ScheduleResponseDtos = schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
        return ScheduleResponseDtos;
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        return new ScheduleResponseDto(scheduleRepository.getScheduleById(id));
    }

    @Override
    public Optional<ScheduleResponseDto> updateSchedule(Long id, String password, String newToDo, String newCreatedBy) {
        Schedule schedule = scheduleRepository.getScheduleById(id); // 만약 null이라면? 이 부분 추후에 개선 필요
        schedule.setToDo(newToDo);
        schedule.setCreatedBy(newCreatedBy);
        if (schedule.getPassword().equals(password)) {
            if (scheduleRepository.updateScheduleById(id, schedule) == 0) return Optional.empty();
            else return Optional.of(new ScheduleResponseDto(schedule));
        }
        else return Optional.empty();
    }

    @Override
    public Optional<ScheduleResponseDto> deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.getScheduleById(id);
        if (schedule.getPassword().equals(password)) {
            if (scheduleRepository.deleteScheduleById(id) == 0) return Optional.empty();
            else return Optional.of(new ScheduleResponseDto(schedule));
        }
        else return Optional.empty();
    }
}
