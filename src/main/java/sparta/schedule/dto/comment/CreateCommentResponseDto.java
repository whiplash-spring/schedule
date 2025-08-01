package sparta.schedule.dto.comment;

import lombok.Getter;
import sparta.schedule.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponseDto {

    private final Long commentId;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateCommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
