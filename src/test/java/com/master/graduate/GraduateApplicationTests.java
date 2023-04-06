package com.master.graduate;

import com.master.graduate.customer.dao.UserDAO;
import com.master.graduate.customer.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
class GraduateApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserDAO userDAO;

    @Test
    void contextLoads() {
    }

    /*
    数据库连接测试
     */
    @Test
    public void druid() throws SQLException {
        System.out.println("dataSource.getClass() = " + dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);

        connection.close();
    }

    @Test
    public void testTimestamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(timestamp);
    }

    @Test
    public void testUserDAO() {
        List<Customer> all = userDAO.getAll();
        all.forEach(System.out::println);
    }

}
