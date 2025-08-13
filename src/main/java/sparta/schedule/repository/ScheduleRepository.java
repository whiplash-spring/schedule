package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sparta.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 수정일 기준 내림차순으로 전체 조회
    List<Schedule> findAllByOrderByModifiedAtDesc();

    // 작성자명으로 필터링하고 수정일 기준으로 내림차순 정렬
    @Query("SELECT s FROM Schedule s WHERE s.user.username = :username ORDER BY s.modifiedAt DESC")
    List<Schedule> findByUsernameOrderByModifiedAtDesc(@Param("username") String username);



    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. ID: " + scheduleId));
    }
}
