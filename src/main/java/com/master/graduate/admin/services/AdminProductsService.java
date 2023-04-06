package com.master.graduate.admin.services;

import com.master.graduate.customer.dao.OrderDAO;
import com.master.graduate.customer.dao.ProductsDAO;
import com.master.graduate.customer.entities.Kind;
import com.master.graduate.customer.entities.Order;
import com.master.graduate.customer.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 管理员处理产品的相关服务
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 5:05 PM
 */
@Service
public class AdminProductsService {

    /**
     * 处理产品的DAO
     */
    @Autowired
    ProductsDAO productsDAO;
    /**
     * 处理订单的DAO
     */
    @Autowired
    OrderDAO orderDAO;

    /**
     * 获取所有的产品，并返回
     */
    public List<Products> getAll() {
        return productsDAO.getAllProducts();
    }

    /**
     * 通过id获得一个商品并返回
     */
    public Products getProductById(Integer id) {
        return productsDAO.getProductsById(id);
    }

    /**
     * 通过id删除一个商品
     */
    public void deleteProductById(Integer id) {
        productsDAO.deleteById(id);
    }

    /**
     * 根据我们的用户传过来信息，更新后并返回给控制层
     * 1. 图片要考虑
     * 2. 种类如果不在原来的种类里面我们就创建一个，改动两张表
     * 2.1 获取所有的种类对象
     * 2.2 查看一下，对象中的名字是否包含新名字
     * 2.2.1 若不包含名字，则获取最大的id，然后加1，生成一个新的对象放在kind表中
     */
    public Products updateProducts(Products temp, Integer id, MultipartFile file) {
        // 状态不在这里面统统设置为1
        if (!"0".equals(temp.getState()) && !"1".equals(temp.getState()) && !"5".equals(temp.getState()) && !"6".equals(temp.getState())) {
            temp.setState("1");
        }
        Products product = productsDAO.getProductsById(id);
        String newCategory = temp.getpCategory();
        // 种类如果不在原来的种类里面我们就创建一个，改动两张表
        List<Kind> kinds = productsDAO.getKinds();
        List<String> kindsName = kinds.stream().map(Kind::getkName).collect(Collectors.toList());
        Optional<Integer> max = kinds.stream().map(Kind::getkId).map(Integer::parseInt).max(Comparator.naturalOrder());
        if (!kindsName.contains(newCategory)) {
            String newKid = String.valueOf(max.get() + 1);
            temp.setpKind(newKid);
            Kind newKind = new Kind(newKid, newCategory);
            productsDAO.insertKind(newKind);
        }
        Class<Products> pClass = Products.class;
        Field[] fields = pClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object original = field.get(product);
                Object present = field.get(temp);
                if (present != null) {
                    if (!present.equals(original)) {
                        field.set(product, present);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String name = uploadFile(file);

        product.setpPath("/library/upload/" + name);
        //
        // 比较temp与products的不同，修改
        productsDAO.updateProduct(product);
        return productsDAO.getProductsById(id);
    }

    /**
     * 上传文件，并返回文件的名字
     */
    private String uploadFile(MultipartFile file) {

        String name = null;
        try {
            /*
            把文件上传到，resources目录下面的library下面， 然后更新地址
             */
            InputStream inputStream = file.getInputStream();
            name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            // new File(System.getProperty("user.dir") + "/src/main/resources/public/library");
            OutputStream outputStream = new FileOutputStream("f:/resources/library/upload/" + name);
            OutputStream outputStream1 = new FileOutputStream("f:/resources/4040/library/upload/" + name);
            OutputStream outputStream2 = new FileOutputStream("f:/resources/product1/library/upload/" + name);
            OutputStream outputStream3 = new FileOutputStream("f:/resources/product1a/library/upload/" + name);
//            OutputStream outputStream3 = new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/public/product1a/library/upload/" + name);
            FileCopyUtils.copy(inputStream, outputStream);
            InputStream inputStream1 = file.getInputStream();
            InputStream inputStream2 = file.getInputStream();
            InputStream inputStream3 = file.getInputStream();
            FileCopyUtils.copy(inputStream, outputStream);
            FileCopyUtils.copy(inputStream1, outputStream1);
            FileCopyUtils.copy(inputStream2, outputStream2);
            FileCopyUtils.copy(inputStream3, outputStream3);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 返回所有的有效演出
     */
    public List<Products> getAllNormal() {
        return productsDAO.getAllNormal();
    }

    /**
     * 获取所有的热门产品
     */
    public List<Products> getHot() {
        return productsDAO.getHot();
    }

    /**
     * 获取热门产品
     */
    public List<Products> getLatest() {
        return productsDAO.getLatest();
    }

    /**
     * 获取所有的订单对象
     */
    public List<Order> getOrders() {
        return orderDAO.getAll();
    }

    /**
     * 商品数量
     */
    public String getProductsNumber() {
        Long number = productsDAO.getNumber();
        return String.valueOf(number);
    }

    /**
     * 添加一个产品，并返回
     */
    public Products addProducts(Products temp, MultipartFile file) {
//        文件路径
        String name = uploadFile(file);
        temp.setpPath("/library/upload/" + name);
        // 种类问题
        List<Kind> kinds = productsDAO.getKinds();
        List<String> kindsName = kinds.stream().map(Kind::getkName).collect(Collectors.toList());
        Optional<Integer> max = kinds.stream().map(Kind::getkId).map(Integer::parseInt).max(Comparator.naturalOrder());
        String newCategory = temp.getpCategory();
        if (!kindsName.contains(newCategory)) {
            String newKid = String.valueOf(max.get() + 1);
            temp.setpKind(newKid);
            Kind newKind = new Kind(newKid, newCategory);
            productsDAO.insertKind(newKind);
        }

        productsDAO.saveProduct(temp);
        return temp;


    }
}
