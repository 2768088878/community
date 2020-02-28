package lift.majiang.community.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
