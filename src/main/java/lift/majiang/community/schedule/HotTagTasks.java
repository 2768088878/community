package lift.majiang.community.schedule;

import lift.majiang.community.entity.Question;
import lift.majiang.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Scheduled(fixedRate = 60*60*24)//五秒
    public void reportCurrentTime() {

        questionMapper.flushTemp();

    }
}
