package sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule.dto.schedule.*;
import sparta.schedule.entity.Comment;
import sparta.schedule.entity.Schedule;
import sparta.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;

    @Transactional
    public CreateScheduleResponseDto createSchedule(final CreateScheduleRequestDto createScheduleRequestDto) {

        Schedule schedule = new Schedule(
                createScheduleRequestDto.getTitle(),
                createScheduleRequestDto.getContent(),
                createScheduleRequestDto.getAuthor(),
                createScheduleRequestDto.getPassword()
        );
        scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleListResponseDto> getAllSchedules(final String author) {
        List<Schedule> schedules;

        boolean isAuthorEmpty = validateAuthorIsEmpty(author);
        if (isAuthorEmpty) {
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        } else {
            schedules = scheduleRepository.findByAuthorOrderByModifiedAtDesc(author);
        }

        return schedules.stream()
                .map(GetScheduleListResponseDto::new)
                .toList();

    }

    @Transactional
    public UpdateScheduleByIdResponseDto updateSchedule(final Long scheduleId, final UpdateScheduleRequestDto updateScheduleRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId); // Global Exception Handler (후속 처리?)

        schedule.validatePassword(updateScheduleRequestDto.getPassword());
        schedule.updateSchedule(updateScheduleRequestDto.getTitle(), updateScheduleRequestDto.getAuthor());

        return new UpdateScheduleByIdResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(final Long scheduleId, final DeleteScheduleRequestDto deleteScheduleRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        schedule.validatePassword(deleteScheduleRequestDto.getPassword());
        scheduleRepository.delete(schedule);
    }


    @Transactional
    public GetScheduleByIdResponseDto getScheduleById(final Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        List<Comment> commentListBySchedule = commentService.getCommentListByScheduleId(scheduleId); // 퍼사드 레이어? -> 순환참조 방지 (퍼사드 패턴)
        List<GetScheduleByIdResponseDto.CommentBySchedule> commentList = commentListBySchedule.stream()
                .map(GetScheduleByIdResponseDto.CommentBySchedule::new)
                .toList();

        return new GetScheduleByIdResponseDto(schedule, commentList);
    }

    private boolean validateAuthorIsEmpty(final String author) {
        return Objects.isNull(author) || author.trim().isEmpty();
    }
}
