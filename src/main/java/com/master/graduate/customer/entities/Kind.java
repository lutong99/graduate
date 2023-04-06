package com.master.graduate.customer.entities;

import java.io.Serializable;

/**
 * 种类的实体类
 *
 * @author Master_Joe lutong99
 * @since 2/22/2020 3:23 PM
 */
public class Kind implements Serializable {

    /**
     * 种类id
     */
    private String kId;

    /**
     * 种类名
     */
    private String kName;

    public Kind() {
    }

    public Kind(String kId, String kName) {
        this.kId = kId;
        this.kName = kName;
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId;
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }
}
