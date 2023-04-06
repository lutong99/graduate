package com.master.graduate.customer.entities;

/**
 * 愿望清单
 *
 * @author Master_Joe lutong99
 * @since 2/28/2020 7:39 PM
 */
public class WishList {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer cId;

    /**
     * 产品id
     */
    private Integer pId;

    public WishList() {
    }

    public WishList(Integer id, Integer cId, Integer pId) {
        this.id = id;
        this.cId = cId;
        this.pId = pId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "id=" + id +
                ", cId=" + cId +
                ", pId=" + pId +
                '}';
    }
}
