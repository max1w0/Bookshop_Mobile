package com.example.bookshop_mobile.model;

public class Product {
    private int id;
    private String name;
    private String genre;
    private double price;
    private int imageRes;

    public Product(int id, String name, String genre, double price, int imageRes) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.imageRes = imageRes;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public double getPrice() { return price; }
    public int getImageRes() { return imageRes; }
}
