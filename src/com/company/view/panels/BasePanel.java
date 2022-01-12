package com.company.view.panels;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    protected static JFrame window;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    protected final Font textFont = new Font("Century Gothic", Font.BOLD, 25);
    protected final Font btnFont = new Font("Franklin Gothic Medium", Font.BOLD, 18);
    protected final Color bgColor = new Color(135, 206, 250);
    protected final Color btnColor = new Color(30, 144, 255);

    public BasePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        setBackground(bgColor);
    }
    public void addFrame(JFrame frame) { window = frame; }
}
