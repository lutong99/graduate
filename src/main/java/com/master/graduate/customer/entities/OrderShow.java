package com.master.graduate.customer.entities;

import java.util.Map;

/**
 * 显示Order内容
 *
 * @author Master_Joe lutong99
 * @since 2/27/2020 5:45 PM
 */
public class OrderShow {

    /**
     * 显示订单和订单中的商品
     */
    private Map<Order, CustomerCart> orderMap;

    public OrderShow() {
    }

    public OrderShow(Map<Order, CustomerCart> orderMap) {
        this.orderMap = orderMap;
    }

    public Map<Order, CustomerCart> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Order, CustomerCart> orderMap) {
        this.orderMap = orderMap;
    }
}
