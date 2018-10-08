package ocrGUI.view;

import ocrGUI.model.AppImage;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class CustomImageList extends JList<AppImage> {


    public CustomImageList(List<AppImage> imageList) {
        super(new Vector<>(imageList));
        setCellRenderer(new CustomImageListRender());
    }

    private class CustomImageListRender extends JLabel implements ListCellRenderer<AppImage> {

        @Override
        public Component getListCellRendererComponent(JList<? extends AppImage> list, AppImage currImg, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setIcon(new ImageIcon(currImg.getImage()));
            setText(currImg.toString());

            return this;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}

