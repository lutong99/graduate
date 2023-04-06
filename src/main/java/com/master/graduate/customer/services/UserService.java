package com.master.graduate.customer.services;

import com.master.graduate.customer.dao.CartDAO;
import com.master.graduate.customer.dao.MessageDAO;
import com.master.graduate.customer.dao.OrderDAO;
import com.master.graduate.customer.dao.OrderItemDAO;
import com.master.graduate.customer.dao.ProductsDAO;
import com.master.graduate.customer.dao.UserDAO;
import com.master.graduate.customer.dao.WishListDAO;
import com.master.graduate.customer.entities.Cart;
import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.CustomerCart;
import com.master.graduate.customer.entities.Message;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.OrderItem;
import com.master.graduate.customer.entities.OrderShow;
import com.master.graduate.customer.entities.Products;
import com.master.graduate.customer.entities.WishList;
import com.master.graduate.utils.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个服务用来处理我们的用户相关信息
 *
 * @author Master_Joe lutong99
 */

@Service
public class UserService {

    /**
     * 操作用户数据表{customer}
     */
    @Autowired
    UserDAO userDAO;

    /**
     * 操作cart数据表
     */
    @Autowired
    CartDAO cartDAO;

    /**
     * 操作product数据表
     */
    @Autowired
    ProductsDAO productsDAO;

    /**
     * 操作orders数据表
     */
    @Autowired
    OrderDAO orderDAO;

    /**
     * 操作订单项的数据表
     */
    @Autowired
    OrderItemDAO orderItemDAO;

    /**
     * 操作用户的愿望清单的数据库表
     */
    @Autowired
    WishListDAO wishListDAO;

    /**
     * 处理信息相关的实体类
     */
    @Autowired
    MessageDAO messageDAO;

    /**
     * 用户注册到数据库中
     *
     * @param customer 将已经注册好的用户添加到数据库中
     */
    public void register(Customer customer) {
        userDAO.add(customer);
    }

    /**
     * 登陆，通过相应信息获取到一个用户对象
     */
    public Customer login(Customer temp) {
        String email = temp.getEmail();
        String password = temp.getPassword();
        return userDAO.getUser(email, password);
    }

    /**
     * 在注册的时候验证用户是否存在
     *
     * @param customer 注册的时候传过来的用户
     * @return 返回一个用户
     */
    public Customer verify(Customer customer) {
        String email = customer.getEmail();
        return userDAO.getUserByEmail(email);
    }

    /**
     * 获取用戶的所有的购物车信息
     * <p>
     * 1. 根据用户获取到所有的商品id和数量。<br/>
     * 2. 根据商品id获得商品<br/>
     * <p>
     * 注意：用户购物车包含一个集合（这个集合中的每一条为一个商品对象和一个数量）；<br/>
     *
     * @param customer 用戶
     * @return 一个用户购物车
     */
    public CustomerCart getUsersCart(Customer customer) {
        Integer cId = customer.getId();
        List<Cart> carts = cartDAO.getCartByCId(cId);
        List<CustomerCart.ProductCart> productCarts = new ArrayList<>();
        for (Cart cart : carts) {
            int id = cart.getpId();
            Products product = productsDAO.getProductsById(id);
            CustomerCart.ProductCart productCart = new CustomerCart.ProductCart(product, cart.getCartNum());
            productCarts.add(productCart);
        }
        return new CustomerCart(productCarts);
    }

    /**
     * 1. 先将用户的订单放在数据库中<br/>
     * 2. 将我们的用户的所有cart的状态设置为0，（修改数据库），为了以后例时记录查看<br/>
     *
     * @param order 将要提交的订单。
     */
    public void submit(Order order) {
        // 将订单的放在数据库中。
        orderDAO.saveOrders(order);
        // 将订单的所有订单项存在数据库中
        Integer cId = order.getcId();
        Customer userById = userDAO.getUserById(cId);
        CustomerCart usersCart = getUsersCart(userById);
        List<CustomerCart.ProductCart> productCarts = usersCart.getProductCarts();
        for (CustomerCart.ProductCart productCart : productCarts) {
            int cartNum = productCart.getNum();
            Integer pId = productCart.getProduct().getId();
            OrderItem orderItem = new OrderItem(null, cId, pId, cartNum, order.getOrderNumber());
            orderItemDAO.save(orderItem);
        }
        // 根据用户的id将订单中的所有购物车都设置成状态为0；
        cartDAO.submit(cId);
    }

    /**
     * 验证用户是否已经注册。一部查询
     * 1. 取数据库中查询用户，如果查询到，就说明用户已经存在了<br/>
     *
     * @param email 查询条件
     */
    public Customer verifyEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    /**
     * 更改密码
     *
     * @param customer 原来的用户
     * @param newpass  新密码
     */
    public void updatePass(Customer customer, String newpass) {
        Integer id = customer.getId();
        newpass = MD5Generator.encrypByMD5(newpass);
        userDAO.updatePass(id, newpass);
    }

