package lift.majiang.community.mapper;

import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where id = #{id}")
    public Question getByQueId(Integer id);


    //增加浏览数量
    @Update("update question set view_count = view_count+1 where id=#{id}")
    void incView(@Param("id") Integer id);

    //增加评论数
    @Update("update question set comment_count = comment_count+1,temp=temp+1  where id=#{id}")
    void incCommentCount(@Param("id") Integer id);

    //增加评论点赞数
    @Update("update question set like_count = like_count+1 where id=#{id}")
    void incCommentLikeCount(@Param("id") Integer id);

    //减少评论点赞数
    @Update("update question set like_count = like_count+1 where id=#{id}")
    void redCommentLikeCount(@Param("id") Integer id);

    //根据问题标签模糊查询问题列
    @Select("select id,title,tag from question where id!=#{id} and tag regexp #{tag} ")
    List<Question> listlikeQuestions(@Param("id") Integer id,@Param("tag") String tag);

    //查询一天内新增回复数最多的三个问题
    @Select("select * from question where temp !=0 order by temp desc limit 0,3")
    List<Question> hotQuestions();

    //每天清空一下temp
    @Update("update question set temp =0")
    void flushTemp();
}
