package lift.majiang.community.entity;

import lombok.Data;

@Data
public class AccessEntity {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
