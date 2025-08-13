package sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sparta.schedule.dto.schedule.*;
import sparta.schedule.entity.Comment;
import sparta.schedule.entity.Schedule;
import sparta.schedule.entity.User;
import sparta.schedule.repository.ScheduleRepository;
import sparta.schedule.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponseDto createSchedule(final CreateScheduleRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user,
                request.getPassword()
        );
        scheduleRepository.save(schedule);
        return CreateScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleListResponseDto> getAllSchedules(final String author) {
        List<Schedule> schedules;

        boolean isAuthorEmpty = validateAuthorIsEmpty(author);
        if (isAuthorEmpty) {
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        } else {
            schedules = scheduleRepository.findByUsernameOrderByModifiedAtDesc(author);
        }

        return schedules.stream()
                .map(GetScheduleListResponseDto::new)
                .toList();
    }

    @Transactional
    public UpdateScheduleByIdResponseDto updateSchedule(final Long scheduleId, final UpdateScheduleRequestDto request) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId); // Global Exception Handler (후속 처리?)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        schedule.validatePassword(request.getPassword());
        schedule.updateTitleAndUser(request.getTitle(), user);
        return new UpdateScheduleByIdResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(final Long scheduleId, final DeleteScheduleRequestDto deleteScheduleRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        schedule.validatePassword(deleteScheduleRequestDto.getPassword());
        scheduleRepository.delete(schedule);
    }


    @Transactional(readOnly = true)
    public GetScheduleByIdResponseDto getScheduleById(final Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        List<Comment> commentListBySchedule = commentService.getCommentListByScheduleId(scheduleId); // 퍼사드 레이어? -> 순환참조 방지 (퍼사드 패턴)
        List<GetScheduleByIdResponseDto.CommentBySchedule> commentList = commentListBySchedule.stream()
                .map(GetScheduleByIdResponseDto.CommentBySchedule::new)
                .toList();

        return new GetScheduleByIdResponseDto(schedule, commentList);
    }

    private boolean validateAuthorIsEmpty(final String author) {
        return !StringUtils.hasText(author);
    }
}
