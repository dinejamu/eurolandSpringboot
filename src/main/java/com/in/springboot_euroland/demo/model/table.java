package com.in.springboot_euroland.demo.model;

import org.springframework.stereotype.Controller;

public class table {

    private int rows;
    private int coulmn;
    private String value;



    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCoulmn() {
        return coulmn;
    }

    public void setCoulmn(int coulmn) {
        this.coulmn = coulmn;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
