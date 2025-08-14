package sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule.dto.comment.CreateCommentRequestDto;
import sparta.schedule.dto.comment.CreateCommentResponseDto;
import sparta.schedule.entity.Comment;
import sparta.schedule.entity.Schedule;
import sparta.schedule.entity.User;
import sparta.schedule.global.PasswordEncoder;
import sparta.schedule.repository.CommentRepository;
import sparta.schedule.repository.ScheduleRepository;
import sparta.schedule.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateCommentResponseDto createComment(final CreateCommentRequestDto request, final Long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        long totalCommentOfSchedule = getTotalCommentOfSchedule(scheduleId);
        validateMaxCommentCountOverTen(totalCommentOfSchedule);

        Comment comment = new Comment(
                schedule,
                request.getContent(),
                user,
                passwordEncoder.encode(request.getPassword())
        );

        commentRepository.save(comment);

        return new CreateCommentResponseDto(comment);
    }

    // Schedule 객체를 넘겨주는게 맞을까? 아니면 scheduleId만 받는게 맞을까..?
    // scheduleId만 받으면 Schedule과 관련없는 정수값이 오면...
    // 확장성 vs 타입 안정성... -> 과제이기 때문에 더 확장될 일이 없으므로 명확한 값만 전달하기로..!
    public List<Comment> getCommentListByScheduleId(final Long scheduleId) {
        return commentRepository.findAllByScheduleId(scheduleId);
    }

    private static void validateMaxCommentCountOverTen(final long totalCommentOfSchedule) {
        if (totalCommentOfSchedule >= 10) {
            throw new IllegalArgumentException("일정당 최대 10개의 댓글만 생성 가능합니다.");
        }
    }

    private long getTotalCommentOfSchedule(final Long scheduleId) {
        return commentRepository.countByScheduleId((scheduleId));
    }

}
