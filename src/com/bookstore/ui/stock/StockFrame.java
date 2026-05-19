package com.bookstore.ui.stock;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.BookDAO.BookRow;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.TableStyler;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.common.WindowManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StockFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private BookDAO dao = new BookDAO();

    public StockFrame() {

        setTitle("Stock Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIUtils.setAppIcon(this);

        //  Register this window in WindowManager
        WindowManager.register(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                WindowManager.unregister(StockFrame.this);
            }
        });

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        wrapper.add(new HeaderPanel("Stock Management", "Monitor and update stock levels"), BorderLayout.NORTH);

        table = new JTable();
        model = new DefaultTableModel(
                new Object[]{"ID", "Title", "Qty", "Category", "Supplier"}, 0
        );
        table.setModel(model);

        TableStyler.apply(table);

        JScrollPane scroll = new JScrollPane(table);
        wrapper.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        StyledButton increase = new StyledButton("Increase Stock");
        StyledButton decrease = new StyledButton("Decrease Stock");
        StyledButton refresh = new StyledButton("Refresh");

        btnPanel.add(increase);
        btnPanel.add(decrease);
        btnPanel.add(refresh);

        wrapper.add(btnPanel, BorderLayout.SOUTH);

        increase.addActionListener(e -> showIncreaseWindow());
        decrease.addActionListener(e -> showDecreaseWindow());
        refresh.addActionListener(e -> loadStock());

        loadStock();

        add(wrapper);
        setVisible(true);
    }

    // LOAD STOCK
    private void loadStock() {
        model.setRowCount(0);
        List<BookRow> list = dao.getAllBookRows();

        for (BookRow r : list) {
            model.addRow(new Object[]{
                    r.id, r.title, r.quantity, r.category, r.supplier
            });
        }
    }

    // INCREASE STOCK POPUP
  
    private void showIncreaseWindow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int currentQty = Integer.parseInt(model.getValueAt(row, 2).toString());

        String input = JOptionPane.showInputDialog(this, "Increase by:", "0");

        if (input == null) return;

        try {
            int add = Integer.parseInt(input);
            if (add <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a valid number");
                return;
            }

            dao.updateQuantity(id, currentQty + add);
            loadStock();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number");
        }
    }

    // DECREASE STOCK POPUP
    private void showDecreaseWindow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int currentQty = Integer.parseInt(model.getValueAt(row, 2).toString());

        String input = JOptionPane.showInputDialog(this, "Decrease by:", "0");

        if (input == null) return;

        try {
            int dec = Integer.parseInt(input);
            if (dec <= 0 || dec > currentQty) {
                JOptionPane.showMessageDialog(this, "Invalid quantity");
                return;
            }

            dao.updateQuantity(id, currentQty - dec);
            loadStock();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number");
        }
    }
}