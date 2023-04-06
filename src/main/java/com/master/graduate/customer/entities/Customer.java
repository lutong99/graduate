package com.master.graduate.customer.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 用户的实体类
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 5701056054968061461L;
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 名字
     */
    private String firstName;
    /**
     * 姓
     */
    private String lastName;
    /**
     * 电子邮件地址
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 公司
     */
    private String company;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 注册时间.
     */
    private Timestamp registerTime;
    /**
     * 用户生日
     */
    private Date birthday;
    /**
     * 用户状态: <br>
     * <code>1</code>为有效用户 <br>
     * <code>0</code>为无效用户
     */
    private String state;
    /**
     * 用户居住城市
     */
    private String city;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 是否会员
     */
    private String vip;
    /**
     * 用户是否订阅
     */
    private char subscribe;

    /**
     * 用户的购物车
     */
    private CustomerCart customerCart;

    /**
     * 展示订单用的，里面封装了一个map
     */
    private OrderShow orderShow;

    public Customer() {
    }

    public Customer(Integer id, String firstName, String lastName, String email, String phone, String company, String password, Timestamp registerTime, Date birthday, String state, String city, String country, String vip, char subscribe, CustomerCart customerCart, OrderShow orderShow) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.password = password;
        this.registerTime = registerTime;
        this.birthday = birthday;
        this.state = state;
        this.city = city;
        this.country = country;
        this.vip = vip;
        this.subscribe = subscribe;
        this.customerCart = customerCart;
        this.orderShow = orderShow;
    }

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public char getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(char subscribe) {
        this.subscribe = subscribe;
    }

    public CustomerCart getCustomerCart() {
        return customerCart;
    }

    public void setCustomerCart(CustomerCart customerCart) {
        this.customerCart = customerCart;
    }

    public OrderShow getOrderShow() {
        return orderShow;
    }

    public void setOrderShow(OrderShow orderShow) {
        this.orderShow = orderShow;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", birthday=" + birthday +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", vip='" + vip + '\'' +
                ", subscribe=" + subscribe +
                ", customerCart=" + customerCart +
                ", orderShow=" + orderShow +
                '}';
    }
}
