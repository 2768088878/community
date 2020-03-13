package lift.majiang.community.controller;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.exception.CustomizeException;
import lift.majiang.community.mapper.QuestionMapper;
import lift.majiang.community.service.IndexService;
import lift.majiang.community.service.NotificationService;
import lift.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private PublishService publishService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String Hello(@RequestParam(name="page",defaultValue="1") Integer page,
                        @RequestParam(name="search",required = false) String search,
                        HttpServletRequest request,
                        HttpSession session,Model model) throws IOException {

        User user= (User) session.getAttribute("user");
        if (null!=user){
            /*通知数*/
            Integer noticeCount = notificationService.noticeCount(user.getId());
            request.getSession().setAttribute("noticeCount",noticeCount);
        }
        //分页
        Integer totalCount=publishService.questionCount();
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

        List<QuestionDTO> questionList= publishService.questionList(search,page);

        /*热门话题*/
        List<Question> hotQuestions = questionMapper.hotQuestions();


        model.addAttribute("hotQuestions",hotQuestions);
        model.addAttribute("questionList", questionList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("nextPage", nextPage);
//        if (questionList.size()==0){
//            throw new CustomizeException("没有该帖子");
//        }
        return "index";
    }

    //退出登录
    @GetMapping("exitLogin")
    public String exitLogin(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }

}
