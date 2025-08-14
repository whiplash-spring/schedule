package sparta.schedule.dto.user;

import lombok.Getter;
import sparta.schedule.entity.User;

import java.time.LocalDateTime;

@Getter
public class LoginResponseDto {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private LoginResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

    public static LoginResponseDto from(User user) {
        return new LoginResponseDto(user);
    }

}
