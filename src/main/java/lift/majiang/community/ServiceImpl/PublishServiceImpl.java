package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.PublishMapper;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.PublishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PublishMapper questionMapper;

    @Override
    public void createQuestion(Question question) {
        questionMapper.createQuestion(question);
    }

    @Override
    public List<QuestionDTO> questionList(Integer page) {

        Integer currentPage = (page-1)*5;

        List<Question> questions=questionMapper.questionList(currentPage);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            /*
                不用这个工具类最原始的方法是：
                questionDTO.setid(question.getId();
                questionDTO.setTitle(question.getTitle();
                .....
                这个工具类的作用是 快速的把前面对象的属性 拷贝到 后面的对象属性上
             */
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }


    //统计首页有多少问题
    public int questionCount(){
        int i = questionMapper.questionCount();
        return i;
    }







}
