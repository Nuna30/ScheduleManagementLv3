package io.github.minhyeon.ScheduleManagementProject.dto;

import io.github.minhyeon.ScheduleManagementProject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleUpdateDto {
    private Long id;
    private String password;
    private String newToDo;
    private String newCreatedBy;
}
