package lift.majiang.community.service;

import lift.majiang.community.entity.User;

public interface UserService {

    public User findByUser(User user);

    public String  findByEmail(String email);

    public String findByName(String name);

    public void register(User user);
}
