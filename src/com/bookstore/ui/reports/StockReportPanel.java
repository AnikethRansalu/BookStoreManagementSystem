package com.bookstore.ui.reports;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.BookDAO.BookRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StockReportPanel extends JPanel {

    private JTable table;

    public StockReportPanel() {

        setLayout(new BorderLayout());
        table = new JTable();

        loadStock();

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadStock() {
        BookDAO dao = new BookDAO();
        List<BookRow> list = dao.getAllBookRows();

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Title", "Qty", "Category", "Supplier", "Status"}, 0);

        for (BookRow b : list) {

            String status;

            if (b.quantity == 0) status = "OUT OF STOCK";
            else if (b.quantity < 5) status = "LOW STOCK";
            else status = "OK";

            if (!status.equals("OK")) {
                model.addRow(new Object[]{
                        b.id, b.title, b.quantity, b.category, b.supplier, status
                });
            }
        }

        table.setModel(model);
    }
}
