package io.github.minhyeon.ScheduleManagementProject.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String toDo;
    private String createdBy;
    private String password;
}