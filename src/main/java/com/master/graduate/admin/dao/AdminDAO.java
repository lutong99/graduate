package com.master.graduate.admin.dao;

import com.master.graduate.admin.entities.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 操作管理员用户的用户表的数据库访问对象
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 5:59 PM
 */
@Mapper
public interface AdminDAO {

    /**
     * 表名字
     */
    String TABLE_NAME = "admin";

    /**
     * 表的所有的列名
     */
    String FIELDS = "(id, username, password)";

    /**
     * 为了返回是一个对香而设计的
     */
    String Q_FIELDS = "id, username, password";

    /**
     * 由指定的ID获取Admin对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    Admin getAdminById(Integer id);

    /**
     * 由用户名和密码获取一个管理员对象
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME, " where username = #{username} and password = #{password}"})
    Admin getAdminByUsernameAndPassword(String username, String password);

    /**
     * 更新密码信息
     */
    @Update({"update ", TABLE_NAME, "set password = #{newpass} where id = #{id}"})
    void updatePassById(Integer id, String newpass);
}
