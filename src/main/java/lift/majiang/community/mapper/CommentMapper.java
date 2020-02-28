package lift.majiang.community.mapper;

import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.CommentDTO;
import lift.majiang.community.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    //回复
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values" +
            "(#{parentId},#{type},#{commentator},now(),now(),#{likeCount},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);


    //增加回复数
    @Update("update comment set comment_count = comment_count+1 where id=#{id}")
    void incReplyCount(@Param("id") Integer id);

    //增加回复点赞数
    @Update("update comment set like_count = like_count+1 where id=#{id}")
    void incCommentLikeCount(@Param("id") Integer id);

    //减少回复点赞数
    @Update("update comment set like_count = like_count+1 where id=#{id}")

    void redCommentLikeCount(@Param("id") Integer id);
    //根据问题id获取它的评论
    @Select("select * from comment where parent_id = #{parent_id} and type=1 order by gmt_create DESC")
    List<Comment> listByQuestionId(@Param("parent_id") Integer id);

    //根据评论id获取它的子回复
    @Select("select * from comment where parent_id = #{id} and type = 2 order by gmt_create DESC")
    List<Comment> listComments(@Param("id") Integer id);

    //通过评论的问题id查出问题的发布人
    @Select("select creator from question where id = #{id}")
    Integer findCommentTatorByParentId(@Param("id") Integer parentId);

    @Select("select commentator,parent_id from comment where id = #{id}")
    Comment findComtorAndQuesIdByParentId(Integer parentId);

    //通过评论的id查出评论的内容
    @Select("select content from comment where id = #{id}")
    String commentContent(Integer commentId);




}
