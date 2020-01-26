package lift.majiang.community.service;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;

import java.util.List;

public interface PublishService {
    public void createQuestion(Question question);

    public List<QuestionDTO> questionList(Integer page);

    //统计首页有多少问题
    public int questionCount();
}