    /**
     * 更新用户的信息
     */
    public void updateUser(Customer customer, Integer id) {
        customer.setId(id);
        userDAO.updateUser(customer);
    }

    /**
     * 首先把所有的订单都放在我们的用户的属性中的map<Order, List<OrderItem>>
     * <p>
     * 1. 先从订单表中获取我们的订单信息放在key<br/>
     * 2. 从orderitem里面获得所有的orderitem放在一个list，也就是我们的CustomerCart里面<br/>
     * 3. 封装完之后做一个返回，更新一下session中的内容<br/>
     *
     * @param customer 传过来根据
     * @return 更新后的customer
     */
    public Customer show(Customer customer) {
        Integer id = customer.getId();
        List<Order> orders = orderDAO.getOrders(id);
//        Map<Order, List<OrderItem>> map = new HashMap<>();
//        for (Order order : orders) {
//            String orderNumber = order.getOrderNumber();
//            List<OrderItem> orderItems = orderItemDAO.getItemsByOrderNumber(orderNumber);
//            map.put(order, orderItems);
//        }
//        customer.setOrderShow(new OrderShow(map));

        Map<Order, CustomerCart> map = new HashMap<>();
        for (Order order : orders) {
            String orderNumber = order.getOrderNumber();
            List<OrderItem> items = orderItemDAO.getItemsByOrderNumber(orderNumber);
            List<CustomerCart.ProductCart> products = new ArrayList<>();
            for (OrderItem item : items) {
                int num = item.getCartNum();
                Products productsById = productsDAO.getProductsById(item.getpId());
                CustomerCart.ProductCart productCart = new CustomerCart.ProductCart(productsById, num);
                products.add(productCart);
            }
            CustomerCart cart = new CustomerCart(products);
            map.put(order, cart);
        }
        OrderShow orderShow = new OrderShow(map);
        customer.setOrderShow(orderShow);
        return customer;
    }

    /**
     * 更新用户订单的数据
     *
     * @param order 订单对象
     */
    public void updateOrder(Order order) {
        Order temp = orderDAO.getOrderByOrderNum(order.getOrderNumber());
        order.setId(temp.getId());
        order.setOrderTime(temp.getOrderTime());
        orderDAO.updateOrder(order);
    }

    /**
     * 将商品加入到愿望清单中
     * <p>
     * 1. 将要加入的商品加入到数据库中，若清单中已经有了，则不操作，只显示 <br/>
     * 2. 并获取到所有的商品
     * 2.1 根据我们的cId（customer ID）所有商品的商品id，pIds
     * 2.2 根据pIds获取商品装成集合传回去
     *
     * @param cId 用户id
     * @param pId 产品id
     */
    public List<Products> addToWishList(Integer cId, Integer pId) {
        WishList item = wishListDAO.getByPIdAndCId(cId, pId);
        if (item == null) {
            wishListDAO.insert(cId, pId);
        }
        List<Integer> pIds = wishListDAO.getAllByCId(cId);
        List<Products> pros = new ArrayList<>();
        for (Integer i : pIds) {
            Products product = productsDAO.getProductsById(i);
            pros.add(product);
        }
        return pros;
    }

    /**
     * 显示愿望清单
     * <p>
     * 1. 并获取到所有的商品
     * 1.1 根据我们的cId（customer ID）所有商品的商品id，pIds
     * 1.2 根据pIds获取商品装成集合传回去
     *
     * @param cId 用户id
     */
    public List<Products> getWishList(Integer cId) {
        List<Integer> pIds = wishListDAO.getAllByCId(cId);
        List<Products> pros = new ArrayList<>();
        for (Integer i : pIds) {
            Products pro = productsDAO.getProductsById(i);
            pros.add(pro);
        }
        return pros;
    }

    /**
     * 根据cId和pId删除一条记录
     *
     * @return 一个列表项的对象
     */
    public WishList deleteWishList(Integer cId, Integer pId) {
        WishList wishList = wishListDAO.getByPIdAndCId(cId, pId);
        wishListDAO.deleteByCIdAndPId(cId, pId);
        return wishList;
    }

    /**
     * 提交信息
     * <p>
     * 1. 检验message
     * 1.1 c_id，time，name，email，type
     * 2. 保存到数据库中
     */
    public void commit(Message message, Customer customer) {
        message.setcId(customer.getId());
        message.setTime(new Timestamp(new Date().getTime()));
        if (message.getName() != null && "".equals(message.getName().trim())) {
            message.setName(customer.getLastName() + customer.getFirstName());
        }
        if (message.getEmail() != null && "".equals(message.getEmail().trim())) {
            message.setEmail(customer.getEmail());
        }
        if (message.getType() != null && "".equals(message.getType())) {
            message.setType("未指定类型");
        }

        messageDAO.insert(message);
    }
}
