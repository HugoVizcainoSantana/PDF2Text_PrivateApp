package ocrGUI;

import ocrGUI.model.AppImage;
import ocrGUI.view.CustomProgressDialog;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.tesseract;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.lept.*;

public class PDF_Processor {

    private static final JFileChooser fileChooser;
    private static tesseract.TessBaseAPI api;

    static {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    }

    public static List<AppImage> toImageArray(PDDocument pdf, int firstPage, int lastPage, JFrame window) throws IOException {
        CustomProgressDialog progressDialog = new CustomProgressDialog(window, "Procesando...", true, "Procesando Pagina... ", lastPage - firstPage + 1);
        List<AppImage> images = new ArrayList<>(lastPage - firstPage);
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        for (int currPage = firstPage; currPage <= lastPage; ++currPage) {
            System.out.println("Current Page:" + currPage + " | PDF page for public is " + (currPage + 1));
            progressDialog.updateProgress(currPage - firstPage + 1);
            AppImage page = new AppImage(pdfRenderer.renderImageWithDPI(currPage, 300, ImageType.RGB), currPage);
            images.add(page);
        }
        progressDialog.setVisible(false);
        progressDialog.dispose();
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

    static PDDocument selectPDF(JFrame window) {
        if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            try {
                return toPDF(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.exit(1);
        }
        return null;
    }
}
