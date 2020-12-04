package com.platform.entity;

/**
 * @author luxx
 * @date 2017/11/16 0016
 */
public class Competition extends BaseEntity {

    private int id;
    private String name;
    private boolean valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Competition() {
    }

    public Competition(String name, boolean valid) {
        this.name = name;
        this.valid = valid;
    }
}
