package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作用户表的数据库访问对象
 *
 * @author Master_Joe lutong99
 * @since 2/20/2020 10:45 AM
 */
@Mapper
public interface UserDAO {

    /**
     * 数据库表名
     */
    String TABLE_NAME = "customers";

    /**
     * 数据库表对应的表项名
     */
    String FIELDS = "first_name, last_name, email, phone, birthday, company, country, city, password, registerTime, state, vip";

    /**
     * 查询名，用于封装对象使用
     */
    String Q_FIELDS = "id, first_name firstName, last_name lastName, email, phone, birthday, company, country, city, password, registerTime, state, vip, subscribe";

    /**
     * 状态查询条件
     */
    String STATE_Q = " and state = '1'";

    /**
     * 向数据库中插入一条数据
     */
    @Insert({"insert into ", TABLE_NAME, "( ", FIELDS, ") values(" +
            "#{firstName}, #{lastName}, #{email}, #{phone}, #{birthday}, #{company}, #{country}, #{city}, #{password}, #{registerTime}, #{state}, #{vip})"})
    void add(Customer customer);

    /**
     * 通过邮件和密码来获取一条数据
     *
     * @param email    邮箱
     * @param password 密码
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where email = #{email} and password = #{password}", STATE_Q})
    Customer getUser(String email, String password);

    /**
     * 通过邮箱获取一个用户
     *
     * @param email 邮箱
     * @return 一个用户对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where email = #{email}", STATE_Q})
    Customer getUserByEmail(String email);

    /**
     * @param id      用户id
     * @param newPass 新密码
     */
    @Update({"update ", TABLE_NAME, " set password = #{newPass} where id = #{id}", STATE_Q})
    void updatePass(Integer id, String newPass);

    /**
     * 更新用户，修改数据库信息
     * <p>
     * 要修改的信息有：
     * 1. 名
     * 2. 姓
     * 3. 手机
     * 4. 生日
     * 5. 公司
     * 6. 城市
     *
     * @param customer 要修改的用户信息
     */
    @Update({"update ", TABLE_NAME, " set last_name = #{lastName}, company = #{company}, first_name = #{firstName}, phone = #{phone}, city = #{city} where id = #{id}", STATE_Q})
    void updateUser(Customer customer);

    /**
     * 根据用户id查询用户
     *
     * @param id 指定的用户id
     * @return 一个用户对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where id = #{id}", STATE_Q})
    Customer getUserById(Integer id);

    /**
     * 查询所有有效用户
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where state = '1'"})
    List<Customer> getAll();

    /**
     * 查询所有普通客户
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where vip = '0'", STATE_Q})
    List<Customer> getAllNormal();

    /**
     * 查询所有VIP客户
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where vip = '1' ", STATE_Q})
    List<Customer> getAllVIP();

    /**
     * 删除用户，即把用户的状态设置为0
     */
    @Update({"update ", TABLE_NAME, " set state = '0' where id = #{id}"})
    void deleteById(Integer id);

    /**
     * 更新用户的几乎所有信息
     */
    @Update({"update ", TABLE_NAME, " set first_name = #{firstName}, last_name = #{lastName}, email = #{email}, phone = #{phone}, birthday = #{birthday}, company = #{company}, city = #{city}, vip = #{vip} where id = #{id} ", STATE_Q})
    void updateCustomer(Customer customer);

    /**
     * 获取信息的总数
     */
    @Select({"select count(*) from ", TABLE_NAME})
    Long getNumbers();
}
