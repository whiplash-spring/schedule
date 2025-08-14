package sparta.schedule.dto.schedule;


import lombok.Getter;
import sparta.schedule.entity.Comment;
import sparta.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleByIdResponseDto {
    
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentBySchedule> commentList;

    public GetScheduleByIdResponseDto(final Schedule schedule, final List<CommentBySchedule> commentList) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.username = schedule.getUser().getUsername();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.commentList = commentList;
    }

    @Getter
    public static class CommentBySchedule {
        private final Long commentId;
        private final String content;
        private final String username;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public CommentBySchedule(final Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.username = comment.getUser().getUsername();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }
}
