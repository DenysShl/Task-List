package org.example.tasklist.repository;

import org.example.tasklist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
            SELECT *
            FROM task t
            join users_tasks ut on t.id = ut.task_id
            where ut.user_id = :userId
            """, nativeQuery = true)
    List<Task> findAllByUserId(Long userId);
}
