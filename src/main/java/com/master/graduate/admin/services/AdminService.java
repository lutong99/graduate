package com.master.graduate.admin.services;

import com.master.graduate.admin.dao.AdminDAO;
import com.master.graduate.admin.dao.DataOptionDAO;
import com.master.graduate.admin.entities.Admin;
import com.master.graduate.admin.entities.BackUpRecover;
import com.master.graduate.customer.dao.MessageDAO;
import com.master.graduate.customer.entities.Message;
import com.master.graduate.utils.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 管理员控制器
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 6:05 PM
 */
@Service
public class AdminService {

    /**
     * 管理员数据库管理
     */
    @Autowired
    AdminDAO adminDAO;

    /**
     * 数据库备份还原记录
     */
    @Autowired
    DataOptionDAO dataOptionDAO;

    /**
     * 数据库用户名
     */
    @Value("${spring.datasource.username}")
    String username;

    /**
     * 数据库密码
     */
    @Value("${spring.datasource.password}")
    String password;

    /**
     * 数据库url
     */
    @Value("${spring.datasource.url}")
    String url;

    /**
     * 处理用户留言的DAO
     */
    @Autowired
    MessageDAO messageDAO;

    /**
     * 验证用户是否正确
     * <p>
     * 1. 获取管理员用户的两个属性
     * 2. 将密码加密后进行数据查询
     */
    public Admin verify(Admin admin) {
        String password = admin.getPassword();
        password = MD5Generator.encrypByMD5(password);
        String username = admin.getUsername();
        return adminDAO.getAdminByUsernameAndPassword(username, password);
    }

    /**
     * 管理员密码的修改
     * <p>
     * 1. 获取要修改的管理员id
     * 2. 根据id更新数据库内容
     * 3. 返回修改后的admin对象
     */
    public Admin updateInfo(Admin admin, String newpass) {
        Integer id = admin.getId();
        newpass = MD5Generator.encrypByMD5(newpass);
        adminDAO.updatePassById(id, newpass);
        return adminDAO.getAdminById(id);
    }

    /**
     * 获得所有的备份
     */
    public List<BackUpRecover> getAllBackUps() {
        return dataOptionDAO.getAll();
    }

    /**
     * 对数据库进行备份
     * <p>
     * 1. 编写备份数据库的代码，将备份文件放入合适的位置
     * 2. 将相应的数据存入到数据库
     */
    public void backup() {
        // 备份操作
        String database = null;
        if (url.contains("?")) {
            database = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
            database = url.substring(url.lastIndexOf("/") + 1);
        }
        File parent = new File("f:/resources/backup");
        String backupFileName = UUID.randomUUID() + ".sql";
        String path = new File(parent, backupFileName).getAbsolutePath();
        String backup = "mysqldump -h127.0.0.1 -p3306 -u" + username + " -p" + password + " " + database + " > " + path;
        String[] cmd = {"cmd", "/C", backup};
        try {
            Process runtime = Runtime.getRuntime().exec(cmd);
            System.out.println(runtime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 数据库操作
        Timestamp timestamp = new Timestamp(new Date().getTime());
        BackUpRecover backUpRecover = new BackUpRecover(null, timestamp, backupFileName, "备份");
        dataOptionDAO.insertRecord(backUpRecover);
    }


    /**
     * 还原数据
     * <p>
     * 1. 根据id获取文件
     * 2. 找到绝对路径
     * 3. 根据绝对全路径执行还原操作
     * 4. 插入数据
     */
    public void recover(Integer id) {
        String database = null;
        if (url.contains("?")) {
            database = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
            database = url.substring(url.lastIndexOf("/") + 1);
        }
        BackUpRecover backup = dataOptionDAO.getBackupById(id);
        if (backup != null) {
            String parent = "f:/resources/backup";
            String fileName = backup.getFileName();
            String absolutePath = new File(parent, fileName).getAbsolutePath();
            String recover = "mysql -h127.0.0.1 -u" + username + " -p" + password + " " + database + " < " + absolutePath;

            String[] cmd = {"cmd", "/C", recover};
            try {
                Process runtime = Runtime.getRuntime().exec(cmd);
                System.out.println(runtime);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BackUpRecover recover1 = new BackUpRecover(null, new Timestamp(new Date().getTime()), fileName, "还原");
            dataOptionDAO.insertRecord(recover1);
        }
    }

    /**
     * 获取所有的状态下的message
     */
    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }

    /**
     * 根据id删除一条记录
     */
    public void deleteMessage(Integer id) {
        messageDAO.deleteMessage(id);
    }

    /**
     * 共售出多少钱
     */
    public String getAllMoney() {
        return null;
    }

    /**
     * 获得消息条数
     */
    public String getAllMessages() {
        Long numbers = messageDAO.getNumbers();
        return String.valueOf(numbers);
    }

}
