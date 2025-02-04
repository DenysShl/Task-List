package org.example.tasklist.mapper;

import lombok.SneakyThrows;
import org.example.tasklist.model.Status;
import org.example.tasklist.model.Task;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRowToTask(ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));
            Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
            if (timestamp != null) {
                task.setExpirationDate(resultSet.getTimestamp("task_expiration_date").toLocalDateTime());
            }
            return task;
        }
        return null;
    }

    @SneakyThrows
    public static List<Task> mapRowToTasks(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if (!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("task_title"));
                task.setDescription(resultSet.getString("task_description"));
                task.setStatus(Status.valueOf(resultSet.getString("task_status")));
                Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
                if (timestamp != null) {
                    task.setExpirationDate(resultSet.getTimestamp("task_expiration_date").toLocalDateTime());
                }
                tasks.add(task);
            }
        }
        return tasks;
    }
}
