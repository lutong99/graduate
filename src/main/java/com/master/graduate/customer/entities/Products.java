package com.master.graduate.customer.entities;

import java.io.Serializable;
import java.util.Random;

/**
 * 演出票的实体类
 *
 * @author Master_Joe lutong99
 * @since 2/19/2020 2:57 PM
 */
public class Products implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 演出描述
     */
    private String pDescription;

    /**
     * 演出的种类
     */
    private String pCategory;

    /**
     * 演出描述
     */
    private String pName;

    /**
     * 演出的价格
     */
    private double pPrice;

    /**
     * 演出时间
     */
    private String pShowTime;

    /**
     * 演出地点
     */
    private String pVenue;

    /**
     * 演出的图片的路径
     */
    private String pPath;

    /**
     * 演出的种类编号。
     */
    private String pKind;

    /**
     * 库存量
     */
    private int store;

    /**
     * 存在状态
     */
    private String state;

    private int randomPrice = new Random().nextInt(78);

    public Products() {
    }

    public Products(Integer id, String pDescription, String pCategory, String pName, double pPrice, String pShowTime, String pVenue, String pPath, String pKind, int store, String state, int randomPrice) {
        this.id = id;
        this.pDescription = pDescription;
        this.pCategory = pCategory;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pShowTime = pShowTime;
        this.pVenue = pVenue;
        this.pPath = pPath;
        this.pKind = pKind;
        this.store = store;
        this.state = state;
        this.randomPrice = randomPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public String getpShowTime() {
        return pShowTime;
    }

    public void setpShowTime(String pShowTime) {
        this.pShowTime = pShowTime;
    }

    public String getpVenue() {
        return pVenue;
    }

    public void setpVenue(String pVenue) {
        this.pVenue = pVenue;
    }

    public String getpPath() {
        return pPath;
    }

    public void setpPath(String pPath) {
        this.pPath = pPath;
    }

    public String getpKind() {
        return pKind;
    }

    public void setpKind(String pKind) {
        this.pKind = pKind;
    }


    public int getRandomPrice() {
        return randomPrice;
    }

    public void setRandomPrice(int randomPrice) {
        this.randomPrice = randomPrice;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", pDescription='" + pDescription + '\'' +
                ", pCategory='" + pCategory + '\'' +
                ", pName='" + pName + '\'' +
                ", pPrice=" + pPrice +
                ", pShowTime='" + pShowTime + '\'' +
                ", pVenue='" + pVenue + '\'' +
                ", pPath='" + pPath + '\'' +
                ", pKind='" + pKind + '\'' +
                ", store=" + store +
                ", state='" + state + '\'' +
                ", randomPrice=" + randomPrice +
                '}';
    }
}
