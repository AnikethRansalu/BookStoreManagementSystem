package com.bookstore.ui.common;

import javax.swing.*;
import java.awt.*;

public class FormUtils {

    // ---------------------------------------------------------
    // TEXT FIELD WITH LABEL
    // ---------------------------------------------------------
    public static JTextField labeledTextField(String label, int width) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JLabel lbl = new JLabel(label);
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(width, 28));

        row.add(lbl);
        row.add(txt);

        // attach reference so caller can add row using txt.getParent()
        txt.putClientProperty("row", row);

        return txt;
    }

    // ---------------------------------------------------------
    // COMBOBOX WITH LABEL
    // ---------------------------------------------------------
    public static <T> JComboBox<T> labeledComboBox(String label, int width) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JLabel lbl = new JLabel(label);
        JComboBox<T> combo = new JComboBox<>();
        combo.setPreferredSize(new Dimension(width, 28));

        row.add(lbl);
        row.add(combo);

        combo.putClientProperty("row", row);

        return combo;
    }

    // ---------------------------------------------------------
    // Get panel row for any component returned by FormUtils
    // ---------------------------------------------------------
    public static JPanel getRow(Component comp) {
        return (JPanel) ((JComponent) comp).getClientProperty("row");
    }
}