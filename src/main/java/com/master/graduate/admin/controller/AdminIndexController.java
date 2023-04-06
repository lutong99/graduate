package com.master.graduate.admin.controller;

import com.master.graduate.admin.entities.BackUpRecover;
import com.master.graduate.admin.services.AdminCustomerService;
import com.master.graduate.admin.services.AdminOrderService;
import com.master.graduate.admin.services.AdminProductsService;
import com.master.graduate.admin.services.AdminService;
import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.Message;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 管理员初始化控制器，用来映射一开始的页面
 *
 * @author Master_Joe lutong99
 * @since 2/20/2020 9:37 AM
 */
@Controller
public class AdminIndexController {


    /**
     * 处理管理员相关服务
     */
    @Autowired
    AdminService adminService;

    /**
     * 处理用户相关服务
     */
    @Autowired
    AdminCustomerService customerService;

    /**
     * 处理产品相关服务
     */
    @Autowired
    AdminProductsService productsService;

    /**
     * 处理订票服务
     */
    @Autowired
    AdminOrderService orderService;

    /**
     * 映射管理员登陆
     */
    @RequestMapping({"/admin/", "/admin/login", "/admin/login/", "/admin/login.html"})
    public String login() {
        return "admin/login";
    }

    /**
     * 映射管理员首页
     */
    @RequestMapping({"/admin/index.html", "/admin/index"})
    public String adminIndex() {
        return "admin/index";
    }

    /**
     * 映射所有用户的页面
     */
    @RequestMapping({"/admin/customers.html", "/admin/customers", "/admin/customers/list.html", "/admin/customers/customers", "/admin/customers/list"})
    public String listCustomers(Model model) {
        List<Customer> list = customerService.getCustomers();
        model.addAttribute("list", list);
        return "admin/customerlist";
    }

    /**
     * 映射普通客户管理页面
     * <p>
     * 1. 查询到所有的普通客户
     * 2. 返回来放在我们的页面中
     * 注： 由于我门已经设计了一个拦截器，不需要考虑登陆的情况
     */
    @RequestMapping({"/admin/customers/normal", "/admin/customer/normal.html"})
    public String normalCustomers(Model model) {
        List<Customer> customers = customerService.getNormalCustomers();
        model.addAttribute("customers", customers);
        return "admin/normal";
    }

    /**
     * 映射vip客户管理页面
     */
    @RequestMapping({"/admin/customers/vip", "/admin/customer/vip.html"})
    public String vipCustomers(Model model) {
        List<Customer> vips = customerService.getVIPCustomers();
        model.addAttribute("vips", vips);
        return "admin/vip";
    }

    /**
     * 映射备份 & 还原页面
     */
    @RequestMapping({"/admin/data/backup", "/admin/data/backup.html"})
    public String backupData(Model model) {
        List<BackUpRecover> backups = adminService.getAllBackUps();
        model.addAttribute("backups", backups);
        return "admin/backup";
    }

    /**
     * 映射演出的增删改页面
     */
    @RequestMapping({"/admin/products/list", "/admin/products/list.html", "/admin/products/"})
    public String products(Model model) {
        List<Products> products = productsService.getAll();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @RequestMapping({"/admin/products/normal", "/admin/products/normal.html"})
    public String productsNormal(Model model) {
        List<Products> products = productsService.getAllNormal();
        model.addAttribute("products", products);
        return "admin/productsNormal";
    }

    /**
     * 映射演出的添加页面
     */
    @RequestMapping({"/admin/products/add", "/admin/products/add.html"})
    public String addProducts() {
        return "admin/addProducts";
    }

    /**
     * 映射演出的更新页面
     */
    @RequestMapping({"/admin/products/update", "/admin/products/update.html"})
    public String updateProducts() {
        return "admin/updateProducts";
    }

    /**
     * 映射最新演出
     */
    @RequestMapping({"/admin/products/latest", "/admin/products/latest.html"})
    public String showLatestProducts(Model model) {
        List<Products> latest = productsService.getLatest();
        model.addAttribute("latest", latest);
        return "admin/latest";
    }

    /**
     * 映射最热演出
     */
    @RequestMapping({"/admin/products/hot", "/admin/products/hot.html"})
    public String showHotProducts(Model model) {
        List<Products> hots = productsService.getHot();
        model.addAttribute("hots", hots);
        return "admin/hot";
    }

    /**
     * 映射打印信息
     */
    @RequestMapping({"/admin/info/print", "/admin/info/print.html"})
    public String printInfo() {
        return "admin/printInfo";
    }

    /**
     * 映射统计页面
     */
    @RequestMapping({"/admin/info/statistics", "/admin/info/statistics.html"})
    public String statistics(Model model) {

        String productsNumber = productsService.getProductsNumber();
        String money = orderService.getAllMoney();
        String messages = adminService.getAllMessages();
        String users = customerService.getAllUsers();

        model.addAttribute("products", productsNumber);
        model.addAttribute("money", money);
        model.addAttribute("messages", messages);
        model.addAttribute("users", users);

        return "admin/statistics";
    }

    /**
     * 显示留言
     */
    @RequestMapping({"/admin/message/", "/admin/message/list", "/admin/message/list.html"})
    public String showMessage(Model model) {
        List<Message> messages = adminService.getMessages();
        model.addAttribute("messages", messages);
        return "admin/message";
    }

    /**
     * 留言详情查看
     */
    @RequestMapping({"/admin/message/detail", "/admin/message/detail.html"})
    public String showMessageDetails() {
        return "admin/messageDetails";
    }

    /**
     * 订票管理
     */
    @RequestMapping({"/admin/sale/order", "/admin/sale/order.html"})
    public String orderProducts(Model model) {
        List<Order> orders = productsService.getOrders();
        model.addAttribute("orders", orders);
        return "admin/orderProducts";
    }

    /**
     * 售票管理
     */
    @RequestMapping({"/admin/sale/", "/admin/sale/sale", "/admin/sale/sale.html"})
    public String saleProducts() {
        return "admin/saleProducts";
    }

    /**
     * 退票管理
     */
    @RequestMapping({"/admin/sale/cancel", "/admin/sale/cancel.html"})
    public String cancelProducts() {
        return "admin/cancelProducts";
    }

    /**
     * 修改管理员密码的请求
     */
    @RequestMapping("/admin/update")
    public String updateAdmin() {
        return "admin/updateAdmin";
    }
}
