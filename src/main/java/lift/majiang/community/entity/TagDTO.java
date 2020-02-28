package lift.majiang.community.entity;

import lombok.Data;

import java.util.List;
@Data
public class TagDTO {
    public String categoryName;//分类
    private List<String> tags;
}
