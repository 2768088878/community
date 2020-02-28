package lift.majiang.community.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
/*通知的模型*/
public class NotificationDTO {
    private Integer id;
    private Timestamp gmtCreate;//通知时间
    private Integer status;
    private  User notifier;//通知的人
    private Integer questionId;
    private Integer commentId;
    private String Title;//发布问题的标题
    private String type;//通知的类型
    private String content;//内容
}
