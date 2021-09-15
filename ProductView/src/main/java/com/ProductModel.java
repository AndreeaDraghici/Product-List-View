package com;

public class ProductModel {

    private String name;
    private double price;
    private int cantitate;
    private String details;


    public ProductModel() {
        this.name = "";
        this.price = 0;
        this.cantitate = 0;
        this.details = "";
    }

    public ProductModel(String _name, double _price, int _cantitate, String _details) {
        this.name = _name;
        this.price = _price;
        this.cantitate = _cantitate;
        this.details = _details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
