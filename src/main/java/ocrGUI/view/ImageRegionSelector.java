package ocrGUI.view;

import ocrGUI.model.AppImage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ImageRegionSelector {

    private final List<AppImage> images;
    private final JFrame window;

    public ImageRegionSelector(JFrame window, List<AppImage> images) {
        this.window = window;
        this.images = images;

    }

    public void show() {
        window.setTitle("Pages Region Selector");
        window.setExtendedState(Frame.MAXIMIZED_BOTH);
        //Left Panel
        JPanel pagelistPanel = new JPanel();
        pagelistPanel.add(new CustomImageList(images));
        //Right Panel
        JPanel regionsSelectedPanel = new JPanel();
        regionsSelectedPanel.add(new JList<AppImage>());
        //Final build
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(pagelistPanel, BorderLayout.WEST);
        mainPanel.add(regionsSelectedPanel, BorderLayout.EAST);
        window.setContentPane(mainPanel);
        window.pack();
        window.setVisible(true);
    }

}
