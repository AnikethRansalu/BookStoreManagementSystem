package com.bookstore.ui.login;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;
import com.bookstore.ui.common.FormUtils;
import com.bookstore.ui.common.HeaderPanel;
import com.bookstore.ui.common.StyledButton;
import com.bookstore.ui.common.UIUtils;
import com.bookstore.ui.dashboard.DashboardFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Bookstore Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        UIUtils.setAppIcon(this);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        wrapper.add(new HeaderPanel("Bookstore Login", "Enter your credentials"));
        wrapper.add(Box.createRigidArea(new Dimension(0,10)));

        txtUsername = FormUtils.labeledTextField("Username:", 280);
        txtPassword = new JPasswordField();
        JPanel pPass = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPass.add(new JLabel("Password:"));
        txtPassword.setPreferredSize(new Dimension(280,28));
        pPass.add(txtPassword);

        wrapper.add(txtUsername.getParent());
        wrapper.add(pPass);

        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        wrapper.add(lblError);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        StyledButton btnLogin = new StyledButton("Login", new Dimension(120,38));
        btnLogin.addActionListener(e -> login());
        btns.add(btnLogin);
        wrapper.add(Box.createRigidArea(new Dimension(0,8)));
        wrapper.add(btns);

        add(wrapper);
        setVisible(true);
    }

    private void login() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();
        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Enter username and password");
            return;
        }
        UserDAO dao = new UserDAO();
        User u = dao.login(user, pass);
        if (u != null) {
            new DashboardFrame(u).setVisible(true);
            dispose();
        } else {
            lblError.setText("Invalid credentials");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
