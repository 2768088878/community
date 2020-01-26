package lift.majiang.community.mapper;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PublishMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void createQuestion(Question question);

    @Select("select * from question limit #{current} , 5")
    public List<Question> questionList(Integer current);

    //统计有多少问题
    @Select("select count(*) from question")
    public int questionCount();
}
