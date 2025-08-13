package sparta.schedule.dto.schedule;


import lombok.Getter;
import sparta.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class GetScheduleListResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetScheduleListResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.username = schedule.getUser().getUsername();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
