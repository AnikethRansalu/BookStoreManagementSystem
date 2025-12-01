package com.bookstore.ui.common;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    public HeaderPanel(String title) {
        this(title, null);
    }

    public HeaderPanel(String title, String subtitle) {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(Theme.H1);
        lblTitle.setForeground(Theme.HEADER_TEXT);

        add(lblTitle, BorderLayout.WEST);

        if (subtitle != null && !subtitle.isEmpty()) {
            JLabel lblSub = new JLabel(subtitle);
            lblSub.setFont(Theme.H2);
            lblSub.setForeground(Theme.HEADER_TEXT);
            add(lblSub, BorderLayout.SOUTH);
        }
    }
}
