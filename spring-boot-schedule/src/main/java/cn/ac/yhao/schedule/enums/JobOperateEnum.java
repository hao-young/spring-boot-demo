package cn.ac.yhao.schedule.enums;

/**
 * 定时任务操作枚举
 */
public enum JobOperateEnum {
    START("START"),
    PAUSE("PAUSE"),
    DELETE("DELETE");

    public final String value;
    private JobOperateEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
