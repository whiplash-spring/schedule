package sparta.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.schedule.dto.comment.CreateCommentRequestDto;
import sparta.schedule.dto.comment.CreateCommentResponseDto;
import sparta.schedule.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponseDto> createCommentInSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequestDto requestDto) {
        CreateCommentResponseDto comment = commentService.createComment(requestDto, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
}
