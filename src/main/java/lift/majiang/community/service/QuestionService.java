package lift.majiang.community.service;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;

import java.util.List;

public interface QuestionService {

    public QuestionDTO getByQueId(Integer id);

    void incView(Integer id);

    public List<Question> listlikeQuestions(QuestionDTO questionDTO);
}
