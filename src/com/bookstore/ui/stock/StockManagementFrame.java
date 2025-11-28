package com.bookstore.ui.stock;

import com.bookstore.dao.BookDAO;
import com.bookstore.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StockManagementFrame extends JFrame {

    private JComboBox<Book> cmbBooks;
    private JTextField txtCurrentQty;
    private JTextField txtChangeQty;
    private JTable tblStock;
    private BookDAO bookDAO = new BookDAO();

    public StockManagementFrame() {
        setTitle("Stock Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cmbBooks = new JComboBox<>();
        txtCurrentQty = new JTextField();
        txtCurrentQty.setEditable(false);

        txtChangeQty = new JTextField();

        topPanel.add(new JLabel("Book:"));
        topPanel.add(cmbBooks);
        topPanel.add(new JLabel("Current Stock:"));
        topPanel.add(txtCurrentQty);
        topPanel.add(new JLabel("Change Qty:"));
        topPanel.add(txtChangeQty);

        add(topPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Add Stock");
        JButton btnRemove = new JButton("Reduce Stock");

        btnPanel.add(btnAdd);
        btnPanel.add(btnRemove);

        add(btnPanel, BorderLayout.CENTER);

        // Load books
        loadBooks();

        btnAdd.addActionListener(e -> addStock());
        btnRemove.addActionListener(e -> reduceStock());

        setVisible(true);
    }

    private void loadBooks() {
        List<Book> list = bookDAO.getAllBooks();
        for (Book b : list) {
            cmbBooks.addItem(b);
        }
    }

    private void addStock() {
        Book b = (Book) cmbBooks.getSelectedItem();
        int change = Integer.parseInt(txtChangeQty.getText());
        int newQty = b.getQuantity() + change;

        b.setQuantity(newQty);
        bookDAO.updateBook(b);

        JOptionPane.showMessageDialog(this, "Stock Added!");
    }

    private void reduceStock() {
        Book b = (Book) cmbBooks.getSelectedItem();
        int change = Integer.parseInt(txtChangeQty.getText());

        if (change > b.getQuantity()) {
            JOptionPane.showMessageDialog(this, "Not enough stock!");
            return;
        }

        int newQty = b.getQuantity() - change;
        b.setQuantity(newQty);
        bookDAO.updateBook(b);

        JOptionPane.showMessageDialog(this, "Stock Reduced!");
    }
}
