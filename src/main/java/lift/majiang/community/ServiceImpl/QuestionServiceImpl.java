package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.exception.CustomizeException;
import lift.majiang.community.mapper.QuestionMapper;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public QuestionDTO getByQueId(Integer id) {
        Question question = questionMapper.getByQueId(id);
        if (question==null){
            log.error("this question is lose,{}",question);
            throw new CustomizeException("该问题已丢失。");
        }

        QuestionDTO questionDTO = new QuestionDTO();
        User user=userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void incView(Integer id) {
        questionMapper.incView(id);
    }

    @Override
    public List<Question> listlikeQuestions(QuestionDTO questionDTO) {
        /*根据问题标签模糊查询问题列*/
        String replace = StringUtils.replace(questionDTO.getTag(), ",", "|");
        List<Question> questionList=questionMapper.listlikeQuestions(questionDTO.getId(),replace);
        return questionList;
    }
}
