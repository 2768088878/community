package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private UserMapper userMapper;







}
