package com.bookstore.ui.reports;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.SupplierDAO;
import com.bookstore.model.Book;
import com.bookstore.dao.BookDAO.BookRow;
import com.bookstore.model.Category;
import com.bookstore.model.Supplier;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.common.WindowManager;
import com.bookstore.ui.stock.StockFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReportsFrame extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private BookDAO bookDAO = new BookDAO();

    public ReportsFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Reports");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        UIUtils.setAppIcon(this);
        
         //  Register this window in WindowManager
        WindowManager.register(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                WindowManager.unregister(ReportsFrame.this);
            }
        });

        JPanel wrapper = new JPanel(new BorderLayout(10,10));
        wrapper.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        wrapper.add(new HeaderPanel("Reports", "Export and view reports"), BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID","Title","Author","Qty","Category","Supplier"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        wrapper.add(sp, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        StyledButton refresh = new StyledButton("Refresh", new Dimension(120,38));
        StyledButton exportCsv = new StyledButton("Export CSV", new Dimension(120,38));
        refresh.addActionListener(e -> loadReport());
        exportCsv.addActionListener(e -> UIUtils.exportTableToCSV(table, this));
        actions.add(refresh); actions.add(exportCsv);

        wrapper.add(actions, BorderLayout.SOUTH);

        add(wrapper);
        loadReport();
        setVisible(true);
    }

    private void loadReport() {
        model.setRowCount(0);
        List<BookRow> rows = bookDAO.getAllBookRows();
        for (BookRow r : rows) {
            model.addRow(new Object[]{r.id, r.title, r.author, r.quantity, r.category, r.supplier});
        }
    }
}
