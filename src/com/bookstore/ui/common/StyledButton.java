package com.bookstore.ui.common;

import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {

    
    public StyledButton(String text, Dimension size) {
        super(text);
        init(size);
    }

 
    public StyledButton(String text) {
        super(text);
        init(null);
    }

    private void init(Dimension size) {
        setFont(Theme.BTN);
        setBackground(Theme.BUTTON_BG);
        setForeground(Theme.BUTTON_FG);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        if (size != null) {
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);
        } else {
            setPreferredSize(new Dimension(120, 38));
        }
    }
}
