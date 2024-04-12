package com.ptnzzn.chillcoffee.modal;

public class HR {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String image;

    public HR(int id, String name, String phone, String address, String image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public HR(String name, String phone, String address, String image) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
