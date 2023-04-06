package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作数据表项的dao
 *
 * @author Master_Joe lutong99
 * @since 2/27/2020 5:09 PM
 */
@Mapper
public interface OrderItemDAO {

    /**
     * 操作的数据库表名字
     */
    String TABLE_NAME = "orderitem";

    /**
     * 数据库表中的数据项
     */
    String FIELDS = "(c_id, p_id, cartNum, order_number)";

    /**
     * 数据库表中查询的项，目的是构成一个对象
     */
    String Q_FIELDS = "id, c_id cId, p_id pId, cartNum, order_number orderNum";

    /**
     * 将一条数据插入到数据库中
     */
    @Insert({"insert into ", TABLE_NAME, FIELDS, "values(#{cId}, #{pId}, #{cartNum}, #{orderNum})"})
    void save(OrderItem orderItem);

    /**
     * 根据订单号获取我们的所有订单
     *
     * @param orderNumber 指定的订单号
     * @return 指定订单号的订单项集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where order_number = #{orderNumber}"})
    List<OrderItem> getItemsByOrderNumber(String orderNumber);

    /**
     * 根据订单号和产品id删除一条记录
     */
    @Delete({"delete from ", TABLE_NAME, " where order_number = #{orderNum} and p_id = #{pid}"})
    void deleteOrderByPIdOrderNum(String orderNum, Integer pid);

    /**
     * 根据订单号和产品id获得一个订单项的结果
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where order_number = #{orderNum} and p_id = #{pid}"})
    OrderItem getOrderByPIdOrderNum(String orderNum, Integer pid);

    /**
     * 根据一个id来获取一个订单项
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    OrderItem getItemById(Integer id);

    /**
     * 更新一个订单项
     */
    @Update({"update ", TABLE_NAME, " set cartNum = #{cartNum} where id = #{id}"})
    void updateItem(OrderItem item);
}
