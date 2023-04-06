package com.master.graduate.customer.entities;

import java.sql.Timestamp;

/**
 * 订单的实体类
 *
 * @author Master_Joe lutong99
 * @since 2/26/2020 5:52 PM
 */
public class Order {

    /**
     * 订单id
     */
    private Integer id;

    /**
     * 下订单的用户
     */
    private Integer cId;

    /**
     * 订单的金额
     */
    private double money;

    /**
     * 收件人地址
     */
    private String receiverAddress;

    /**
     * 收件人姓名
     */
    private String receiverName;

    /**
     * 收件人电话
     */
    private String receiverPhone;

    /**
     * 付款状态
     */
    private char paystate = '0';

    /**
     * 付款时间
     */
    private Timestamp orderTime;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 付款方式
     */
    private String payMethod;

    /**
     * 快递方式
     */
    private String expressMethod;

    /**
     * 邮政编码
     */
    private String post;

    /**
     * 备注
     */
    private String extra;


    /**
     * 订单状态
     */
    private String state;


    public Order() {
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public char getPaystate() {
        return paystate;
    }

    public void setPaystate(char paystate) {
        this.paystate = paystate;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getExpressMethod() {
        return expressMethod;
    }

    public void setExpressMethod(String expressMethod) {
        this.expressMethod = expressMethod;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cId=" + cId +
                ", money=" + money +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", paystate=" + paystate +
                ", orderTime=" + orderTime +
                ", orderNumber='" + orderNumber + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", expressMethod='" + expressMethod + '\'' +
                ", post='" + post + '\'' +
                ", extra='" + extra + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
