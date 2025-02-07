package org.example.tasklist.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.config.DataSourceConfig;
import org.example.tasklist.exception.ResourceMappingException;
import org.example.tasklist.mapper.TaskRowMapper;
import org.example.tasklist.model.Task;
import org.example.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;
    private final TaskRowMapper taskRowMapper;

    private final String FIND_BY_ID = """
            SELECT
                 t.id as task_id,
                 t.title as task_title,
                 t.description as task_description,
                 t.status as task_status,
                 t.expiration_date as task_expiration_date
             FROM task t
             WHERE t.id = ?
             """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT
                t.id as task_id,
                t.title as task_title,
                t.description as task_description,
                t.status as task_status,
                t.expiration_date as task_expiration_date
            FROM task t
                JOIN users_tasks ut ON t.id = ut.task_id
            WHERE ut.user_id = ?
            """;

    private final String ASSIGN_TO_USER_BY_ID = """
            INSERT INTO users_tasks (task_id, user_id)
            VALUES (?, ?)
            """;

    private final String UPDATE = """
            UPDATE task
            SET title = ?,
                description = ?,
                status = ?,
                expiration_date = ?
            WHERE id = ?
            """;

    private final String DELETE_BY_ID = """
            DELETE FROM task
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO task (title, description, status, expiration_date)
            VALUES (?, ?, ?, ?)
            """;

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRowToTask(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return TaskRowMapper.mapRowToTasks(resultSet);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding all by user id.");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_USER_BY_ID);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assigning task to user.");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            if (task.getTitle() != null) {
                statement.setString(1, task.getTitle());
            } else {
                statement.setNull(1, java.sql.Types.VARCHAR);
            }
            if (task.getDescription() != null) {
                statement.setString(2, task.getDescription());
            } else {
                statement.setNull(2, java.sql.Types.VARCHAR);
            }
            if (task.getStatus() != null) {
                statement.setString(3, task.getStatus().name());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
            }
            if (task.getExpirationDate() != null) {
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(task.getExpirationDate()));
            } else {
                statement.setNull(4, java.sql.Types.TIMESTAMP);
            }
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating task.");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            if (task.getTitle() != null) {
                statement.setString(1, task.getTitle());
            } else {
                statement.setNull(1, java.sql.Types.VARCHAR);
            }
            if (task.getDescription() != null) {
                statement.setString(2, task.getDescription());
            } else {
                statement.setNull(2, java.sql.Types.VARCHAR);
            }
            if (task.getStatus() != null) {
                statement.setString(3, task.getStatus().name());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
            }
            if (task.getExpirationDate() != null) {
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(task.getExpirationDate()));
            } else {
                statement.setNull(4, java.sql.Types.TIMESTAMP);
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                task.setId(resultSet.getLong(1));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating task.");
        }
    }

    @Override
    public void delete(Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting task.");
        }
    }
}
