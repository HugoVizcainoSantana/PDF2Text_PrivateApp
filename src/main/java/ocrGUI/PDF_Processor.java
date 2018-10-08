package ocrGUI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.tesseract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import static org.bytedeco.javacpp.lept.*;

public class PDF_Processor {

    private static tesseract.TessBaseAPI api;

    static {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    }

    public static LinkedList<BufferedImage> toImageArray(PDDocument pdf, int firstPage, int lastPage) throws IOException {
        LinkedList<BufferedImage> images = new LinkedList<>();
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        for (int page = firstPage; page < lastPage; ++page) {
            System.out.println("Current Page:" + page + " | PDF page for public is " + (page + 1));
            images.add(pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB));
        }

        return images;
    }

    public static PDDocument toPDF(File file) throws IOException {
        return PDDocument.load(file);
    }

    public static String imageToText(File savedImg) {
        String result;

        PIX pix = pixRead(savedImg.getAbsolutePath());
        api.SetImage(pix);

        BytePointer ocrText = api.GetUTF8Text();
        //System.out.println("OCR output:\n" + ocrText.getString());
        result = ocrText.getString();
        // Destroy used object and release memory
        api.Clear();
        ocrText.close();
        pixDestroy(pix);
        return result;
    }

    static void prepareEnviroment() throws IOException {
        try (InputStream spaData = PDF_Processor.class.getClassLoader().getResourceAsStream("tessdata/spa.traineddata")) {
            File spaExtracted = Paths.get(".", "tessdata", "spa.traineddata").toFile();
            if (spaExtracted.createNewFile()) {
                System.out.println("File created");
                Files.copy(spaData, spaExtracted.toPath(), StandardCopyOption.REPLACE_EXISTING);
                spaExtracted.deleteOnExit();
            }
        }

        api = new tesseract.TessBaseAPI();
        // Initialize tesseract-ocr, data path searches in tessdata folder
        if (api.Init(Paths.get("").toAbsolutePath().toString(), "spa") != 0) {
            System.err.println("\n" +
                    "###############################\n" +
                    "Could not initialize tesseract.\n" +
                    "###############################");
            System.exit(1);
        }
    }
}
