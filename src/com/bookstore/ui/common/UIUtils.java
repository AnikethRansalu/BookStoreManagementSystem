package com.bookstore.ui.common;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public final class UIUtils {

    private UIUtils() {}

    public static void setAppIcon(Window w) {
        try {
            // Load icon file from project resources
            ImageIcon icon = new ImageIcon(
                    UIUtils.class.getResource("/images/app_icon.png")
            );
            
            // Set the window icon
            w.setIconImage(icon.getImage());

        } catch (Exception ex) {
            System.err.println("App icon missing: /images/app_icon.png");
        }
    }

    /**
     * Center window with given size
     */
    public static void center(Window w, int width, int height) {
        w.setSize(width, height);
        w.setLocationRelativeTo(null);
    }

    /**
     * Export JTable to CSV
     */
    public static void exportTableToCSV(JTable table, Component parent) {
        if (table == null || table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent, "Table is empty.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("report.csv"));
        int res = chooser.showSaveDialog(parent);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {

            TableModel model = table.getModel();

            // Write header
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) writer.write(",");
            }
            writer.write("\n");

            // Write rows
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object value = model.getValueAt(r, c);
                    String cell = value == null ? "" : value.toString().replace("\"", "\"\"");

                    if (cell.contains(",") || cell.contains("\n")) {
                        writer.write("\"" + cell + "\"");
                    } else {
                        writer.write(cell);
                    }

                    if (c < model.getColumnCount() - 1) writer.write(",");
                }
                writer.write("\n");
            }

            JOptionPane.showMessageDialog(parent,
                    "Exported successfully:\n" + file.getAbsolutePath());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Error exporting: " + ex.getMessage());
        }
    }
}