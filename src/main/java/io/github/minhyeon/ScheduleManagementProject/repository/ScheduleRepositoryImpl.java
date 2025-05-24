package io.github.minhyeon.ScheduleManagementProject.repository;

import io.github.minhyeon.ScheduleManagementProject.dto.ScheduleResponseDto;
import io.github.minhyeon.ScheduleManagementProject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("to_do", schedule.getToDo());
        parameters.put("created_by", schedule.getCreatedBy());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new ScheduleResponseDto(key.longValue(), schedule.getToDo(), schedule.getCreatedBy(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Override
    public List<Schedule> getSchedulesByUpdatedFromToAndByCreatedBy(LocalDateTime updatedFrom,LocalDateTime updatedTo, String createdBy) {
        return jdbcTemplate.query(
        "SELECT * FROM schedule WHERE created_by = ? AND updated_at BETWEEN ? AND ? ORDER BY updated_at DESC",
          new Object[]{createdBy, updatedFrom, updatedTo},
          new RowMapper<Schedule>() {
              @Override
              public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                  return new Schedule(
                          rs.getLong("id"),
                          rs.getString("to_do"),
                          rs.getString("created_by"),
                          rs.getString("password"),
                          rs.getTimestamp("created_at").toLocalDateTime(),
                          rs.getTimestamp("updated_at").toLocalDateTime()
                  );
              }
          }
        );
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM schedule WHERE id = ?",
            new Object[]{id},
            new RowMapper<Schedule>() {
                @Override
                public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Schedule(
                            rs.getLong("id"),
                            rs.getString("to_do"),
                            rs.getString("created_by"),
                            rs.getString("password"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                }
            }
        );
    }

    @Override
    public int updateScheduleById(Long id, Schedule newSchedule) {
        String query = "UPDATE schedule SET to_do = ?, created_by = ? WHERE id = ?";
        return jdbcTemplate.update(query, newSchedule.getToDo(), newSchedule.getCreatedBy(), id);
    }

    @Override
    public int deleteScheduleById(Long id) {
        String query = "DELETE FROM schedule WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }
}
