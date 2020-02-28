package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.CommentDTO;
import lift.majiang.community.entity.Notification;
import lift.majiang.community.entity.User;
import lift.majiang.community.enums.NotificationEnum;
import lift.majiang.community.enums.NotificationStatusEnum;
import lift.majiang.community.mapper.CommentMapper;
import lift.majiang.community.mapper.NotificationMapper;
import lift.majiang.community.mapper.QuestionMapper;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    //回复
    public void insert(Comment comment,Integer id){
        commentMapper.insert(comment);


        Integer type=comment.getType();
        if (type==1){
            //评论 增加评论数
            questionMapper.incCommentCount(comment.getParentId());
            //增加评论的通知
//            Integer Master = commentMapper.findCommentTatorByParentId(comment.getParentId());
            Notification notification = new Notification();
            notification.setType(NotificationEnum.REPLY_QUESTION.getType());
            notification.setQuestionId(comment.getParentId());//评论的问题id
            notification.setNotifier(comment.getCommentator());//通知人=评论人
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            //通过评论的问题id查出问题的发布人
            Integer receiver=commentMapper.findCommentTatorByParentId(comment.getParentId());
            notification.setReceiver(receiver);
            notification.setQuestionId(comment.getParentId());
            notification.setContent(comment.getContent());
            notificationMapper.insertQuestionNotice(notification);
        }
        if (type==2){
            //回复成功后增加回复数
            commentMapper.incReplyCount(comment.getParentId());
            //增加回复的通知
            Notification notification = new Notification();
            notification.setType(NotificationEnum.REPLY_COMMENT.getType());
            notification.setCommentId(comment.getParentId());//评论的问题id
            notification.setNotifier(comment.getCommentator());//通知人=评论人
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            //通过回复的评论id查出评论人
            Comment comment2=commentMapper.findComtorAndQuesIdByParentId(comment.getParentId());
            notification.setReceiver(comment2.getCommentator());
            notification.setQuestionId(comment2.getParentId());
            notification.setCommentId(comment.getParentId());
            notification.setContent(comment.getContent());
            notificationMapper.insertCommentNotice(notification);
        }

    }

    @Override
    public List<CommentDTO> listByQuestionId(Integer id) {
        /*通过问题id获取所有的评论*/
        List<Comment> comments = commentMapper.listByQuestionId(id);
        ArrayList<CommentDTO> listcommentDTOS = new ArrayList<>();
        for (Comment comment:comments){
            CommentDTO commentDTOS = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTOS);
            /*通过评论人id获取评论人信息*/
            User user=userMapper.findById(comment.getCommentator());
            commentDTOS.setUser(user);
            listcommentDTOS.add(commentDTOS);
        }
        return listcommentDTOS;
    }

    @Override
    public List<CommentDTO> listComments(Integer id) {
        List<Comment> comments = commentMapper.listComments(id);
        ArrayList<CommentDTO> listcommentDTOS = new ArrayList<>();

        for (Comment comment:comments){
            CommentDTO commentDTOS = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTOS);
            User user=userMapper.findById(comment.getCommentator());
            commentDTOS.setUser(user);
            listcommentDTOS.add(commentDTOS);
        }
        return listcommentDTOS;
    }
}
