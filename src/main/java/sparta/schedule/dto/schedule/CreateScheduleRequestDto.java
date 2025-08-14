package sparta.schedule.dto.schedule;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank(message = "일정 제목은 필수입니다.") // 필수값 검증
    @Size(max = 30, message = "일정 제목은 최대 30자까지 입력할 수 있습니다.") // 길이 제한
    private String title;

    @NotBlank(message = "일정 내용은 필수입니다.")
    @Size(max = 200, message = "일정 내용은 최대 200자까지 입력할 수 있습니다.")
    private String content;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
