package com.master.graduate.customer.entities;

import java.sql.Timestamp;

/**
 * 留言消息的实体类
 *
 * @author Master_Joe lutong99
 * @since 3/4/2020 8:12 AM
 */
public class Message {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer cId;

    /**
     * 留言内容
     */
    private String message;

    /**
     * 留言时间
     */
    private Timestamp time;

    /**
     * 留言类型
     */
    private String type;

    /**
     * 留言人的姓名
     */
    private String name;

    /**
     * 留言人的邮箱
     */
    private String email;

    /**
     * 留言状态
     */
    private String state = "1";

    public Message() {
    }

    public Message(Integer id, Integer cId, String message, Timestamp time, String type, String name, String email, String state) {
        this.id = id;
        this.cId = cId;
        this.message = message;
        this.time = time;
        this.type = type;
        this.name = name;
        this.email = email;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", cId=" + cId +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
