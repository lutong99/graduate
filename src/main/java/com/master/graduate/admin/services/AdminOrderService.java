package com.master.graduate.admin.services;

import com.master.graduate.customer.dao.OrderDAO;
import com.master.graduate.customer.dao.OrderItemDAO;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * 处理管理订单相关的服务
 *
 * @author Master_Joe lutong99
 * @since 3/4/2020 2:36 PM
 */
@Service
public class AdminOrderService {

    /**
     * 处理订单的DAO
     */
    @Autowired
    OrderDAO orderDAO;
    /**
     * 处理订单项的数据访问对象
     */
    @Autowired
    OrderItemDAO orderItemDAO;

    /**
     * 根据id获取一个Order
     */
    public Order getOrder(Integer id) {
        return orderDAO.getOrderById(id);
    }

    /**
     * 更新订单信息
     * <p>
     * 1. 根据id获取一个订单
     */
    public void updateOrder(Order temp, Integer id) {
        Order order = orderDAO.getOrderById(id);
        Class<Order> oClass = Order.class;
        Field[] fields = oClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object present = field.get(temp);
                Object original = field.get(order);
                if (original != null && present != null) {
                    if (!present.equals(original)) {
                        field.set(order, present);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        orderDAO.updateOrder(order);

    }

    /**
     * 获取指定订单号的items
     */
    public List<OrderItem> getItems(String orderNumber) {
        return orderItemDAO.getItemsByOrderNumber(orderNumber);
    }

    /**
     * 通过id获取一个orderItem对象
     */
    public OrderItem getItemById(Integer id) {
        return orderItemDAO.getItemById(id);
    }

    /**
     * 更新一条数据
     */
    public void updateItem(OrderItem orderItem) {
        orderItemDAO.updateItem(orderItem);
    }

    /**
     * 获取到我们的所有订单的获取的钱
     * <p>
     * 1. 获取所有的订单
     * 2. 获取价格求和
     */
    public String getAllMoney() {
        Optional<Double> reduce = orderDAO.getAll().stream().map(Order::getMoney).reduce(Double::sum);
        return String.valueOf(reduce.get());
    }
}
