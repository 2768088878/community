package lift.majiang.community.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
}
