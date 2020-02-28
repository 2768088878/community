package lift.majiang.community.cache;


import lift.majiang.community.entity.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TagCache {
    /*查询出所有tag*/
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","html","node","css","python","shell","jquery","perl","ruby","html5","scala","golang","typescript","c++","dess"));
        tagDTOs.add(program);

        TagDTO framework=new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","struts"));
        tagDTOs.add(framework);

        return tagDTOs;
    }
}
