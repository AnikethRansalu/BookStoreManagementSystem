package com.bookstore.ui.books;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.SupplierDAO;
import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.model.Supplier;
import com.bookstore.ui.common.FormUtils;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.common.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddBookFrame extends JFrame {

    private JTextField txtTitle, txtAuthor, txtPrice, txtQty;
    private JComboBox<Category> cmbCategory;
    private JComboBox<Supplier> cmbSupplier;
    private final Consumer<Void> onSaved;

    public AddBookFrame(Consumer<Void> onSaved) {
        this.onSaved = onSaved;
        initUI();
    }

    private void initUI() {
        setTitle("Add Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
        UIUtils.setAppIcon(this);

        //  Register window
        WindowManager.register(this);

        //  Correct listener (windowClosing instead of windowClosed)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                WindowManager.unregister(AddBookFrame.this);
            }
        });

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        wrapper.add(new HeaderPanel("Add Book", ""));
        wrapper.add(Box.createRigidArea(new Dimension(0,10)));

        txtTitle = FormUtils.labeledTextField("Title:", 300);
        txtAuthor = FormUtils.labeledTextField("Author:", 300);
        txtPrice = FormUtils.labeledTextField("Price:", 200);
        txtQty = FormUtils.labeledTextField("Quantity:", 200);

        cmbCategory = FormUtils.labeledComboBox("Category:", 300);
        cmbSupplier = FormUtils.labeledComboBox("Supplier:", 300);

        wrapper.add(txtTitle.getParent());
        wrapper.add(txtAuthor.getParent());
        wrapper.add(txtPrice.getParent());
        wrapper.add(txtQty.getParent());
        wrapper.add(cmbCategory.getParent());
        wrapper.add(cmbSupplier.getParent());

        loadLookups();

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        StyledButton save = new StyledButton("Save", new Dimension(120,38));
        StyledButton cancel = new StyledButton("Cancel", new Dimension(120,38));

        save.addActionListener(e -> saveBook());
        cancel.addActionListener(e -> dispose());

        btns.add(save);
        btns.add(cancel);

        wrapper.add(Box.createRigidArea(new Dimension(0,10)));
        wrapper.add(btns);

        add(wrapper);
    }

    private void loadLookups() {
        CategoryDAO cdao = new CategoryDAO();
        DefaultComboBoxModel<Category> m1 = new DefaultComboBoxModel<>();
        for (Category c : cdao.getAllCategories()) m1.addElement(c);
        cmbCategory.setModel(m1);

        SupplierDAO sdao = new SupplierDAO();
        DefaultComboBoxModel<Supplier> m2 = new DefaultComboBoxModel<>();
        for (Supplier s : sdao.getAllSuppliers()) m2.addElement(s);
        cmbSupplier.setModel(m2);
    }

    private void saveBook() {
        try {
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int qty = Integer.parseInt(txtQty.getText().trim());
            Category c = (Category) cmbCategory.getSelectedItem();
            Supplier s = (Supplier) cmbSupplier.getSelectedItem();

            if (title.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter title"); return; }

            Book b = new Book(title, author, price, qty, c.getId(), s.getId());

            if (new BookDAO().addBook(b)) {
                JOptionPane.showMessageDialog(this,"Saved");
                if (onSaved != null) onSaved.accept(null);
                dispose(); // windowClosing triggers unregister
            } else {
                JOptionPane.showMessageDialog(this, "Save failed");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Invalid number input");
        }
    }
}