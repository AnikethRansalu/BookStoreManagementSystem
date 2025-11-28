package com.bookstore.ui.reports;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.BookDAO.BookRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StockReportFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public StockReportFrame() {

        setTitle("Low Stock Report");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(wrapper);

        JLabel title = new JLabel("Low Stock Report (Quantity < 5)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        wrapper.add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{
                "ID", "Title", "Author", "Qty", "Category", "Supplier"
        }, 0);

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);

        JScrollPane tableScroll = new JScrollPane(table);
        wrapper.add(tableScroll, BorderLayout.CENTER);

        loadLowStock();

        setVisible(true);
    }

    private void loadLowStock() {
        model.setRowCount(0);

        BookDAO dao = new BookDAO();
        List<BookRow> list = dao.getAllBookRows();

        for (BookRow b : list) {
            if (b.quantity < 5) {
                model.addRow(new Object[]{
                        b.id, b.title, b.author, b.quantity, b.category, b.supplier
                });
            }
        }
    }
}
