package sparta.schedule.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 100, message = "댓글 내용은 최대 100자까지 입력할 수 있습니다.")
    private String content;

    @NotBlank(message = "작성자명은 필수입니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}
