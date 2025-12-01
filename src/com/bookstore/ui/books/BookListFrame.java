package com.bookstore.ui.books;

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

public class BookListFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public BookListFrame() {

        WindowManager.register(this);   // <<--- AUTO CLOSE SUPPORT

        setTitle("Manage Books");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UIUtils.setAppIcon(this);

        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        wrapper.add(new HeaderPanel("Books", "Manage your book inventory"), BorderLayout.NORTH);

        table = new JTable();
        model = new DefaultTableModel(new Object[]{
                "ID", "Title", "Author", "Price", "Qty", "Category", "Supplier"
        }, 0);

        table.setModel(model);
        TableStyler.apply(table);

        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        StyledButton add = new StyledButton("Add Book");
        StyledButton edit = new StyledButton("Edit Book");
        StyledButton del = new StyledButton("Delete Book");
        StyledButton refresh = new StyledButton("Refresh");

        btnPanel.add(add);
        btnPanel.add(edit);
        btnPanel.add(del);
        btnPanel.add(refresh);

        wrapper.add(btnPanel, BorderLayout.SOUTH);

        add.addActionListener(e -> openAddWindow());
        edit.addActionListener(e -> openEditWindow());
        del.addActionListener(e -> deleteBook());
        refresh.addActionListener(e -> loadBooks());

        loadBooks();

        add(wrapper);
        setVisible(true);
    }

    public void loadBooks() {
        model.setRowCount(0);
        BookDAO dao = new BookDAO();
        List<BookRow> list = dao.getAllBookRows();

        for (BookRow r : list) {
            model.addRow(new Object[]{
                    r.id, r.title, r.author, r.price, r.quantity, r.category, r.supplier
            });
        }
    }

    private void openAddWindow() {
        new AddBookFrame(v -> loadBooks()).setVisible(true);
    }

    private void openEditWindow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        new EditBookFrame(id, v -> loadBooks()).setVisible(true);
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        if (JOptionPane.showConfirmDialog(this, "Delete selected book?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            BookDAO dao = new BookDAO();
            dao.deleteBook(id);
            loadBooks();
        }
    }
}