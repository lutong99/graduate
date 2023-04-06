package com.master.graduate.customer.entities;

import java.io.Serializable;

/**
 * 购物车的实体类
 *
 * @author Master_Joe lutong99
 * @since 2/24/2020 5:08 PM
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 7273987186663574646L;
    /**
     * 购物车项的id
     */
    private Integer id;

    /**
     * 购物车所属的用户id
     */
    private Integer cId;

    /**
     * 购物车项的产品id
     */
    private Integer pId;

    /**
     * 购买数量
     */
    private int cartNum;

    /**
     * 购物车的状态
     */
    private int state;

    public Cart() {
    }

    public Cart(Integer id, Integer cId, Integer pId, int cartNum) {
        this.id = id;
        this.cId = cId;
        this.pId = pId;
        this.cartNum = cartNum;
    }

    public Cart(Integer id, Integer cId, Integer pId, int cartNum, int state) {
        this.id = id;
        this.cId = cId;
        this.pId = pId;
        this.cartNum = cartNum;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cId=" + cId +
                ", pId=" + pId +
                ", cartNum=" + cartNum +
                ", state=" + state +
                '}';
    }
}
