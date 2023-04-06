package com.master.graduate.customer.entities;

/**
 * 订单项的实体类
 *
 * @author Master_Joe lutong99
 * @since 2/27/2020 5:05 PM
 */
public class OrderItem {

    /**
     * 订单项的id
     */
    private Integer id;

    /**
     * 订单所有用户的id
     */
    private Integer cId;

    /**
     * 订单所有产品的订单
     */
    private Integer pId;

    /**
     * 产品数量
     */
    private int cartNum;

    /**
     * 订单号
     */
    private String orderNum;

    public OrderItem() {
    }

    public OrderItem(Integer id, Integer cId, Integer pId, int cartNum, String orderNum) {
        this.id = id;
        this.cId = cId;
        this.pId = pId;
        this.cartNum = cartNum;
        this.orderNum = orderNum;
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

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", cId=" + cId +
                ", pId=" + pId +
                ", cartNum=" + cartNum +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }
}
