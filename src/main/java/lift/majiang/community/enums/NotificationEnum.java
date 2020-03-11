package lift.majiang.community.enums;

public enum NotificationEnum {

    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了你");


    private int type1;
    private String name;

    public int getType() {
        return type1;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int type, String name) {
        this.type1 = type;
        this.name = name;
    }

}
