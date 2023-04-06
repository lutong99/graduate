package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.WishList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作愿望清单的数据库访问对象
 *
 * @author Master_Joe lutong99
 * @since 2/28/2020 7:41 PM
 */
@Mapper
public interface WishListDAO {

    /**
     * 表名
     */
    String TABLE_NAME = "wishlist";

    /**
     * 表的column名字
     */
    String FIELDS = "id, p_id, c_id";

    /**
     * 查询条件，构成对象
     */
    String Q_FIELDS = "id, p_id pId, c_id cId";

    /**
     * 插入一条记录
     */
    @Insert({"insert into ", TABLE_NAME, "(c_id, p_id) values (#{cId}, #{pId})"})
    void insert(Integer cId, Integer pId);

    /**
     * 根据指定的用户id获取所有的商品id
     *
     * @param cId 指定的用户id
     * @return 所有的指定用户的商品id集合
     */
    @Select({"select p_id from ", TABLE_NAME, " where c_id = #{cId}"})
    List<Integer> getAllByCId(Integer cId);

    /**
     * 获取一条记录
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where c_id = #{cId} and p_id = #{pId}"})
    WishList getByPIdAndCId(Integer cId, Integer pId);


    /**
     * 删除一条指定的记录
     */
    @Delete({"delete from ", TABLE_NAME, " where c_id = #{cId} and p_id = #{pId}"})
    void deleteByCIdAndPId(Integer cId, Integer pId);

}
