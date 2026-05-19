package com.bookstore.ui.categories;

import com.bookstore.dao.CategoryDAO;
import com.bookstore.model.Category;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.TableStyler;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.FormUtils;
import com.bookstore.ui.common.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryFrame extends JFrame {

    private JTextField txtName;
    private JTextArea txtDescription;
    private JTable tblCategories;
    private DefaultTableModel model;
    private int selectedId = -1;
    private final CategoryDAO dao = new CategoryDAO();

    public CategoryFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Categories");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // auto closes on logout
        UIUtils.setAppIcon(this);

        JPanel wrapper = new JPanel(new BorderLayout(10,10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        // HEADER
        wrapper.add(new HeaderPanel("Categories", "Manage category list"), BorderLayout.NORTH);

        // ---------------- LEFT SIDE FORM ----------------
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        txtName = FormUtils.labeledTextField("Name:", 300);

        txtDescription = new JTextArea(4, 20);
        JScrollPane descScroll = new JScrollPane(txtDescription);

        left.add(txtName.getParent());
        left.add(Box.createRigidArea(new Dimension(0, 10)));

        left.add(new JLabel("Description:"));
        left.add(descScroll);
        left.add(Box.createRigidArea(new Dimension(0, 15)));

        // Buttons
        StyledButton btnAdd = new StyledButton("Add");
        StyledButton btnUpdate = new StyledButton("Update");
        StyledButton btnDelete = new StyledButton("Delete");
        StyledButton btnClear = new StyledButton("Clear");

        btnAdd.addActionListener(e -> addCategory());
        btnUpdate.addActionListener(e -> updateCategory());
        btnDelete.addActionListener(e -> deleteCategory());
        btnClear.addActionListener(e -> clearForm());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        left.add(btnPanel);

        wrapper.add(left, BorderLayout.WEST);

        // ---------------- TABLE ----------------
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Description"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblCategories = new JTable(model);
        TableStyler.apply(tblCategories);

        JScrollPane tableScroll = new JScrollPane(tblCategories);
        wrapper.add(tableScroll, BorderLayout.CENTER);

        // on row select
        tblCategories.getSelectionModel().addListSelectionListener(e -> fillSelection());

        add(wrapper);

        loadCategories();
        setVisible(true);
    }

    private void loadCategories() {
        model.setRowCount(0);
        List<Category> list = dao.getAllCategories();
        for (Category c : list) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getDescription()});
        }
    }

    private void addCategory() {
        String name = txtName.getText().trim();
        String desc = txtDescription.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter name");
            return;
        }

        dao.addCategory(new Category(name, desc));
        loadCategories();
        clearForm();
    }

    private void updateCategory() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select category");
            return;
        }

        dao.updateCategory(new Category(selectedId, txtName.getText().trim(), txtDescription.getText().trim()));
        loadCategories();
        clearForm();
    }

    private void deleteCategory() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select category");
            return;
        }

        dao.deleteCategory(selectedId);
        loadCategories();
        clearForm();
    }

    private void fillSelection() {
        int row = tblCategories.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) model.getValueAt(row, 0);
            txtName.setText(model.getValueAt(row, 1).toString());
            txtDescription.setText(model.getValueAt(row, 2).toString());
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtDescription.setText("");
        selectedId = -1;
        tblCategories.clearSelection();
    }
}