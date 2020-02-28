package lift.majiang.community.service;

import lift.majiang.community.entity.NotificationDTO;
import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;

import java.util.List;

public interface PublishService {

    public List<QuestionDTO> questionList(Integer page);

    //统计首页有多少问题
    public int questionCount();

    //根据ID查询该id的所有问题
    public List<QuestionDTO> questionById(Integer creator,Integer page);

    //根据ID查询该id的问题总数
    public Integer questionsCountById(Integer creator);

    void createOrUpdate(Question question);

    List<NotificationDTO> repliesById(Integer id, Integer page);
}
