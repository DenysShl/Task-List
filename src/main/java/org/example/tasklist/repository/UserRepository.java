package org.example.tasklist.repository;

import org.apache.ibatis.annotations.Param;
import org.example.tasklist.model.Role;
import org.example.tasklist.model.User;

import java.util.Optional;

//@Mapper
public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String userName);

    void update(User user);

    void create(User user);

    void insertUserRole(@Param("userId") Long userId, @Param("role") Role role);

    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);

    void delete(Long userId);
}
