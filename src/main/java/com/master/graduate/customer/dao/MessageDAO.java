package com.master.graduate.customer.dao;

import com.master.graduate.customer.entities.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 留言处理，数据库访问对象
 *
 * @author Master_Joe lutong99
 * @since 3/4/2020 10:30 AM
 */
@Mapper
public interface MessageDAO {

    /**
     * 表名字
     */
    String TABLE_NAME = "message";

    /**
     * 表的所有列名
     */
    String FIELDS = "id, c_id, message, time, type, name, email, state";

    /**
     * 查询后的域名，构成一个对象
     */
    String Q_FIELDS = "id, c_id cId, message, time, type, name, email, state";

    /**
     * STATE_Q：查询条件正确的
     */
    String STATE_Q = " and state = '1'";

    /**
     * 获取消息条数
     */
    @Select({"select count(*) from ", TABLE_NAME})
    Long getNumbers();

    /**
     * 插入一条数据（一个对象）
     */
    @Insert({"insert into ", TABLE_NAME, " values (#{id}, #{cId}, #{message}, #{time}, #{type}, #{name}, #{email}, #{state})"})
    void insert(Message message);

    /**
     * 获取所有的message
     */
    @Select({"select ", Q_FIELDS, " from ", TABLE_NAME})
    List<Message> getMessages();

    /**
     * 删除一条数据：把状态设置为0
     */
    @Update({"update ", TABLE_NAME, " set state = '0' where id = #{id}"})
    void deleteMessage(Integer id);

}
