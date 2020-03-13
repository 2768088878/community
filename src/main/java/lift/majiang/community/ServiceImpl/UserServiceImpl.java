package lift.majiang.community.ServiceImpl;

import lift.majiang.community.entity.User;
import lift.majiang.community.mapper.UserMapper;
import lift.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /*
     *登录
     */
    public User findByUser(User user){
        User dbUser=userMapper.findByUser(user.getName(),user.getPwd());
        return dbUser;
        }


    public String  findByEmail(String email){
        String e=userMapper.findByEmail(email);
        if (e==null){
            return "ok";
        }else {
            return "该邮箱已注册";
        }
    }

    public String findByName(String name){
        String n=userMapper.findByName(name);
        if (n==null){
            return "ok";
        }else {
            return "该账号已注册";
        }
    }
    /*
     *注册
     */
    public void register(User user){
        //先查找有没有已经注册的用户名和邮箱

        userMapper.insert(user);
    }
}
