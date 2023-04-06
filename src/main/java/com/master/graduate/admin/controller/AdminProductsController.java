package com.master.graduate.admin.controller;

import com.master.graduate.admin.services.AdminProductsService;
import com.master.graduate.customer.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理产品相关的请求的控制器
 *
 * @author Master_Joe lutong99
 * @since 3/3/2020 10:52 AM
 */
@Controller
public class AdminProductsController {

    /**
     * 处理产品相关服务
     */
    @Autowired
    AdminProductsService productsService;

    /**
     * 请求要更新的请求
     */
    @RequestMapping("/admin/updateProduct")
    public String updateProducts(@RequestParam("id") Integer id, Model model) {
        Products product = productsService.getProductById(id);
        if (product != null) {
            model.addAttribute(product);
            return "admin/updateProducts";
        } else {
            model.addAttribute("exception", "该商品不存在或者已被删除");
            return "admin/exception";
        }
    }

    /**
     * 更新一个商品
     * <p>
     * 1. 获得我们需要更新的商品temp
     * 2. 将原来的商品的id和temp获取到传给服务层
     */
    @RequestMapping("/admin/submitUpdateProduct")
    public String submitProducts(Products temp, @RequestParam("file") MultipartFile file, @RequestParam("id") Integer id, Model model) {
        Products products = productsService.updateProducts(temp, id, file);
        if (products != null) {
            return "redirect:/admin/products/normal";
        } else {
            model.addAttribute("exception", "系统异常，请联系管理员");
            return "admin/exception";
        }
    }


    /**
     * 请求要添加的请求
     */
    @RequestMapping("/admin/deleteProduct")
    public String deleteProducts(@RequestParam("id") Integer id, Model model) {
        Products product = productsService.getProductById(id);
        if (product != null) {
            productsService.deleteProductById(id);
            return "redirect:/admin/products/normal";
        } else {
            model.addAttribute("exception", "该商品不存在或者已被删除");
            return "admin/exception";
        }
    }

    /**
     * 添加请求
     */
    @RequestMapping("/admin/addProduct")
    public String addProducts() {
        return "admin/addProducts";
    }

    @RequestMapping("/admin/submitAddProducts")
    public String submitAddProducts(Products temp, @RequestParam("file") MultipartFile file, Model model) {
        Products pro = productsService.addProducts(temp, file);
        return "redirect:/admin/products/list";
    }


}
