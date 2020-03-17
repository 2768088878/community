package lift.majiang.community.controller;

import lift.majiang.community.entity.User;
import lift.majiang.community.service.NotificationService;
import lift.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class loginAndRegisterController {

    @Autowired
    UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    /*
    登录
     */
    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8"); //转码
        User u = userService.findByUser(user);
        if (u != null) {
            /*通知数*/
            Integer noticeCount = notificationService.noticeCount(u.getId());
            request.getSession().setAttribute("noticeCount",noticeCount);
            session.setAttribute("user", u);
            return "redirect:/";
        } else {
            model.addAttribute("msg", "error");
            PrintWriter printWriter = response.getWriter();
            printWriter.flush();
            printWriter.println("<script>");
            printWriter.println("alert('登录失败！');");
            printWriter.println("history.back();");//这种不会刷新页面
            printWriter.println("history.go(0);");//这种会刷新页面
            printWriter.println("</script>");
            return null;
        }
    }

    /*
    注册
     */
    @PostMapping("/register")
    public String register (User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8"); //转码

        String emailFlag = userService.findByEmail(user.getEmail());
        String nameFlag = userService.findByName(user.getName());

        if (emailFlag.equals("ok")&&nameFlag.equals("ok")){
            userService.register(user);
            PrintWriter printWriter = response.getWriter();
            printWriter.flush();
            printWriter.println("<script>");
            printWriter.println("alert('注册成功！');");
            printWriter.println("history.back();");//这种不会刷新页面
            printWriter.println("history.go(0);");//这种会刷新页面
            printWriter.println("</script>");
            return null;
        }else {
            PrintWriter printWriter = response.getWriter();
            printWriter.flush();
            printWriter.println("<script>");
            printWriter.println("alert('邮箱或者用户名已存在！');");
            printWriter.println("history.back();");//这种不会刷新页面
            printWriter.println("history.go(0);");//这种会刷新页面
            printWriter.println("</script>");
            return null;
        }

    }


}
