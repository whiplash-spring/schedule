package sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 공통 필드를 상속받을 수 있게한다.
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 활성화
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false) // 생성 후 수정 불가
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}
