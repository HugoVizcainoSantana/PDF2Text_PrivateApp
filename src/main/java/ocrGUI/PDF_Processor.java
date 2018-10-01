package ocrGUI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PDF_Processor {

    static {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    }

    public static List<BufferedImage> toImageArray(File pdf) {
        LinkedList<BufferedImage> images = new LinkedList<>();
        try (final PDDocument document = PDDocument.load(pdf)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            /*for (int page = 0; page < document.getNumberOfPages(); ++page) {
                System.out.println("Current Page:"+page);
                images.add(pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB));
            }*/
            for (int page = 0; page < 5; ++page) {
                System.out.println("Current Page:"+page);
                images.add(pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB));
            }
            document.close();
        } catch (IOException e) {
            System.err.println("Exception while trying to convert pdf document - " + e);
        }
        return images;
    }
}
