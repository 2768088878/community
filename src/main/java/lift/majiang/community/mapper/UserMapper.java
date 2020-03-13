package lift.majiang.community.mapper;

import lift.majiang.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    public void select();

    @Insert("insert into user (name,pwd,email) values (#{name},#{pwd},#{email})")
    public void insert(User user);

    @Select("select * from user where id = #{id}")
    public User findById(@Param("id") Integer creator);

    @Select("select email from user where email = #{email}")
    public String findByEmail(@Param("email") String email);

    @Select("select name from user where name = #{name}")
    public String findByName(@Param("name") String name);

    //当传入的值不是class对象的时候，在形参前需要加上@Param （两个参数以上必加）
    @Select("select * from user where name = #{name} and pwd = #{pwd}" )
    public User findByUser(@Param("name") String name,@Param("pwd") String pwd);



    @Select("select * from user where account_id = #{accountId}")
    public User findByAccountId(String accountId);

    @Update("update user set name = #{name},token=#{token} ,gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where account_id = #{accountId}")
    public void update(User dbUser);

    //通过评论的问题id查出问题的发帖人ID
    @Select("select creator from question where id = #{id}")
    public Integer findUserByParentId(@Param("id") Integer id);


}
