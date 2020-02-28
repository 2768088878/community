package lift.majiang.community.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer questionId;
    private Integer commentId;
    private Integer type;
    private Timestamp gmtCreate;
    private Integer status;
    private String content;//内容
}
