package com.master.graduate.customer.controller;

import com.master.graduate.customer.entities.Customer;
import com.master.graduate.customer.entities.CustomerCart;
import com.master.graduate.customer.entities.Message;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.Products;
import com.master.graduate.customer.entities.WishList;
import com.master.graduate.customer.services.UserService;
import com.master.graduate.utils.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户特有的相关请求处理器
 *
 * @author Master_Joe lutong99
 * @since 2/18/2020 10:45 AM
 */
@Controller
public class UserController {
    /**
     * 处理用户的服务
     */
    @Autowired
    UserService userService;

    /**
     * 注册信息：
     * 1. 检测用户是否存在
     * <p>
     * 2. 获取足够的信息以存入数据库中。
     * 3. 设置好不能通过请求参数获得的信息
     * 3.1. 加密密码
     * 3.2. 设置时间戳
     *
     * @param customer spring封装好的实体类
     * @param province 没有封装的省份
     * @param city     没有封装的城市
     * @param model    将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session  将属于全局的属性设置到session中
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Customer customer, @RequestParam("PurchaseProvince") String province, @RequestParam("PurchaseCity") String city, Model model, HttpSession session) {
        Customer c = userService.verify(customer);
        if (c == null) {
            customer.setPassword(MD5Generator.encrypByMD5(customer.getPassword()));
            customer.setCity(province + "-" + city);
            customer.setRegisterTime(new Timestamp(new Date().getTime()));
            customer.setVip("0");
            // 如果数据库中没有用户，则注册成功，将注册好的用户放在session中，回到我的用户界面，
            userService.register(customer);
            return "customer/myaccount";
        } else {
            // 用户已经存在，那么就提醒用户用户已经存在是否登陆？
            model.addAttribute("message", "用户已经存在，是否(点我)登陆？");
            return "customer/register";
        }
    }

    /**
     * 登陆
     * <p>
     * 1. 根据用户输入的信息比对数据库中已有数据
     * 2. 若能查询到数据，我们就判断其登录成功，将用户加入到session中，作为登陆成功的标志，转到我的信息界面。
     * 3. 若不能查询到数据，判断其密码或账号错误，并将消息传回页面
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @PostMapping(path = "/login")
    public String login(Customer customer, Model model, HttpSession session) {
        Customer temp = new Customer(customer.getEmail(), MD5Generator.encrypByMD5(customer.getPassword()));
        Customer c = userService.login(temp);
        if (c == null) {
            // 用户名或者密码不正确，就返回原来的登陆页面
            model.addAttribute("message", "用户名或密码错误");
            return "customer/login";
        } else {
            // 成功之后：将用户信息挂在整个页面中，用session为了防止表单重复提交使用重定向
            CustomerCart usersCart = userService.getUsersCart(c);
            c.setCustomerCart(usersCart);
            session.setAttribute("customer", c);
            return "redirect:/account";
        }
    }

    /**
     * 退出。
     * 1. 将session中的用户属性清楚掉并返回首页
     *
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        return "customer/index";
    }

    /**
     * 1. 提交订单后接收到数据，我们插入到数据库中
     * 2. 之前我们要设置好订单号，订单时间
     * 3. 提交的过程中，将我们的用户的所有cart的状态设置为0，（修改数据库），为了以后例时记录查看
     *
     * @param order   用户提交的订单。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping(path = "/submitOrder", method = RequestMethod.POST)
    public String submitOrder(@RequestParam("option2") String option2, @RequestParam("lastName") String lastName, @RequestParam("PurchaseProvince") String province, @RequestParam("PurchaseCity") String city, Order order, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Integer id = customer.getId();
        order.setcId(id);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderTime(new Timestamp(new Date().getTime()));
        order.setReceiverName(lastName + "-" + order.getReceiverName());
        order.setReceiverAddress(province + "-" + city + "-" + order.getReceiverAddress());
        order.setExtra(option2);
        if (order.getMoney() != 0) {
            userService.submit(order);
        }
        return "redirect:/index";
    }


    /**
     * 1. 修改订单后接收到数据，我们在数据库中更新数据
     *
     * @param order   用户提交的订单。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping(path = "/updateOrder", method = RequestMethod.POST)
    public String updateOrder(@RequestParam("option2") String option2, @RequestParam("lastName") String lastName, @RequestParam("PurchaseProvince") String province, @RequestParam("PurchaseCity") String city, @RequestParam("orderNum") String orderNum, Order order, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Integer id = customer.getId();
        order.setcId(id);
        order.setOrderNumber(orderNum);
        order.setReceiverName(lastName + "-" + order.getReceiverName());
        order.setReceiverAddress(province + "-" + city + "-" + order.getReceiverAddress());
        order.setExtra(option2);
        System.out.println("order = " + order);
        userService.updateOrder(order);
//        if (order.getMoney() != 0) {
//            userService.submit(order);
//        }
        return "redirect:/index";
    }

    /**
     * 验证邮箱是否已经存在，返回JSON字符串，用作ajax异步请求的返回消息
     *
     * @param email 需要验证的邮箱
     */
    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    @ResponseBody
    public String verifyEmail(@RequestParam("email") String email) {
        Customer customer = userService.verifyEmail(email);
        if (customer == null) {
            return "";
        } else {
            return "用户已存在，是否(点我)登录？";
        }
    }

