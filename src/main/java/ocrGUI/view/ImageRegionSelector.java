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
        //Center panel
        JPanel pagePanel = new JPanel();
        pagePanel.add(new PageViewPanel(images));
        //Final build
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c;
        // Add left panel
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 1;
        mainPanel.add(pagelistPanel, c);
        // Add right panel
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.2;
        c.weighty = 1;
        mainPanel.add(regionsSelectedPanel, c);
        window.setContentPane(mainPanel);
        window.pack();
        window.setVisible(true);
    }

}
