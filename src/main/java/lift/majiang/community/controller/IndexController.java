package lift.majiang.community.controller;

import lift.majiang.community.entity.User;
import lift.majiang.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    public String Hello(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if ("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User user=indexService.findByToken(token);
                if (null!=user){
                    request.getSession().setAttribute("user", user);
                    break;
                }else {
                    JOptionPane.showConfirmDialog(null, "登录失败");
                }

            }
        }

        return "index";
    }

}
