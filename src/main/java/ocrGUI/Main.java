package ocrGUI;

import ocrGUI.model.AppImage;
import ocrGUI.view.ImageRegionSelector;
import ocrGUI.view.RequestPage;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("PDF to TEXT with OCR");
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        FilesHandler.createFolders();
        try {
            PDF_Processor.prepareEnviroment();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        PDDocument pdf = PDF_Processor.selectPDF(window);
        final Integer firstPage = new RequestPage(null, pdf.getNumberOfPages()).expectValue();
        if (firstPage == null) {
            System.exit(0);
        }
        Integer lastPage = new RequestPage(firstPage + 1, pdf.getNumberOfPages()).expectValue();
        if (lastPage == null) {
            System.exit(0);
        }
        List<AppImage> images = null;
        try {
            images = PDF_Processor.toImageArray(pdf, firstPage, lastPage, window);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        new ImageRegionSelector(window, images).show();
    }
}
