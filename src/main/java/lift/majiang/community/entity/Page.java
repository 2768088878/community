package lift.majiang.community.entity;

import lift.majiang.community.service.PublishService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Page {

    public void setPage(Integer totalCount,Integer page,Integer size){
        //最后一页
        Integer totalPage;
        if (totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount /size+1;
        }


    }


}
