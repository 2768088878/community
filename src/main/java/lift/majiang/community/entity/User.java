package lift.majiang.community.entity;

import com.sun.jmx.snmp.Timestamp;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String pwd;
    private Timestamp gmtCreate;
    private String avatarUrl;

}
