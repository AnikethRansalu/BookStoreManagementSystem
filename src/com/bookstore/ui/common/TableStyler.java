package com.bookstore.ui.common;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class TableStyler {
    private TableStyler(){}

    
    public static void apply(JTable table) {
        if (table == null) return;
        table.setRowHeight(28);
        table.setFont(Theme.TABLE);
        table.setGridColor(Theme.BORDER);

        JTableHeader header = table.getTableHeader();
        header.setFont(Theme.TABLE_HEADER);
        header.setBackground(Theme.CARD);
        header.setReorderingAllowed(false);

        
        int qtyCol = -1;
        for (int i = 0; i < table.getColumnCount(); i++) {
            String name = table.getColumnName(i).toLowerCase();
            if (name.contains("qty") || name.contains("quantity")) { qtyCol = i; break; }
        }

        if (qtyCol != -1) {
            final int qc = qtyCol;
            DefaultTableCellRenderer lowStockRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                    try {
                        int q = Integer.parseInt(String.valueOf(tbl.getValueAt(row, qc)));
                        if (!isSelected && q < 10) {
                            c.setBackground(Theme.LOW_STOCK);
                        } else {
                            c.setBackground(Color.WHITE);
                        }
                    } catch (Exception ex) {
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            };
            table.getColumnModel().getColumn(qc).setCellRenderer(lowStockRenderer);
        }

        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        
    }
}
