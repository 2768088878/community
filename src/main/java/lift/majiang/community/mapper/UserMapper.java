package lift.majiang.community.mapper;

import lift.majiang.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    public void select();

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    //当传入的值不是class对象的时候，在形参前需要加上@Param （两个参数以上必加）
    @Select("select * from user where token = #{token}")
    public User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    public User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id = #{accountId}")
    public User findByAccountId(String accountId);

    @Update("update user set name = #{name},token=#{token} ,gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where account_id = #{accountId}")
    public void update(User dbUser);

    //通过评论的问题id查出问题的发帖人ID
    @Select("select creator from question where id = #{id}")
    public Integer findUserByParentId(@Param("id") Integer id);


}
