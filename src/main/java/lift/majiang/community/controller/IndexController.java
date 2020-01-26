package lift.majiang.community.controller;

import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.service.IndexService;
import lift.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private PublishService publishService;

    @GetMapping("/")
    public String Hello(@RequestParam(name="page",defaultValue="1") Integer page,HttpServletRequest request, HttpSession session,Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0) {
            for(Cookie cookie:cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user=indexService.findByToken(token);
                    if (null!=user){
                        request.getSession().setAttribute("user", user);
                        System.out.println("登录的user="+user);
                        break;
                    }

                }
            }
        }



        //分页
        Integer totalCount=publishService.questionCount();
        Integer totalPage;
        if (totalCount%10==0){
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

        List<QuestionDTO> questionList= publishService.questionList(page);
        model.addAttribute("questionList", questionList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("nextPage", nextPage);
        return "index";
    }

    //退出登录
    @GetMapping("exitLogin")
    public String exitLogin(HttpSession session,HttpServletRequest request){
        session.invalidate();
        Cookie[] cookies = request.getCookies();

        return "redirect:/";
    }


}
