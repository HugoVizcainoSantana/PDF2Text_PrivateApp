package ocrGUI.model;

import java.awt.image.BufferedImage;

public class AppImage {
    private final BufferedImage image;
    private final int page;
    private final Integer slice;

    public AppImage(BufferedImage image, int page, Integer slice) {
        this.image = image;
        this.page = page;
        this.slice = slice;
    }

    public AppImage(BufferedImage image, int page) {
        this(image, page, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getPage() {
        return page;
    }

    public Integer getSlice() {
        return slice;
    }

    @Override
    public String toString() {
        String cellText = "Pagina " + page;
        if (slice != null) {
            cellText += " Recorte " + slice;
        }
        return cellText;
    }
}
