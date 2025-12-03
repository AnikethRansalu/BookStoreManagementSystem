package com.bookstore.ui.suppliers;

import com.bookstore.dao.SupplierDAO;
import com.bookstore.model.Supplier;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.FormUtils;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.TableStyler;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.common.WindowManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtContact;
    private JTextArea txtAddress;
    private int selectedId = -1;
    private final SupplierDAO dao = new SupplierDAO();

    public SupplierFrame() {
        initUI();
        WindowManager.register(this);  // register frame
    }

    @Override
    public void dispose() {
        WindowManager.unregister(this); // unregister frame
        super.dispose();
    }

    private void initUI() {
        setTitle("Suppliers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        UIUtils.setAppIcon(this);

        JPanel wrapper = new JPanel(new BorderLayout(12,12));
        wrapper.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        wrapper.add(new HeaderPanel("Suppliers", "Manage suppliers"), BorderLayout.NORTH);

        // LEFT FORM
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        txtName = FormUtils.labeledTextField("Name:", 300);
        txtContact = (JTextField) FormUtils.labeledTextField("Contact:", 300);
        txtAddress = new JTextArea(4, 20);
        JScrollPane addrScroll = new JScrollPane(txtAddress);

        left.add(txtName.getParent());
        left.add(Box.createRigidArea(new Dimension(0,10)));
        left.add(txtContact.getParent());
        left.add(Box.createRigidArea(new Dimension(0,10)));
        left.add(new JLabel("Address:"));
        left.add(addrScroll);
        left.add(Box.createRigidArea(new Dimension(0,12)));

        StyledButton btnAdd = new StyledButton("Add");
        StyledButton btnUpdate = new StyledButton("Update");
        StyledButton btnDelete = new StyledButton("Delete");

        btnAdd.addActionListener(e -> addSupplier());
        btnUpdate.addActionListener(e -> updateSupplier());
        btnDelete.addActionListener(e -> deleteSupplier());

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btns.add(btnAdd); btns.add(btnUpdate); btns.add(btnDelete);
        left.add(btns);

        wrapper.add(left, BorderLayout.WEST);

        // TABLE
        model = new DefaultTableModel(new String[]{"ID","Name","Contact","Address"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        TableStyler.apply(table);

        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> onRowSelect());

        add(wrapper);
        loadSuppliers();
        setVisible(true);
    }

    private void loadSuppliers() {
        model.setRowCount(0);
        List<Supplier> list = dao.getAllSuppliers();
        for (Supplier s : list) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getContact(), s.getAddress()});
        }
    }

    private void onRowSelect() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            selectedId = (int) model.getValueAt(r,0);
            txtName.setText(model.getValueAt(r,1).toString());
            txtContact.setText(model.getValueAt(r,2).toString());
            txtAddress.setText(model.getValueAt(r,3).toString());
        }
    }

    private void addSupplier() {
        String name = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String addr = txtAddress.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Enter name");
            return;
        }

        dao.addSupplier(new Supplier(name, contact, addr));
        loadSuppliers();
        clearForm();
    }

    private void updateSupplier() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this,"Select supplier");
            return;
        }

        Supplier s = new Supplier(selectedId,
                txtName.getText().trim(),
                txtContact.getText().trim(),
                txtAddress.getText().trim()
        );

        dao.updateSupplier(s);
        loadSuppliers();
        clearForm();
    }

    private void deleteSupplier() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this,"Select supplier");
            return;
        }

        dao.deleteSupplier(selectedId);
        loadSuppliers();
        clearForm();
    }

    private void clearForm() {
        txtName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        selectedId = -1;
        table.clearSelection();
    }

}