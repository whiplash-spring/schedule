package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByScheduleId(Long scheduleId);

    List<Comment> findAllByScheduleId(Long scheduleId);
}
