package com.master.graduate.admin.services;

import com.master.graduate.customer.dao.UserDAO;
import com.master.graduate.customer.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 管理员处理用户相关的服务
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 5:06 PM
 */
@Service
public class AdminCustomerService {

    /**
     * 用户表数据库（表）访问对象
     */
    @Autowired
    UserDAO customerDAO;

    /**
     * 从数据库查到数据并返回给控制层
     *
     * @return 一个用户集合
     */
    public List<Customer> getCustomers() {
        return customerDAO.getAll();
    }

    /**
     * 删除用户
     * <p>
     * 1. 把我们的用户删除，然后再返回查询结果，查询如果返回null，就说明了删除成功
     */
    public Customer deleteCustomerById(Integer id) {
        customerDAO.deleteById(id);
        return customerDAO.getUserById(id);
    }

    /**
     * 获取一个用户返回
     */
    public Customer getCustomerById(Integer id) {
        return customerDAO.getUserById(id);
    }

    /**
     * 更新用户信息
     * <p>
     * 1. 获取要修改的用户id
     * 2. 根据用户id从数据库中获取一个用户
     * 3. 设置相应属性，然后放回到数据库中
     * 3.1 相应属性：姓名，邮箱，手机号码，生日，公司，城市，VIP
     * 4. 将修改好的用户返回
     */
    public Customer updateCustomer(Customer temp) {
        Integer id = temp.getId();
        if (!"0".equals(temp.getVip()) && !"1".equals(temp.getVip())) {
            temp.setVip("1");
        }
        if ("".equals(temp.getVip().trim())) {
            temp.setVip("0");
        }
        Customer customer = customerDAO.getUserById(id);
        /*if (!customer.getFirstName().equalsIgnoreCase(temp.getFirstName())) {
            customer.setFirstName(temp.getFirstName());
        }
        if (!customer.getLastName().equalsIgnoreCase(temp.getLastName())) {
            customer.setLastName(temp.getLastName());
        }

        if (!customer.getEmail().equalsIgnoreCase(temp.getEmail())) {
            customer.setEmail(temp.getEmail());
        }

        if (!customer.getPhone().equalsIgnoreCase(temp.getPhone())) {
            customer.setPhone(temp.getPhone());
        }

        if (!customer.getBirthday().equals(temp.getBirthday())) {
            customer.setBirthday(temp.getBirthday());
        }

        if (!customer.getCompany().equalsIgnoreCase(temp.getCompany())) {
            customer.setCompany(temp.getCompany());
        }

        if (!customer.getCity().equalsIgnoreCase(temp.getCity())) {
            customer.setCity(temp.getCity());
        }

        if (customer.getVip().equalsIgnoreCase(temp.getVip())) {
            customer.setVip(temp.getVip());
        }*/

        Class<Customer> cust = Customer.class;
        Field[] fields = cust.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object original = field.get(customer);
                Object present = field.get(temp);
                if (present != null) {
                    if (!original.equals(present)) {
                        field.set(customer, field.get(temp));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        customerDAO.updateCustomer(customer);
        return customerDAO.getUserById(customer.getId());
    }

    /**
     * 从数据库获得所有的普通用户
     */
    public List<Customer> getNormalCustomers() {
        return customerDAO.getAllNormal();
    }

    /**
     * 从数据库获得所有的VIP用户并返回
     */
    public List<Customer> getVIPCustomers() {
        return customerDAO.getAllVIP();
    }

    /**
     * 获得所有用户数目
     */
    public String getAllUsers() {
        Long numbers = customerDAO.getNumbers();
        return String.valueOf(numbers);
    }

}