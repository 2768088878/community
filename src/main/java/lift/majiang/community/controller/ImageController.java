package lift.majiang.community.controller;

import lift.majiang.community.entity.ImageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
    @RequestMapping("/image/upload")
    @ResponseBody
    public ImageDTO upload(){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setSuccess(1);
        imageDTO.setUrl("/image/a.jpg");
        return imageDTO;
    }
}
