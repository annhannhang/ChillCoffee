package com.ptnzzn.chillcoffee.modal;

public class Bill {
    private int id;
    private String idtable;
    private String idhr;
    private String time;
    private int total;

    public Bill(int id, String idtable, String idhr, String time, int total) {
        this.id = id;
        this.idtable = idtable;
        this.idhr = idhr;
        this.time = time;
        this.total = total;
    }

    public Bill(String idtable, String idhr, String time, int total) {
        this.idtable = idtable;
        this.idhr = idhr;
        this.time = time;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdtable() {
        return idtable;
    }

    public void setIdtable(String idtable) {
        this.idtable = idtable;
    }

    public String getIdhr() {
        return idhr;
    }

    public void setIdhr(String idhr) {
        this.idhr = idhr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
