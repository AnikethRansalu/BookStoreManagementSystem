package com.bookstore.dao;

import com.bookstore.model.Supplier;
import com.bookstore.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public void addSupplier(Supplier s) {
        String sql = "INSERT INTO suppliers(name, contact, address) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getName());
            stmt.setString(2, s.getContact());
            stmt.setString(3, s.getAddress());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSupplier(Supplier s) {
        String sql = "UPDATE suppliers SET name=?, contact=?, address=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getName());
            stmt.setString(2, s.getContact());
            stmt.setString(3, s.getAddress());
            stmt.setInt(4, s.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
