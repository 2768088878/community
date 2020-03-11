package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.*;
import lift.majiang.community.enums.NotificationEnum;
import lift.majiang.community.mapper.CommentMapper;
import lift.majiang.community.mapper.PublishMapper;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.CommentService;
import lift.majiang.community.service.PublishService;
import org.apache.commons.lang3.StringUtils;
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
    private CommentMapper commentMapper;
    @Autowired
    private PublishMapper questionMapper;

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            questionMapper.createQuestion(question);
        }else {
            questionMapper.update(question);
        }

    }

    @Override
    public List<QuestionDTO> questionList(String search,Integer page) {

        Integer currentPage = (page-1)*5;
        List<Question> questions;
        if (StringUtils.isNotBlank(search)){
            questions=questionMapper.questionBysearchList(search, currentPage);
        }else {
            questions=questionMapper.questionList(currentPage);
        }

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


    //根据ID查询该id的所有问题(分页
    public List<QuestionDTO> questionById(Integer creator,Integer page){

        Integer currentPage = (page-1)*5;

        List<Question> questionList=questionMapper.questionsById(creator,currentPage);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questionList){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

    return questionDTOList;
    }

    //根据ID查询该id的问题总数
    public Integer questionsCountById(Integer creator){
        Integer i = questionMapper.questionsCountById(creator);
        return i;
    }

    //根据ID查询该id的所有问题(分页
    public List<NotificationDTO> repliesById(Integer id, Integer page){

        Integer currentPage = (page-1)*5;

        List<Notification> repliesLists=questionMapper.repliesById(id,currentPage);
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for(Notification replies:repliesLists){
            User user=userMapper.findById(replies.getNotifier());//通知人
            NotificationDTO notificationDTO=new NotificationDTO();
            notificationDTO.setNotifier(user);
            notificationDTO.setId(replies.getId());
            notificationDTO.setContent(replies.getContent());
            if (replies.getType()==1){
                //问题的标题
                String title = questionMapper.questionTitle(replies.getQuestionId());
                notificationDTO.setTitle(title);
                notificationDTO.setQuestionId(replies.getQuestionId());
                notificationDTO.setType(NotificationEnum.REPLY_QUESTION.getName());
            }
            if (replies.getType()==2){
                //评论的内容
                Integer questionId = questionMapper.questionId(replies.getCommentId());
                String title = questionMapper.questionTitle(questionId);
                notificationDTO.setTitle(title);
                notificationDTO.setQuestionId(replies.getQuestionId());
                notificationDTO.setCommentId(replies.getCommentId());
                notificationDTO.setType(NotificationEnum.REPLY_COMMENT.getName());
            }
            notificationDTO.setStatus(replies.getStatus());
            notificationDTO.setGmtCreate(replies.getGmtCreate());
            notificationDTOS.add(notificationDTO);
        }

        return notificationDTOS;
    }


}
