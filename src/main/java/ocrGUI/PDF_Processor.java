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

    public static List<BufferedImage> toImageArray(PDDocument pdf,int firstPage, int lastPage) throws IOException {
        LinkedList<BufferedImage> images = new LinkedList<>();
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        for (int page = firstPage-1; page < lastPage; ++page) {
            System.out.println("Current Page:" + page);
            images.add(pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB));
        }

        return images;
    }

    public static PDDocument toPDF(File file) throws IOException {
        return PDDocument.load(file);
    }
}
