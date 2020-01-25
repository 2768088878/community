package lift.majiang.community.controller;

import lift.majiang.community.entity.AccessEntity;
import lift.majiang.community.entity.GithubUser;
import lift.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

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
                           HttpServletRequest request) throws IOException {

        AccessEntity accessEntity = new AccessEntity();
        accessEntity.setClient_id(clientId);
        accessEntity.setClient_secret(clientSecret);
        accessEntity.setCode(code);
        accessEntity.setRedirect_uri(uri);
        accessEntity.setState(state);
        String accessToken = githubProvider.getAccess(accessEntity);
        GithubUser user = githubProvider.getUser(accessToken);

        if (null!=user){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }

    }
}
