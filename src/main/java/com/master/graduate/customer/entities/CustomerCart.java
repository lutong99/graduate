package com.master.graduate.customer.entities;

import java.io.Serializable;
import java.util.List;

/**
 * 用户的购物车的实体类。
 *
 * @author Master_Joe lutong99
 * @since 2/24/2020 5:33 PM
 */
public class CustomerCart implements Serializable {

    /**
     * 购物车中的购物项的集合
     */
    List<ProductCart> productCarts;

    public CustomerCart() {

    }

    public CustomerCart(List<ProductCart> productCarts) {
        this.productCarts = productCarts;
    }

    public List<ProductCart> getProductCarts() {
        return productCarts;
    }

    public void setProductCarts(List<ProductCart> productCarts) {
        this.productCarts = productCarts;
    }

    /**
     * 购物项的类
     */
    public static class ProductCart {
        /**
         * 购物项的产品
         */
        Products product;
        /**
         * 购买数量
         */
        int num;

        /**
         * 购买总金额
         */
        double total;

        public ProductCart() {
        }

        public ProductCart(Products product, int num) {
            this.product = product;
            this.num = num;
        }

        public double getTotal() {
            return product.getpPrice();
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public Products getProduct() {
            return product;
        }

        public void setProduct(Products product) {
            this.product = product;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

}

