package org.example.tasklist.repository;

import org.example.tasklist.model.Role;
import org.example.tasklist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    Optional<User> findByNameAndRolesContaining(String name, Role role);

    @Query(value = """
            select exists (
                select 1
                from users_tasks ut
                where ut.user_id = :userId
                and ut.task_id = :taskId)
            """, nativeQuery = true)
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
