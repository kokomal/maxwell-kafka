package com.wanda.mxwkfk.model.biz;

import java.io.Serializable;

public class IMDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /* id */
    private String id;
    /* 名字 */
    private String name;
    /* =====Getters and Setters below===== */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "IMDto [id=" + id + ", name=" + name + "]";
    }

}
