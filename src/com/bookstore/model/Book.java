package com.bookstore.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;
    private int categoryId;
    private int supplierId;

    public Book() {}

    public Book(int id, String title, String author, double price, int quantity, int categoryId, int supplierId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public Book(String title, String author, double price, int quantity, int categoryId, int supplierId) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getCategoryId() { return categoryId; }
    public int getSupplierId() { return supplierId; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
}
