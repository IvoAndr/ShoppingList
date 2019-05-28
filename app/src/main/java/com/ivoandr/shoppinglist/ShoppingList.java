package com.ivoandr.shoppinglist;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class ShoppingList {
    private int id;
    private String name;
    private String date;
    private ArrayList<Item> items = new ArrayList<>();
    private int itemsCount;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        this.itemsCount = items.size();
    }

    int getItemsCount() {
        return itemsCount;
    }

    void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getFormattedDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date d;
        try {
            d = df.parse(date);
            return " " + d.getDate() + "." + (d.getMonth() + 1) + "." + (d.getYear() + 1900) + "г.";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", items=" + items +
                ", itemsCount=" + itemsCount +
                '}';
    }
}
