package lift.majiang.community.controller;

import lift.majiang.community.entity.NotificationDTO;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.exception.CustomizeException;
import lift.majiang.community.service.CommentService;
import lift.majiang.community.service.NotificationService;
import lift.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private PublishService publishService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public  String profile(@PathVariable(name="action") String action,
                           @RequestParam(name="page",defaultValue="1") Integer page
                            , Model model, HttpServletRequest request){


        User user = (User) request.getSession().getAttribute("user");
        if(null==user){
            throw new CustomizeException("请先登录！");
        }


        /*通知数*/
        Integer noticeCount = notificationService.noticeCount(user.getId());
        request.getSession().setAttribute("noticeCount",noticeCount);

        if ("questions".equals(action)){

            //分页
            Integer totalCount=publishService.questionsCountById(user.getId());
            Integer totalPage;
            if (totalCount%5==0){
                totalPage = totalCount/5;
            }else {
                totalPage = totalCount /5+1;
            }
            //上一页
            Integer lastPage;
            if (page==1){
                lastPage=1;
            }else {
                lastPage=page-1;
            }
            //下一页
            Integer nextPage;
            if (page==totalPage){
                nextPage=totalPage;
            }else {
                nextPage=page+1;
            }

            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");

            System.out.println("总页数："+totalPage);
            List<QuestionDTO> questionList= publishService.questionById(user.getId(), page);
            model.addAttribute("questionList", questionList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("nextPage", nextPage);
            return "profile-question";

        }else if ("replies".equals(action)){

            //分页
            Integer totalCount=noticeCount;
            Integer totalPage;
            if (totalCount%5==0){
                totalPage = totalCount/5;
            }else {
                totalPage = totalCount /5+1;
            }
            //上一页
            Integer lastPage;
            if (page==1){
                lastPage=1;
            }else {
                lastPage=page-1;
            }
            //下一页
            Integer nextPage;
            if (page==totalPage){
                nextPage=totalPage;
            }else {
                nextPage=page+1;
            }

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的提问");

            System.out.println("总页数："+totalPage);
            List<NotificationDTO> repliesList= publishService.repliesById(user.getId(), page);
            model.addAttribute("repliesList", repliesList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("nextPage", nextPage);

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            return "profile-reply";
        }else {
            return "profile-question";
        }

    }
}
