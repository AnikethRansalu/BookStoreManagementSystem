package com.bookstore.ui.common;

import java.awt.*;

public final class Theme {
    private Theme(){}

    // Colors
    public static final Color BACKGROUND = Color.WHITE;
    public static final Color CARD = new Color(250, 250, 250);
    public static final Color BORDER = new Color(220, 220, 220);
    public static final Color BUTTON_BG = new Color(240, 240, 240);
    public static final Color BUTTON_FG = Color.BLACK;
    public static final Color HEADER_TEXT = new Color(40, 40, 40);
    public static final Color LOW_STOCK = new Color(255, 200, 200);

    // Fonts
    public static final Font H1 = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font H2 = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font BTN = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TABLE = new Font("Segoe UI", Font.PLAIN, 13);
}
