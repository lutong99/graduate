package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.Kind;
import com.master.graduate.customer.entities.Products;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作Products（产品）数据表的DAO数据访问对象
 *
 * @author Master_Joe lutong99
 * @since 2/20/2020 5:48 PM
 */
@Mapper
public interface ProductsDAO {

    /**
     * 数据库表名
     */
    String TABLE_NAME = "products";

    /**
     * 数据库表对应的表项名
     */
    String FIELDS = "(p_description, p_category, p_name, p_price, p_showtime, p_venue, p_path, p_kind, store, state)";

    /**
     * 查询名，用于封装对象使用
     */
    String Q_FIELDS = "p_id id, p_description pDescription, p_category pCategory, p_name pName, p_price pPrice, p_showtime pShowTime, p_venue pVenue, p_path pPath, p_kind pKind, store, state";

    /**
     * 状态查询条件
     */
    String STATE_Q = " and state <> '0'";

    /**
     * @return 返回所有商品的详细信息
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where state <> '0'"})
    List<Products> getAll();

    /**
     * @return 所有的kind集合
     */
    @Select({"select k_name kName, k_id kID from kinds"})
    List<Kind> getKinds();

    /**
     * @return 最贵的所有商品
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where state <> '0' order by p_price desc limit 4"})
    List<Products> getExpensive();

    /**
     * @param kind 种类id
     * @return 指定种类的产品集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where p_kind = #{kind}", STATE_Q})
    List<Products> getProductsByKind(String kind);

    /**
     * 分页获取产品数据
     *
     * @param kind     数据库查询的种类号
     * @param startNo  开始的位置
     * @param pageSize 每一页的多少
     * @return 一个产品集合
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, "where p_kind = #{kind} limit #{startNo}, #{pageSize}", STATE_Q})
    List<Products> getProductsByPageNumber(String kind, int startNo, int pageSize);

    /**
     * 获取指定产品种类的数量
     *
     * @param kind 指定的种类
     * @return 指定产品种类的数量
     */
    @Select({"select count(*) from ", TABLE_NAME, " where p_kind = #{kind} ", STATE_Q})
    int getProductsNumberByKind(String kind);

    /**
     * @param id 指定id的产品
     * @return 一个产品
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where p_id = #{id} "})
    Products getProductsById(int id);

    /**
     * 无论什么状态都查询到
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME})
    List<Products> getAllProducts();

    /**
     * 通过id来删除一个商品
     * <p>
     * 1. 将指定id的商品状态设置为0
     */
    @Update({"update ", TABLE_NAME, " set state = '0' where p_id = #{id} ", STATE_Q})
    void deleteById(Integer id);

    /**
     * 通过属性设置我们的产品
     */
    @Update({"update ", TABLE_NAME, " set p_description = #{pDescription}, p_category = #{pCategory}, p_name = #{pName}, p_price = #{pPrice}, p_showtime = #{pShowTime}, p_venue = #{pVenue}, p_path = #{pPath}, p_kind = #{pKind}, store = #{store}, state = #{state}  where p_id = #{id}"})
    void updateProduct(Products product);

    /**
     * 根据Kind对象来插入一条数据
     */
    @Insert({"insert into kinds(k_id, k_name) values(#{kId}, #{kName})"})
    void insertKind(Kind newKind);

    /**
     * 获取热门演出
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where state = '6'"})
    List<Products> getHot();

    /**
     * 获取最新演出
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, "where state = '5'"})
    List<Products> getLatest();

    /**
     * 获取所有的普通产品
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where state = '1'"})
    List<Products> getAllNormal();

    /**
     * 获取商品的记录数
     */
    @Select({"select count(*) from ", TABLE_NAME})
    Long getNumber();

    @Insert({"insert into ", TABLE_NAME, FIELDS, " values(#{pDescription}, #{pCategory}, #{pName}, #{pPrice}, #{pShowTime}, #{pVenue}, #{pPath}, #{pKind}, #{store}, #{state})"})
    void saveProduct(Products temp);
}
