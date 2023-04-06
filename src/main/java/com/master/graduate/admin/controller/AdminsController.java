package com.master.graduate.admin.controller;

import com.master.graduate.admin.entities.Admin;
import com.master.graduate.admin.services.AdminOrderService;
import com.master.graduate.admin.services.AdminService;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.OrderItem;
import com.master.graduate.utils.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * 处理管理相关的请求，杂七杂八的请求都在这里
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 6:10 PM
 */
@Controller
public class AdminsController {

    /**
     * 处理订单的相应服务
     */
    @Autowired
    AdminOrderService orderService;
    /**
     * 处理管理员那些事
     */
    @Autowired
    private AdminService adminService;

    /**
     * 管理员进行登陆
     * <p>
     * 1. 从用户输入的信息组装成一个Admin对象
     * 2. 将这个对象交给服务层，服务层打回一个完整的对象
     */
    @RequestMapping(path = "/adminLogin")
    public String login(Admin admin, HttpSession session, Model model) {
        Admin a = adminService.verify(admin);
        if (a != null) {
            session.setAttribute("admin", a);
            return "redirect:/admin/index";
        } else {
            model.addAttribute("message", "用户名或密码不正确");
            return "admin/login";
        }
    }

    /**
     * 管理员退出登陆
     * <p>
     * 将session中的admin属性去掉即可
     */
    @RequestMapping(path = {"/adminLogout"})
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/admin/login";
    }

    /**
     * 查看管理员的信息
     */
    @RequestMapping(path = {"/adminInfo"})
    public String adminInfo() {
        return "";
    }

    /**
     * 数据库层面操作修改管理员的密码等信息
     * <p>
     * 1. 验证旧密码是否正确<br/>
     * 2. 修改新密码
     */
    @RequestMapping("/admin/updateAdmin")
    public String updateAdmin(@RequestParam("password") String password, @RequestParam("newpass") String newpass, HttpSession session, Model model) {
        Admin a = (Admin) session.getAttribute("admin");
        if (a != null && Objects.requireNonNull(MD5Generator.encrypByMD5(password)).equalsIgnoreCase(a.getPassword())) {
            // 进行修改
            Admin admin = adminService.updateInfo(a, newpass);
            session.setAttribute("admin", admin);
            return "redirect:/admin/customers/list";
        } else {
            model.addAttribute("message", "您的密码不正确");
            return "admin/updateAdmin";
        }
    }

    /**
     * 备份数据
     */
    @RequestMapping("/admin/backup")
    public String backupData() {
        adminService.backup();
        return "redirect:/admin/data/backup";
    }

    /**
     * 还原数据
     */
    @RequestMapping("/admin/recoverData")
    public String recoverData(@RequestParam("id") Integer id) {
        adminService.recover(id);
        return "redirect:/admin/data/backup";
    }

    /**
     * 删除一条消息记录
     */
    @RequestMapping("/admin/deleteMessage")
    public String deleteMessage(@RequestParam("id") Integer id) {
        adminService.deleteMessage(id);
        return "redirect:/admin/message/list";
    }

    /**
     * 修改Order
     */
    @RequestMapping("/admin/updateOrder")
    public String updateOrder(@RequestParam("id") Integer id, Model model) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            model.addAttribute("exception", "没有该订单或订单已被删除");
            return "admin/exception";
        } else {
            model.addAttribute("order", order);
            return "admin/orderDetails";
        }
    }

    /**
     * 提交修改的表单后
     * <p>
     * 1. 获取一个已经得到的订单，将传回来的参数带给服务层
     */
    @RequestMapping("/admin/submitUpdateOrder")
    public String submitUpdateOrder(Order order, @RequestParam("id") Integer id) {
        orderService.updateOrder(order, id);
        return "redirect:/admin/sale/order";
    }

    /**
     * 获取到我们的所有订单项
     */
    @RequestMapping("/admin/showOrderItems")
    public String showOrderItems(@RequestParam("orderNum") String orderNumber, Model model) {
        List<OrderItem> items = orderService.getItems(orderNumber);
        model.addAttribute("items", items);
        return "admin/orderItems";
    }

    /**
     * 更新订单项的请求
     */
    @RequestMapping("/admin/updateItem")
    public String updateItem(@RequestParam("id") Integer id, Model model) {
        OrderItem item = orderService.getItemById(id);
        if (item != null) {
            model.addAttribute("item", item);
            return "admin/updateOrderItem";
        } else {
            model.addAttribute("exception", "该订单项不存在或已被删除");
            return "admin/exception";
        }
    }

    /**
     * 更新OrderItem对象
     * <p>
     * 1. 从请求中获得一个OrderItem
     */
    @RequestMapping("/admin/submitUpdateItem")
    public String submitUpdateItem(OrderItem orderItem) {
        orderService.updateItem(orderItem);
        return "redirect:/admin/sale/order";
    }

}
