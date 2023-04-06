package com.master.graduate.admin.controller;

import com.master.graduate.admin.services.AdminCustomerService;
import com.master.graduate.customer.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 处理用户管理的Controller
 *
 * @author Master_Joe lutong99
 * @since 3/2/2020 3:53 PM
 */
@Controller
public class AdminCustomerController {

    /**
     * 处理用户的相关请求
     */
    @Autowired
    AdminCustomerService customerService;

    /**
     * 删除一个用户
     * <p>
     * 将我们要删除的用户id传给服务层<br/>
     * 删除用户后，再进行查询，查询得到null就说明成功删除
     */
    @RequestMapping("/admin/deleteCustomer")
    public String deleteCustomer(@RequestParam("id") Integer id, Model model) {
        Customer cu = customerService.deleteCustomerById(id);
        if (cu == null) {
            return "redirect:/admin/customers";
        } else {
            model.addAttribute("exception", "删除失败");
            return "/admin/exception";
        }
    }

    /**
     * 更新一个用户的信息
     * <p>
     * 1. 将id交给服务， 查询到用户，如果用户不存在，则返回异常页面
     */
    @RequestMapping("/admin/updateCustomer")
    public String updateCustomer(@RequestParam("id") Integer id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "/admin/updateCustomer";
        } else {
            model.addAttribute("exception", "用户不存在");
            return "/admin/exception";
        }
    }

    /**
     * 提交修改
     * <p>
     * 1. 从用户提交的内容来获取一个Customer交给服务层做相应处理
     */
    @RequestMapping("/admin/submitUpdateCustomer")
    public String submitUpdate(@RequestParam("username") String userName, Customer temp, Model model) {
        temp.setLastName(userName.substring(0, 1));
        temp.setFirstName(userName.substring(1));
        if (!temp.getCity().contains("-")) {
            temp.setCity(temp.getCity().substring(0, 2) + "-" + temp.getCity().substring(2));
        }
        System.out.println("temp = " + temp);
        Customer customer = customerService.updateCustomer(temp);
        if (customer != null) {
            return "redirect:/admin/customers";
        } else {
            model.addAttribute("exception", "修改异常");
            return "admin/exception";
        }
    }

}
