package com.master.graduate.customer.services;

import com.master.graduate.customer.dao.CartDAO;
import com.master.graduate.customer.dao.OrderDAO;
import com.master.graduate.customer.dao.OrderItemDAO;
import com.master.graduate.customer.dao.ProductsDAO;
import com.master.graduate.customer.entities.Cart;
import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.CustomerCart;
import com.master.graduate.customer.entities.Kind;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.OrderItem;
import com.master.graduate.customer.entities.Products;
import com.master.graduate.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 产品相关服务
 *
 * @author Master_Joe lutong99
 * @since 2/20/2020 5:47 PM
 */
@Service
public class ProductsService {

    /**
     * 操作product数据表
     */
    @Autowired
    ProductsDAO productsDAO;
    /**
     * 操作购物车数据表
     */
    @Autowired
    CartDAO cartDAO;

    /**
     * 处理订单的数据库访问对象
     */
    @Autowired
    OrderItemDAO orderItemDAO;

    /**
     * 处理订单的数据库访问对象
     */
    @Autowired
    OrderDAO orderDAO;

    /**
     * 制造一个随机数
     */
    private Random ran = new Random();

    /**
     * 获取热门购买
     */
    public List<Products> getHot() {
        List<Products> all = productsDAO.getHot();
        Collections.shuffle(all);
        return all.subList(0, 4);
    }

    /**
     * 获取最新的产品
     */
    public List<Products> getLatest() {
        List<Products> all = productsDAO.getLatest();
        Collections.shuffle(all);
        return all.subList(0, 8);
    }

    /**
     * 获取种类
     */
    public List<Kind> getKinds() {
        return productsDAO.getKinds();
    }

    /**
     * 获取最贵的商品
     */
    public List<Products> getExpensive() {
        return productsDAO.getExpensive();
    }

    /**
     * 根据种类获取商品
     */
    public List<Products> getProductsByKind(String kind) {
        return productsDAO.getProductsByKind(kind);
    }

    /**
     * 获取一页数据，最后没怎么用上
     */
    public PageUtil<Products> getProductsByPage(int pageNo, String kind) {
        PageUtil<Products> page = new PageUtil<>();
        page.setPageSize(12);
        page.setPageNo(pageNo);
        int count = productsDAO.getProductsNumberByKind(kind);
        page.setTotalCount(count);
        List<Products> productsByPageNumber = productsDAO.getProductsByPageNumber(kind, page.getStartNum(), page.getPageSize());
        page.setLists(productsByPageNumber);
        return page;
    }

    /**
     * 1. 先查看我们的数据库的库存是否足够
     * 2. 查看购物车中是否存在此商品。
     * 3. 添加到购物车中。
     *
     * @param customer 要添加购物车的用户
     * @param pId      要添加的商品id
     * @return <code>true</code>存储成功， <code>false</code>存储失败
     */
    public boolean saveToCart(Customer customer, int pId) {
        // 1.
        Products product = productsDAO.getProductsById(pId);
        if (product.getStore() < 1) {
            return false;
        }
        // 2.
        Integer cId = customer.getId();
        Cart cart = cartDAO.getCartByCIdPId(cId, pId);
        if (cart != null) {
//            if (cart.getCartNum() != 0) {
            cart.setCartNum(cart.getCartNum() + 1);
//            cartDAO.updateCart(cart);
            cartDAO.updateCart(cart.getCartNum(), cart.getId());
//            } else if (cart.getState() == 0) {
//                cartDAO.deleteProduct(cId, pId);
//                cart.setId(null);
//                cartDAO.saveCart(cart);
//            }
            return true;
        } else {
            cart = new Cart(null, customer.getId(), pId, 1);
            cartDAO.saveCart(cart);
            return true;
        }
    }

    /**
     * 调用数据库相关方法删除该条记录
     *
     * @param cId 用户id
     * @param pId 商品id
     */
    public void delete(Integer cId, Integer pId) {
        cartDAO.deleteProduct(cId, pId);
    }

    /**
     * 根据订单号和订单的产品id删除一条记录
     */
    public void deleteInOrder(String orderNum, Integer pid) {
        OrderItem orderItem = orderItemDAO.getOrderByPIdOrderNum(orderNum, pid);
        int cartNum = orderItem.getCartNum();
        Products productsById = productsDAO.getProductsById(pid);
        double decrement = productsById.getpPrice() * cartNum;
        orderDAO.updateMoney(decrement, orderNum);
        orderItemDAO.deleteOrderByPIdOrderNum(orderNum, pid);
    }

    /**
     * 根据OrderNum得到一个Order和一个Cart
     *
     * @param orderNum 指定的order
     * @param cart     被赋值的cart
     */
    public Order showOrderDetails(String orderNum, CustomerCart cart) {
        Order order = orderDAO.getOrderByOrderNum(orderNum);
//        List<CustomerCart.ProductCart> productsList = new ArrayList<>();
//        CustomerCart customerCart = new CustomerCart(List< CustomerCart.ProductCart > productsList  );
//        new CustomerCart.ProductCart(new Products(), int num);
        List<CustomerCart.ProductCart> productCarts = new ArrayList<>();
        List<OrderItem> items = orderItemDAO.getItemsByOrderNumber(orderNum);
        for (OrderItem item : items) {
            int num = item.getCartNum();
            Products product = productsDAO.getProductsById(item.getpId());
            productCarts.add(new CustomerCart.ProductCart(product, num));
        }
        cart.setProductCarts(productCarts);
        return order;
    }

    /**
     * 获取一个产品对象
     */
    public Products getProductsById(Integer id) {
        return productsDAO.getProductsById(id);
    }
}
