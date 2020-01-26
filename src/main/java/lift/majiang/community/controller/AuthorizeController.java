package lift.majiang.community.controller;

import lift.majiang.community.entity.AccessEntity;
import lift.majiang.community.entity.GithubUser;
import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    //去配置文件读这个key的值的value，把他赋值到clientId
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String uri;
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        AccessEntity accessEntity = new AccessEntity();
        accessEntity.setClient_id(clientId);
        accessEntity.setClient_secret(clientSecret);
        accessEntity.setCode(code);
        accessEntity.setRedirect_uri(uri);
        accessEntity.setState(state);
        String accessToken = githubProvider.getAccess(accessEntity);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (null!=githubUser){
            User user=new User();
            //设置token 为uuid随机码 防止重复，相当于唯一标识(id),防止他重复
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            //把long类型转成string类型
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }

    }
}
