package com.master.graduate.customer.controller;


import com.alibaba.fastjson.JSON;
import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.CustomerCart;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.Products;
import com.master.graduate.customer.services.ProductsService;
import com.master.graduate.customer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 产品控制器，接收请求并进行处理转发等工作
 *
 * @author Master_Joe lutong99
 * @since 2/20/2020 5:46 PM
 */
@Controller
public class ProductsController {

    /**
     * 处理产品的服务
     */
    @Autowired
    ProductsService productsService;

    /**
     * 处理用户的服务
     */
    @Autowired
    UserService userService;

    /**
     * 将我们需要的信息以JSON的格式转回去，用作ajax异步请求
     *
     * @param kind 需要的种类
     * @return 一个JSON字符串
     */
    @RequestMapping("getProductsByKind")
    @ResponseBody
    public String getProductsByKind(@RequestParam("kind") String kind) {
        List<Products> list = productsService.getProductsByKind(kind);
        return JSON.toJSONString(list);
    }

    /**
     * 加入购物车的步骤：
     * <p>
     * 1. 检查库存是否充足，充足的情况下
     * 1.1. 检查用户是否登录，若没有登陆，先让用户登陆，
     * 然后将购物车的内容增加到用户的购物车中
     * 1.2. 若用户已经登陆，将商品的对象放在数据库中
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/addCart")
    public String addCart(@RequestParam("id") int id, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            boolean save = productsService.saveToCart(customer, id);
            if (save) {
                // 更新用户的所有的购物车信息，并放在session中的用户中，然后我们就回到购物车页面。
                CustomerCart customerCart = userService.getUsersCart(customer);
                customer.setCustomerCart(customerCart);
                session.setAttribute("customer", customer);
                return "redirect:/shopcart";
            } else {
                return "customer/index";
            }
        } else {
            // 没有登陆的情况下，返回我们的登陆页面。
            model.addAttribute("message", "请您先登录");
            return "customer/login";
        }
    }


    /**
     * 删除购物车中的内容
     * <p>
     * 1. 先判断一下用户有没有登陆
     * 2. 如果没有登陆，就返回到登陆页面
     * 3. 如果登陆了，获取用户id和商品id联合删除
     *
     * @param id      购物车的pid
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/deleteCart")
    public String deleteCart(HttpSession session, Model model, @RequestParam("id") Integer id) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            model.addAttribute("message", "请您先登录后在进行操作哦");
            return "customer/login";
        } else {
            // 删除操作
            productsService.delete(customer.getId(), id);
            return "redirect:/shopcart";
        }
    }

    /**
     * 删除订单中的内容
     * <p>
     * 1. 先判断一下用户有没有登陆
     * 2. 如果没有登陆，就返回到登陆页面
     * 3. 如果登陆了，获取用户id和商品id联合删除
     *
     * @param id      产品id
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping(path = "/deleteCartInOrder")/*{id}{orderNum}*/
    public String deleteCartInOrder(@RequestParam("id") Integer id, @RequestParam("orderNum") String orderNum, HttpSession session, Model model) {

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            model.addAttribute("message", "请您先登录后在进行操作哦");
            return "customer/login";
        } else {
            // 删除订单操作
            productsService.deleteInOrder(orderNum, id);
            return "redirect:/order";
        }
    }

    /**
     * 显示一张订单的细节
     * <p>
     * 1. 和我们的checkout差不多,需要 <br>
     * <pre>名字，姓，电话，详细地址，城市，邮编，支付方式，快递方式，订单详情</pre>
     * 2. 将我们的order信息获取到，并获取一个购物车的Cart，需要一个cart，还需要一个Order
     */
    @RequestMapping("/orderDetails")
    public String showOrderDetails(@RequestParam("orderNumber") String orderNum, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            CustomerCart cart = new CustomerCart();
            Order order = productsService.showOrderDetails(orderNum, cart);
            model.addAttribute("cart", cart);
            model.addAttribute("order", order);
            return "customer/orderDetail";
        } else {
            model.addAttribute("message", "请先登录后再进行操作");
            return "customer/login";
        }
    }

    /**
     * 展示商品的详细信息
     *
     * @param id 产品id
     */
    @RequestMapping("/showDetails")
    public String showDetails(@RequestParam("id") Integer id, HttpSession session, Model model) {
        Products product = productsService.getProductsById(id);
        model.addAttribute("product", product);
        return "customer/product";
    }

}
