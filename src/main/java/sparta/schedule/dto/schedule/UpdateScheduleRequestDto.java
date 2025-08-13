package sparta.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Size(max = 30, message = "일정 제목은 최대 30자까지 입력할 수 있습니다.")
    private String title;

    @NotNull(message = "작성자 식별자는 필수입니다.")
    private Long userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
