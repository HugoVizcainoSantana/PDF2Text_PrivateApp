package ocrGUI.view;

import ocrGUI.model.AppImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class PageViewPanel extends JPanel {
    private final List pages;
    private AppImage currentPage;
    private Shape selection = null;
    private Point startSelection, endSelection;

    public PageViewPanel(List<AppImage> pages) {
        this.pages = pages;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startSelection = new Point(e.getX(), e.getY());
                endSelection = startSelection;
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (endSelection != null && startSelection != null) {
                    try {
                        selection = createSelectionRectangle(startSelection.x, startSelection.y, e.getX(),
                                e.getY());
                        // TODO: Add selected region to JList of selected Regions. This list should be passed by argunment to this panel
                        // mf.updateSelectedRegion(currentPage.getImage().getSubimage(startSelection.x, startSelection.y, e.getX()- startSelection.x, e.getY()- startSelection.y));
                        startSelection = null;
                        endSelection = null;
                        repaint();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                endSelection = new Point(e.getX(), e.getY());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(currentPage.getImage(), 0, 0, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.50f));

        if (selection != null) {
            g2.setPaint(Color.BLACK);
            g2.draw(selection);
            g2.setPaint(Color.YELLOW);
            g2.fill(selection);
        }

        if (startSelection != null && endSelection != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            Shape r = createSelectionRectangle(startSelection.x, startSelection.y, endSelection.x,
                    endSelection.y);
            g2.draw(r);
        }
    }

    private Rectangle2D.Float createSelectionRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
}
