package org.example.tasklist.repository;

import org.example.tasklist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
            SELECT *
            FROM task t
            join users_tasks ut on t.id = ut.task_id
            where ut.user_id = :userId
            """,
            nativeQuery = true)
    List<Task> findAllByUserId(@Param("userId") Long userId);

    @Query(value = """
            select *
            from task t
            where t.expeiration_date is not null
            and t.expiration_date between :start and :end
            """,
            nativeQuery = true)
    List<Task> findAllSoonTasks(@Param("start") Timestamp start, @Param("end") Timestamp end);
}
