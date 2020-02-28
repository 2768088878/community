package lift.majiang.community.ServiceImpl;

import lift.majiang.community.mapper.NotificationMapper;
import lift.majiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Integer noticeCount(Integer id) {
        Integer noticeCount = notificationMapper.noticeCount(id);
        return noticeCount;
    }

    /**/
    @Override
    public void updateStatus(Integer id) {
        notificationMapper.updateStatus(id);
    }
}
