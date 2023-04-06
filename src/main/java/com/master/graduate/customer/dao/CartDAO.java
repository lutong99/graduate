package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.Cart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 查询数据库中购物车项的DAO
 *
 * @author Master_Joe lutong99
 * @since 2/24/2020 5:10 PM
 */
@Mapper
public interface CartDAO {

    /**
     * 数据库表名
     */
    String TABLE_NAME = "cart";

    /**
     * 数据库表对应的表项名
     */
    String FIELDS = "(id, c_id, p_id, cartNum)";

    /**
     * 查询名，用于封装对象使用
     */
    String Q_FIELDS = "id, c_id cId, p_id pId, cartNum";

    /**
     * 根据用户和商品获取用户的购物车信息
     *
     * @param cId 用户id
     * @param pId 商品id
     * @return 一个购物车对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where c_id = #{cId} and p_id = #{pId}"})
    Cart getCartByCIdPId(Integer cId, int pId);


    /**
     * 将购物车的数据放在购物车中
     * 购物车对象cart = new Cart(id, cId, pId, cartNum);
     */
    @Insert({"insert into ", TABLE_NAME, FIELDS, " values(#{id}, #{cId}, #{pId}, #{cartNum})"})
    void saveCart(Cart cart);

    /**
     * 更新购物车数据
     *
     * @param cartNum 购物车对象的数量
     * @param id      购物车项的id
     */
    @Update({"update ", TABLE_NAME, " set cartNum = #{cartNum} where id = #{id}"})
    void updateCart(int cartNum, int id);

    /**
     * 获取指定id用户的有效Cart列表
     *
     * @param cId 指定用户的用户id
     * @return 一个购物车表项集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where c_id = #{cId} and state = '1'"})
    List<Cart> getCartByCId(Integer cId);

    /**
     * 获取指定用户id的Cart列表
     *
     * @param cId 指定用户的用户id
     * @return 一个购物车表项集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where c_id = #{cId}"})
    List<Cart> getAllCartByCId(Integer cId);

    /**
     * 根据cId，pId删除购物车记录
     *
     * @param cId 用户id
     * @param pId 产品id
     */
    @Select({"delete from ", TABLE_NAME, " where c_id = #{cId} and p_id = #{pId}"})
    void deleteProduct(Integer cId, Integer pId);

    /**
     * 提交订单后, 将所有指定用户的购物车项状态设置为0
     *
     * @param cId 指定用户的用户id
     */
    @Select({"delete from ", TABLE_NAME, " where c_id = #{cId}"})
    void submit(Integer cId);
}
