package lift.majiang.community.mapper;

import lift.majiang.community.entity.Notification;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (id,notifier,receiver,question_id,type,gmt_create,status,content) values" +
            "(#{id},#{notifier},#{receiver},#{questionId},#{type},now(),#{status},#{content})")
    void insertQuestionNotice(Notification notification);

    @Insert("insert into notification (id,notifier,receiver,question_id,comment_id,type,gmt_create,status,content) values" +
            "(#{id},#{notifier},#{receiver},#{questionId},#{commentId},#{type},#{gmtCreate},#{status},#{content})")
    void insertCommentNotice(Notification notification);

    //根据用户(接收人)id获取他未读的所有通知
    @Select("select count(*) from notification where receiver = #{id} and status = 0 order by gmt_create DESC")
    Integer noticeCount(@Param("id") Integer id);

    @Select("select id from notification where question_id = #{id}")
    Integer selectNoticeByQuestionId(@Param("id") Integer id);

    //标记为已读
    @Update("update notification set status = 1 where id = #{id}")
    void updateStatus(@Param("id") Integer id);



}
