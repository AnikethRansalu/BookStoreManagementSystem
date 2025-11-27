package com.bookstore.dao;

import com.bookstore.model.Book;
import com.bookstore.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Add book
    public boolean addBook(Book b) {
        String sql = "INSERT INTO books (title, author, price, quantity, category_id, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getQuantity());
            ps.setInt(5, b.getCategoryId());
            ps.setInt(6, b.getSupplierId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update full book
    public boolean updateBook(Book b) {
        String sql = "UPDATE books SET title=?, author=?, price=?, quantity=?, category_id=?, supplier_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getQuantity());
            ps.setInt(5, b.getCategoryId());
            ps.setInt(6, b.getSupplierId());
            ps.setInt(7, b.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("category_id"),
                        rs.getInt("supplier_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Stock update only
    public boolean updateQuantity(int bookId, int newQty) {
        if (newQty < 0) newQty = 0;  // prevent negative stock

        String sql = "UPDATE books SET quantity=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQty);
            ps.setInt(2, bookId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get list with category/supplier names
    public List<BookRow> getAllBookRows() {
        List<BookRow> rows = new ArrayList<>();
        String sql =
                "SELECT b.id, b.title, b.author, b.price, b.quantity, " +
                        "c.name AS category_name, s.name AS supplier_name " +
                        "FROM books b " +
                        "LEFT JOIN categories c ON b.category_id = c.id " +
                        "LEFT JOIN suppliers s ON b.supplier_id = s.id " +
                        "ORDER BY b.id DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                rows.add(new BookRow(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category_name"),
                        rs.getString("supplier_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    // DTO for UI table
    public static class BookRow {
        public int id;
        public String title;
        public String author;
        public double price;
        public int quantity;
        public String category;
        public String supplier;

        public BookRow(int id, String title, String author, double price, int quantity, String category, String supplier) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
            this.quantity = quantity;
            this.category = category == null ? "-" : category;
            this.supplier = supplier == null ? "-" : supplier;
        }
    }
}
