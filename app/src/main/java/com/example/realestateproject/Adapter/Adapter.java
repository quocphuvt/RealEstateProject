package com.example.realestateproject.Adapter;

public class Adapter {
    String name,locate,status,phone,name1,price;

    public Adapter(String name, String locate, String status, String phone, String name1, String price) {
        this.name = name;
        this.locate = locate;
        this.status = status;
        this.phone = phone;
        this.name1 = name1;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getLocate() {
        return locate;
    }

    public String getStatus() {
        return status;
    }

    public String getPhone() { return phone; }

    public String getName1() { return name1; }

    public String getPrice() { return price; }
}



