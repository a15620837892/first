package com.lanqiao.entity;

public class Shares {
    private Integer id;

    private String name;

    private Double oldprice;

    private Double newprice;

    private Integer bargaincount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getOldprice() {
        return oldprice;
    }

    public void setOldprice(Double oldprice) {
        this.oldprice = oldprice;
    }

    public Double getNewprice() {
        return newprice;
    }

    public void setNewprice(Double newprice) {
        this.newprice = newprice;
    }

    public Integer getBargaincount() {
        return bargaincount;
    }

    public void setBargaincount(Integer bargaincount) {
        this.bargaincount = bargaincount;
    }
}