package lift.majiang.community.controller;

import com.alibaba.fastjson.JSONArray;
import lift.majiang.community.entity.Comment;
import lift.majiang.community.entity.CommentDTO;
import lift.majiang.community.entity.Notification;
import lift.majiang.community.entity.User;
import lift.majiang.community.enums.NotificationEnum;
import lift.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //回复评论
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody Comment comment, HttpServletRequest request,Model model){
        String result="";
        User user = (User) request.getSession().getAttribute("user");


        if (user==null){
            result="error";
        }else {
            Comment comment1 = new Comment();
            comment1.setCommentator(user.getId());
            comment1.setContent(comment.getContent());
            comment1.setParentId(comment.getParentId());
            comment1.setType(comment.getType());
            comment1.setLikeCount(0);
            commentService.insert(comment1,user.getId());

            result="ok";
        }

        return JSONArray.toJSONString(result);
    }

    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public List<CommentDTO> listComments(@PathVariable(name="id") Integer id, HttpServletRequest request, Model model){
        String result="";
        List<CommentDTO> replys = commentService.listComments(id);
        return replys;
    }





}
