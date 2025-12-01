package com.bookstore.ui.dashboard;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.SupplierDAO;
import com.bookstore.dao.BookDAO.BookRow;
import com.bookstore.model.User;
import com.bookstore.ui.books.BookListFrame;
import com.bookstore.ui.categories.CategoryFrame;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.TableStyler;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.reports.ReportsFrame;
import com.bookstore.ui.stock.StockFrame;
import com.bookstore.ui.suppliers.SupplierFrame;
import com.bookstore.ui.login.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardFrame extends JFrame {

    private final User loggedUser;

    private JLabel lblBookCount;
    private JLabel lblCategoryCount;
    private JLabel lblSupplierCount;
    private JLabel lblLowStockCount;

    private JTable tblLowStock;
    private DefaultTableModel lowStockModel;

   
    private final List<JFrame> openedFrames = new ArrayList<>();

    public DashboardFrame(User user) {
        this.loggedUser = user;

        setTitle("Dashboard - Bookstore Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        UIUtils.setAppIcon(this);

        initUI();
        refreshDashboard();

        setVisible(true);
    }

    private void initUI() {

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.setBackground(Color.WHITE);

        // Header
        HeaderPanel header = new HeaderPanel("Dashboard - Welcome, " + loggedUser.getUsername());
        main.add(header, BorderLayout.NORTH);

        // NAVIGATION PANEL
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        navPanel.setBackground(Color.WHITE);

        Dimension navBtnSize = new Dimension(220, 45);

        StyledButton btnCategories = createNavButton("Manage Categories", navBtnSize);
        StyledButton btnSuppliers = createNavButton("Manage Suppliers", navBtnSize);
        StyledButton btnBooks = createNavButton("Manage Books", navBtnSize);
        StyledButton btnStock = createNavButton("Stock Management", navBtnSize);
        StyledButton btnReports = createNavButton("Reports", navBtnSize);
        StyledButton btnLogout = createNavButton("Logout", navBtnSize);

        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnCategories);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnSuppliers);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnBooks);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnStock);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnReports);
        navPanel.add(Box.createVerticalGlue());
        navPanel.add(btnLogout);

        main.add(navPanel, BorderLayout.WEST);

    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Summary row
        JPanel summaryRow = new JPanel(new GridLayout(1, 4, 10, 10));
        summaryRow.setOpaque(false);

        lblBookCount = createSummaryCard();
        lblCategoryCount = createSummaryCard();
        lblSupplierCount = createSummaryCard();
        lblLowStockCount = createSummaryCard();

        summaryRow.add(wrapSummaryCard("Books", lblBookCount));
        summaryRow.add(wrapSummaryCard("Categories", lblCategoryCount));
        summaryRow.add(wrapSummaryCard("Suppliers", lblSupplierCount));
        summaryRow.add(wrapSummaryCard("Low Stock", lblLowStockCount));

        centerPanel.add(summaryRow);
        centerPanel.add(Box.createVerticalStrut(15));

        // Low Stock section
        JPanel lowStockPanel = new JPanel(new BorderLayout(5, 5));
        lowStockPanel.setBorder(BorderFactory.createTitledBorder("Low Stock Items"));
        lowStockPanel.setBackground(Color.WHITE);

        tblLowStock = new JTable();
        lowStockModel = new DefaultTableModel(
                new Object[]{"ID", "Title", "Qty", "Category"}, 0
        );
        tblLowStock.setModel(lowStockModel);
        TableStyler.apply(tblLowStock);

        JScrollPane scroll = new JScrollPane(tblLowStock);
        scroll.setPreferredSize(new Dimension(600, 180));
        lowStockPanel.add(scroll, BorderLayout.CENTER);

        StyledButton btnRefresh = new StyledButton("Refresh");
        btnRefresh.addActionListener(e -> refreshDashboard());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(btnRefresh);

        lowStockPanel.add(bottom, BorderLayout.SOUTH);

        centerPanel.add(lowStockPanel);

        main.add(centerPanel, BorderLayout.CENTER);

        //BUTTON ACTIONS 

        btnCategories.addActionListener(e -> openFrame(new CategoryFrame()));
        btnSuppliers.addActionListener(e -> openFrame(new SupplierFrame()));
        btnBooks.addActionListener(e -> openFrame(new BookListFrame()));
        btnStock.addActionListener(e -> openFrame(new StockFrame()));
        btnReports.addActionListener(e -> openFrame(new ReportsFrame()));

        btnLogout.addActionListener(e -> logout());

        setContentPane(main);
    }

    private StyledButton createNavButton(String text, Dimension size) {
        StyledButton btn = new StyledButton(text);
        btn.setPreferredSize(size);
        btn.setMaximumSize(size);
        return btn;
    }

    private JLabel createSummaryCard() {
        JLabel lbl = new JLabel("0", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        return lbl;
    }

    private JPanel wrapSummaryCard(String title, JLabel value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(248, 249, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(80, 80, 80));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    // Track and open frames
    private void openFrame(JFrame frame) {
        openedFrames.add(frame);
        frame.setVisible(true);
    }

    // This closes ALL open windows before logging out
    private void logout() {
        // Close all opened management windows
        for (JFrame f : openedFrames) {
            try { f.dispose(); } catch (Exception ignored) {}
        }
        openedFrames.clear();

        // Close dashboard
        dispose();

        // Open login window
        new LoginFrame();
    }

    private void refreshDashboard() {

        BookDAO bookDAO = new BookDAO();
        CategoryDAO catDAO = new CategoryDAO();
        SupplierDAO supDAO = new SupplierDAO();

        lblBookCount.setText(String.valueOf(bookDAO.getAllBooks().size()));
        lblCategoryCount.setText(String.valueOf(catDAO.getAllCategories().size()));
        lblSupplierCount.setText(String.valueOf(supDAO.getAllSuppliers().size()));

        lowStockModel.setRowCount(0);
        int lowStock = 0;

        for (BookRow r : bookDAO.getAllBookRows()) {
            if (r.quantity < 10) {
                lowStock++;
                lowStockModel.addRow(new Object[]{r.id, r.title, r.quantity, r.category});
            }
        }

        lblLowStockCount.setText(String.valueOf(lowStock));
    }
}