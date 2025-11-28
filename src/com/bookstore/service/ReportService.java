package com.bookstore.service;

import com.bookstore.utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportService {

    // Count books
    public int countBooks() {
        return getCount("SELECT COUNT(*) AS total FROM books");
    }

    // Count categories
    public int countCategories() {
        return getCount("SELECT COUNT(*) AS total FROM categories");
    }

    // Count suppliers
    public int countSuppliers() {
        return getCount("SELECT COUNT(*) AS total FROM suppliers");
    }

    // Count low-stock books (< 10 qty)
    public int countLowStock() {
        return getCount("SELECT COUNT(*) AS total FROM books WHERE quantity < 10");
    }

    // Helper
    private int getCount(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
