package com.ivoandr.shoppinglist;

import com.google.gson.Gson;

class Item {
    private int id;
    private String item;
    private double quantity;
    private String quantityType;
    private double price;
    private int status;
    private int dateIndex;

    Item() {
    }

    public Item(int id, String item, double quantity, String quantityType, double price, int status, int dateIndex) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.price = price;
        this.status = status;
        this.dateIndex = dateIndex;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getItem() {
        return item;
    }

    void setItem(String item) {
        this.item = item;
    }

    double getQuantity() {
        return quantity;
    }

    void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    String getQuantityType() {
        return quantityType;
    }

    void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }

    int getDateIndex() {
        return dateIndex;
    }

    void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                ", quantityType='" + quantityType + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", dateIndex=" + dateIndex +
                '}';
    }
}
