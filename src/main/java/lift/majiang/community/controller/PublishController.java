package lift.majiang.community.controller;

import lift.majiang.community.cache.TagCache;
import lift.majiang.community.entity.Question;
import lift.majiang.community.entity.QuestionDTO;
import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.PublishMapper;
import lift.majiang.community.service.IndexService;
import lift.majiang.community.service.PublishService;
import lift.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

@Controller
public class PublishController {

    @Autowired
    private PublishService publishService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        User user= (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("msg", "用户未登录");
            return "publish";
        }else if (StringUtils.isBlank(description)){
            model.addAttribute("msg", "内容不能为空");
            return "publish";
        }else if (StringUtils.isBlank(title)){
            model.addAttribute("msg", "标题不能为空");
            return "publish";
        }else if (StringUtils.isBlank(tag)){
            model.addAttribute("msg", "标签不能为空");
            return "publish";
        }else {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
//            question.setGmtCreate(System.currentTimeMillis());
//            question.setGmtModified(question.getGmtCreate());
            question.setId(id);
            publishService.createOrUpdate(question);
        }
        model.addAttribute("tags", TagCache.get());
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        QuestionDTO questionDTO = questionService.getByQueId(id);

        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
