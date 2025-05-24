package io.github.minhyeon.ScheduleManagementProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String toDo;
    private String createdBy;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // id는 DB에서 auto increment
    public Schedule(String toDo, String createdBy, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.toDo = toDo;
        this.createdBy = createdBy;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
