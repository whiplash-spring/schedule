package sparta.schedule.dto.comment;

import lombok.Getter;
import sparta.schedule.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponseDto {

    private final Long commentId;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateCommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
