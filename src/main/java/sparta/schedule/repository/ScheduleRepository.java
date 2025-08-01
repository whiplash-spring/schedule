package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 수정일 기준 내림차순으로 전체 조회
    List<Schedule> findAllByOrderByModifiedAtDesc();

    // 작성자명으로 필터링하고 수정일 기준으로 내림차순 정렬
    List<Schedule> findByAuthorOrderByModifiedAtDesc(String author);




}
