package com.ptnzzn.chillcoffee.modal;

import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private String name;
    private String time;
    private int price;
    private String status;

    public Table(int id, String name, String time, int price, String status) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.price = price;
        this.status = status;
    }

    public Table(String name, String time, int price, String status) {
        this.name = name;
        this.time = time;
        this.price = price;
        this.status = status;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
