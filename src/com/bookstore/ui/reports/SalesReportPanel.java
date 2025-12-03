package com.bookstore.ui.reports;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.BookDAO.BookRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesReportPanel extends JPanel {

    private JTable table;
    private JLabel lblTotalValue;

    public SalesReportPanel() {

        setLayout(new BorderLayout());

        table = new JTable();
        loadSales();

        lblTotalValue = new JLabel("Total Inventory Value: Rs. 0.00");
        lblTotalValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(lblTotalValue, BorderLayout.SOUTH);
    }

    private void loadSales() {
        BookDAO dao = new BookDAO();
        List<BookRow> list = dao.getAllBookRows();

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Title", "Author", "Price", "Qty", "Category", "Supplier"}, 0);

        double total = 0;

        for (BookRow b : list) {
            model.addRow(new Object[]{
                    b.id, b.title, b.author, b.price, b.quantity,
                    b.category, b.supplier
            });

            total += b.price * b.quantity;
        }

        table.setModel(model);
        lblTotalValue.setText("Total Inventory Value: Rs. " + total);
    }
}
