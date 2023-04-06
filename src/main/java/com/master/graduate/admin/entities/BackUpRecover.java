package com.master.graduate.admin.entities;

import java.sql.Timestamp;

/**
 * 备份与还原的实体类
 *
 * @author Master_Joe lutong99
 * @since 3/3/2020 10:08 PM
 */
public class BackUpRecover {

    /**
     * id
     */
    private Integer id;

    /**
     * 操作的时间
     */
    private Timestamp timeRecord;

    /**
     * 操作后的文件名
     */
    private String fileName;

    /**
     * 操作名
     */
    private String optionName;

    public BackUpRecover() {
    }

    public BackUpRecover(Integer id, Timestamp timeRecord, String fileName, String optionName) {
        this.id = id;
        this.timeRecord = timeRecord;
        this.fileName = fileName;
        this.optionName = optionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(Timestamp timeRecord) {
        this.timeRecord = timeRecord;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public String toString() {
        return "BackUpRecover{" +
                "id=" + id +
                ", timeRecord=" + timeRecord +
                ", fileName='" + fileName + '\'' +
                ", optionName='" + optionName + '\'' +
                '}';
    }
}
