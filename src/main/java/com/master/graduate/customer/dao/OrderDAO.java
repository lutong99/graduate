package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 数据库访问对象，操作Order(订单表的)
 *
 * @author Master_Joe lutong99
 * @since 2/26/2020 9:16 PM
 */
@Mapper
public interface OrderDAO {
    /**
     * 数据库表名
     */
    String TABLE_NAME = "orders";

    /**
     * 数据库表对应的表项名
     */
    String FIELDS = "(id, c_id, money, receiverAddress, receiverName, receiverPhone, paystate, ordertime, order_number, paymethod, expressmethod, post, extra, state)";

    /**
     * 查询名，用于封装对象使用
     */
    String Q_FIELDS = "id, c_id cId, money, receiverAddress, receiverName, receiverPhone, paystate, ordertime orderTime, order_number orderNumber, paymethod payMethod,expressmethod expressMethod, post, extra, state";

    /**
     * 查询状态。
     */
    String STATE_Q = " and state = '1'";

    /**
     * 将一条账单记录加入到数据库中。
     *
     * @param order 一条数据
     */
    @Insert({"insert into ", TABLE_NAME, FIELDS, " values(#{id}, #{cId}, #{money}, #{receiverAddress}, #{receiverName}, #{receiverPhone}, #{paystate}, #{orderTime}, #{orderNumber},  #{payMethod}, #{expressMethod}, #{post}, #{extra}, #{state})"})
    void saveOrders(Order order);

    /**
     * 根据用户的id获取所有的order项
     *
     * @param id 指定的用户id
     * @return order集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where c_id = #{id}", STATE_Q})
    List<Order> getOrders(Integer id);

    /**
     * 删除一条记录后，更新一下订单价格
     *
     * @param decrement 减少的金额
     * @param orderNum  指定订单的订单号
     */
    @Update({"update ", TABLE_NAME, " set money = money - #{decrement} where order_number = #{orderNum} ", STATE_Q})
    void updateMoney(double decrement, String orderNum);

    /**
     * 根据订单号获取订单对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where order_number = #{orderNum}"})
    Order getOrderByOrderNum(String orderNum);


    /**
     * 修改订单的信息
     */
    @Update({"update ", TABLE_NAME, " set money = #{money}, receiverAddress = #{receiverAddress}, receiverName = #{receiverName}, receiverPhone = #{receiverPhone}, paymethod = #{payMethod}, expressmethod = #{expressMethod}, post = #{post}, extra = #{extra}  where id = #{id}"})
    void updateOrder(Order order);

    /**
     * 获取所有的订单信息
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME})
    List<Order> getAll();

    /**
     * 通过id获得一个Order对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where id = #{id} and state <> '0'"})
    Order getOrderById(Integer id);
}
