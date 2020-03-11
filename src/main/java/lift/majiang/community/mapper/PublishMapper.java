package lift.majiang.community.mapper;

import lift.majiang.community.entity.Notification;
import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Mapper
public interface PublishMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},NOW(),NOW(),#{creator},#{tag})")
    public void createQuestion(Question question);

    @Update("update question set title=#{title},description=#{description},gmt_modified=NOW(),tag=#{tag} where id=#{id}")
    public void update(Question question);

    @Select("select * from question order by gmt_create DESC limit #{current} , 5")
    public List<Question> questionList(Integer current);

    //搜索栏搜问题
    @Select("select * from question where title like CONCAT('%',#{search},'%')  order by gmt_create DESC limit #{current} , 5")
    public List<Question> questionBysearchList(@Param("search") String search,@Param("current") Integer current);

    //统计有多少问题
    @Select("select count(*) from question")
    public int questionCount();

    //根据ID查询该id的所有问题
    @Select("select * from question where creator = #{creator} limit #{current} , 5")
    public List<Question> questionsById(@Param("creator") Integer creator,@Param("current") Integer current);

    //根据ID查询问题总数
    @Select("select count(*) from question where creator = #{creator}")
    public Integer questionsCountById(@Param("creator") Integer creator);

    //根据ID查询该id的所有问题
    @Select("select * from notification where receiver = #{id} order by gmt_create desc limit #{current} , 5")
    public List<Notification> repliesById(@Param("id") Integer id, @Param("current") Integer current);

    //根据问题id查出问题标题
    @Select("select title from question where id = #{id}")
    public String questionTitle(@Param("id") Integer id);

    //找到评论的
    @Select("select parent_id from comment where id = #{id}")
    public Integer questionId(@Param("id") Integer id);
}
