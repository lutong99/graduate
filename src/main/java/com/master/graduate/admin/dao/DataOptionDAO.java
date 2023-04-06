package com.master.graduate.admin.dao;

import com.master.graduate.admin.entities.BackUpRecover;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据备份与还原的数据库（表）访问对象
 *
 * @author Master_Joe lutong99
 * @since 3/3/2020 10:12 PM
 */
@Mapper
public interface DataOptionDAO {
    /**
     * 数据表的名字
     */
    String TABLE_NAME = "dataOption";

    /**
     * 数据表的域
     */
    String FIELDS = "id, timeRecord, fileName, optionName";

    /**
     * 获取所有的备份与还原信息
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME})
    List<BackUpRecover> getAll();

    /**
     * 将数据插入到数据库中
     */
    @Insert({"insert into ", TABLE_NAME, "values (#{id}, #{timeRecord}, #{fileName}, #{optionName})"})
    void insertRecord(BackUpRecover backUpRecover);

    /**
     * 根据id获取一条数据
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    BackUpRecover getBackupById(Integer id);
}
