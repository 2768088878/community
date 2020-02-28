package lift.majiang.community.controller;

import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.CommentDTO;
import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.exception.CustomizeException;
import lift.majiang.community.mapper.NotificationMapper;
import lift.majiang.community.mapper.QuestionMapper;
import lift.majiang.community.service.CommentService;
import lift.majiang.community.service.NotificationService;
import lift.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;


    @GetMapping("question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){

        //累加阅读数
        questionService.incView(id);
        /*根据问题id查询问题*/
        QuestionDTO questionDTO=questionService.getByQueId(id);
        if (questionDTO==null){
            throw new CustomizeException("该帖子已丢失！");
        }
        /*根据问题id查询该问题所有的评论*/
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //根据问题标签模糊查询问题列
        List<Question> RelateQuestions = questionService.listlikeQuestions(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestions",RelateQuestions);
        return "question";
    }

    @GetMapping("notification/{id}")
    public String notification(@PathVariable("id") Integer id,
                               @RequestParam(name = "status",required = false)  Integer status,
                               @RequestParam(name = "replyId",required = false) Integer replyId, Model model){

        if (status==0){
            notificationService.updateStatus(replyId);
        }
        //累加阅读数
        questionService.incView(id);
        /*根据问题id查询问题*/
        QuestionDTO questionDTO=questionService.getByQueId(id);
        /*根据问题id查询该问题所有的评论*/
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //根据问题标签模糊查询问题列
        List<Question> RelateQuestions = questionService.listlikeQuestions(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestions",RelateQuestions);
        return "question";
    }
}
