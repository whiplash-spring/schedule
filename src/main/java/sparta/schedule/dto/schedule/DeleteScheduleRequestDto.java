package sparta.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
