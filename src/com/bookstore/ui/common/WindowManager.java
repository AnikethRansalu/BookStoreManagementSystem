package com.bookstore.ui.common;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class WindowManager {

    private static final List<JFrame> openFrames = new ArrayList<>();

    public static void register(JFrame frame) {
        openFrames.add(frame);
    }

    public static void unregister(JFrame frame) {
        openFrames.remove(frame);
    }

    public static void closeAll() {
        
        List<JFrame> copy = new ArrayList<>(openFrames);
        for (JFrame f : copy) {
            if (f != null) f.dispose();
        }
        openFrames.clear();
    }
}