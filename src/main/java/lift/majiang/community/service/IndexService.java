package lift.majiang.community.service;

import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface IndexService {

    public User findByToken(String token);


}
