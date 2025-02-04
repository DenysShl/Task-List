package org.example.tasklist.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.config.DataSourceConfig;
import org.example.tasklist.exception.ResourceMappingException;
import org.example.tasklist.mapper.TaskRowMapper;
import org.example.tasklist.mapper.UserRowMapper;
import org.example.tasklist.model.Role;
import org.example.tasklist.model.User;
import org.example.tasklist.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;
    private final UserRowMapper userRowMapper;

    private final String FIND_BY_ID = """
            SELECT
                u.ia as user_id,
                u.name as user_name,
                u.username as user_username,
                u.password as user_password,
                ur.role as user_role_role,
                t.id as task_id,
                t.description as task_description,
                t.expiration_date as task_expiration_date,
                t.status as task_status
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_tasks ut on u.id = ut.user_id
                LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
            SELECT
                u.ia as user_id,
                u.name as user_name,
                u.username as user_username,
                u.password as user_password,
                ur.role as user_role_role,
                t.id as task_id,
                t.description as task_description,
                t.expiration_date as task_expiration_date,
                t.status as task_status
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_tasks ut on u.id = ut.user_id
                LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.username = ?
            """;

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
           WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (?, ?, ?)
            """;

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)
            """;

    private final String IS_TASK_OWNER = """
            SELECT exists(
                SELECT 1
                FROM users_tasks
                WHERE user_id = ?
                AND task_id =?
            )
            """;

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?
            """;

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating user.");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating user.");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ResourceMappingException("Error while inserting user role");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while checking if user is task owner");
        }
    }

    @Override
    public void delete(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while deleting user");
        }
    }
}
