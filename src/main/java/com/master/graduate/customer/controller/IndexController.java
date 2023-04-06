package com.master.graduate.customer.controller;

import com.alibaba.fastjson.JSON;
import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.CustomerCart;
import com.master.graduate.customer.entities.Kind;
import com.master.graduate.customer.entities.Products;
import com.master.graduate.customer.services.ProductsService;
import com.master.graduate.customer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户界面的初始化控制器，一般是用来映射一些页面
 *
 * @author Master_Joe lutong99
 * @since 2/18/2020 2:34 PM
 */
@Controller
public class IndexController {

    /**
     * 处理商品服务
     */
    @Autowired
    ProductsService productsService;

    /**
     * 处理用户服务
     */
    @Autowired
    UserService userService;

    /**
     * 用三个请求地址来限制首页，使用户能精准找到首页
     * 1. 将热卖票，最贵的票，最新的票放在首页
     *
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping({"/", "/index", "/index.html"})
    public String index(HttpSession session) {
        initializeSom(session);
        return "customer/index";
    }

    /**
     * 进入我的用户界面
     * <p>
     * 1. 判断是否登陆
     * 1.1 若没有登陆，则返回登陆界面，并带上相应提示信息
     * 1.2 若登录了，则进入我的账户
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping({"/account", "/myaccount.html"})
    public String account(HttpSession session, Model model) {
        Object customer = session.getAttribute("customer");
        if (customer != null) {
            return "customer/myaccount";
        } else {
            model.addAttribute("message", "请您先登陆");
            return "customer/login";
        }
    }

    /**
     * 进入到购物车页面
     * <p>
     * 1. 先判断是否登陆
     * 1.1 若没有登陆，带上相应的信息，返回登陆界面
     * 1.2 若登陆了，进入到购物车界面
     * 2. 进入购物车界面，要现根据登录信息获得对应用户的购物车消息，交给页面渲染显示
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping({"/shopcart", "/shopping-cart.html"})
    public String shopcart(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            model.addAttribute("message", "您还没有登陆，请先登录");
            return "customer/login";
        } else {
            CustomerCart usersCart = userService.getUsersCart(customer);
            customer.setCustomerCart(usersCart);
            return "customer/shopping-cart";
        }
    }

    /**
     * 进入结账界面
     * <p>
     * 登陆了就登陆了，进入结账界面
     * 没有登陆就提示登陆，到登陆界面
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping({"/checkout.html", "/checkout"})
    public String checkout(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null && customer.getCustomerCart().getProductCarts().size() != 0) {
            return "customer/checkout";
        } else if (customer != null && customer.getCustomerCart().getProductCarts().size() == 0) {
            return "customer/shopping-cart";
        } else {
            model.addAttribute("message", "请先登录才可以结账哦");
            return "customer/login";
        }
    }

    /**
     * 分类界面。
     * <p>
     * 1. 先将查到的所有信息全部放在JSON里面，
     * 2. 将数据带回到我们的前端显示，使用异步请求更快
     *
     * @param model 将下一个页面需要而其他页面不需要的信息带给前端。
     */
    @RequestMapping({"/category.html", "/category"})
    public String category(HttpSession session, Model model) {
        initializeSom(session);
        List<Kind> kinds = productsService.getKinds();
        List<Products> productsByKind = productsService.getProductsByKind("1");
        productsByKind = productsByKind.subList(0, 9);
        model.addAttribute("productsByKind", productsByKind);
        model.addAttribute("kinds", kinds);
        String pros = JSON.toJSONString(productsByKind);
        model.addAttribute("pros", pros);
        return "customer/category";
    }

    /**
     * 初始化热门，最新消息等
     *
     * @param session 将属于全局的属性设置到session中
     */
    private void initializeSom(HttpSession session) {
        List<Products> productsHot = productsService.getHot();
        List<Products> productsLatest = productsService.getLatest();
        List<Products> productsExpensive = productsService.getExpensive();
        session.setAttribute("productsHot", productsHot);
        session.setAttribute("productsLatest", productsLatest);
        session.setAttribute("productsExpensive", productsExpensive);
    }

    /**
     * 映射联系我们的请求
     */
    @RequestMapping("/contact.html")
    public String contact(HttpSession session, Model model) {
        Object customer = session.getAttribute("customer");
        if (customer != null) {
            return "customer/contact";
        } else {
            model.addAttribute("message", "请先登录后再进行操作");
            return "customer/login";
        }

    }

    @RequestMapping({"/customer/success", "/customer/success.html"})
    public String success() {
        return "customer/success";
    }

    /**
     * 映射登陆界面
     */
    @RequestMapping({"/login.html", "/login"})
    public String login() {
        return "customer/login";
    }

    @Deprecated
    @RequestMapping("/product.html")
    public String product() {
        return "customer/product";
    }

    /**
     * 映射注册页面
     */
    @RequestMapping("/register.html")
    public String register() {
        return "customer/register";
    }

    /**
     * 愿望清单
     * <p>
     * 1. 先登陆后才可以进行查看
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping({"/wishlist.html", "/wishlist"})
    public String wishList(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            Integer cId = customer.getId();
            List<Products> list = userService.getWishList(cId);
            model.addAttribute("list", list);
            return "customer/wishlist";
        } else {
            model.addAttribute("message", "请先登录才可以查看哦");
            return "customer/login";
        }
    }

}