    /**
     * 修改密码请求
     * <p>
     * 1. 判断用户是否登陆。
     * 1.1 已登录，则去修改界面
     * 1.2 未登录，去修改页面
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/change")
    public String change(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            model.addAttribute("message", "请您登陆后进行操作哦");
            return "customer/login";
        } else {
            return "customer/changePassword";
        }
    }


    /**
     * 修改密码。
     * <p>
     * 0. 同样是先检查是否登陆
     * 1. 获取三个参数
     * 2. 比对原密码是否正确，
     * 2.1 若正确，则修改数据库中的数据，并返回我的账户界面
     * 2.2 若不正确返回原来的页面
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping(value = "/updatePass", method = RequestMethod.POST)
    public String changePassword(@RequestParam("previous") String previous, @RequestParam("newpass") String newpass, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            String passwordCur = customer.getPassword();
            String pp = MD5Generator.encrypByMD5(previous);
            if (pp.equalsIgnoreCase(passwordCur)) {
                // 如果密码相等我们就修改
                userService.updatePass(customer, newpass);
                return "customer/myaccount";
            } else {
                // 密码不相等我们就返回原来的页面，并带上一定的消息
                model.addAttribute("message", "原密码不正确");
                return "customer/changePassword";
            }
        } else {
            model.addAttribute("message", "请先登录在进行后续操作");
            return "customer/login";
        }
    }

    /**
     * 编辑请求
     * <p>
     * 1. 先验证账户是否登陆
     * 1.1 若登录了，进入到修改页面
     * 1.2 若没有登陆，带上信息进入到登陆界面
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/edit")
    public String editInfo(HttpSession session, Model model) {
        Object customer = session.getAttribute("customer");
        if (customer != null) {
            return "customer/edit";
        } else {
            model.addAttribute("message", "请您先登录");
            return "customer/login";
        }
    }

    /**
     * 修改用户的信息。
     * <p>
     * 1. 获取请求参数，获取修改后的信息
     * 2. 获取已经登陆的用户id，为了存入到数据库
     *
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/updateUser")
    public String updateUser(Customer customer, @RequestParam("PurchaseProvince") String province, @RequestParam("PurchaseCity") String city, HttpSession session) {
        Customer cGet = (Customer) session.getAttribute("customer");
        Integer id = cGet.getId();
        customer.setCity(province + "-" + city);
        userService.updateUser(customer, id);
        return "redirect:/account";
    }

    /**
     * 1. 发出请求
     * <p>
     * 1. 若已经登陆了，那么就请求成功，转发到订单页面
     * 2. 没有登陆的话，就带上信息返回
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/showOrders")
    public String showOrders(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            return "redirect:/order";
        } else {
            model.addAttribute("message", "请您先登陆后进行操作哦");
            return "customer/login";
        }
    }

    /**
     * 显示所有的订单以及修改订单信息
     * <p>
     * 同样判断是否登录
     * <p>
     * 把这个用户传给显示的服务层
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/order")
    public String showOrder(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            model.addAttribute("message", "请您先登录之后再进行操作");
            return "customer/login";
        } else {
            Customer c = userService.show(customer);
            session.setAttribute("customer", c);
            return "customer/order";
        }
    }

    /**
     * 加入愿望清单
     * <p>
     * 1. 判断是否登录，若登录，继续，未登录，则提醒用户登陆 <br/>
     * 2. 交给服务层去做放入愿望清单剩下的工作，带走的东西有用户的id和产品的id，带回来的东西是一个愿望清单的list
     *
     * @param model   将下一个页面需要而其他页面不需要的信息带给前端。
     * @param session 将属于全局的属性设置到session中
     */
    @RequestMapping("/addToWishList")
    public String addToWishList(@RequestParam("id") Integer id, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            List<Products> list = userService.addToWishList(customer.getId(), id);
            model.addAttribute("list", list);
            return "redirect:/wishlist";
        } else {
            model.addAttribute("message", "请您先登录后在进行后续操作哦");
            return "customer/login";
        }
    }

    /**
     * 删除一个愿望清单的记录
     * <p>
     * 1. 先判断是否登陆
     * 2. 根据pId，cId来进行删除
     */
    @RequestMapping("/deleteWishList")
    public String deleteWishList(@RequestParam("id") Integer pId, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            Integer cId = customer.getId();
            WishList list = userService.deleteWishList(cId, pId);
            return "redirect:/wishlist";
        } else {
            model.addAttribute("message", "请您先登录后在进行操作哦");
            return "customer/login";
        }
    }

    /**
     * 提交信息
     * <p>
     * 1. 判断是否登陆
     * 2. 获取一个Message对象，然后获取我们的用户
     * 3. 传给服务层
     */
    @RequestMapping("/commitMessage")
    public String commitMessage(Message message, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer != null) {
            userService.commit(message, customer);
            return "redirect:/customer/success";
        } else {
            model.addAttribute("message", "请先登录后在进行操作");
            return "customer/login";
        }
    }

}
