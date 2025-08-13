package sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long scheduleId;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;


    public Comment(Long scheduleId, String content, String author, String password) {
        this.scheduleId = scheduleId;
        this.content = content;
        this.author = author;
        this.password = password;
    }
}
