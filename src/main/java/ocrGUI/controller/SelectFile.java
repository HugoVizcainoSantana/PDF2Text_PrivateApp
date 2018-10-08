package ocrGUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ocrGUI.FilesHandler;
import ocrGUI.PDF_Processor;
import ocrGUI.util.AppScene;
import ocrGUI.view.RequestPage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SelectFile extends AppScene {

    private final FileChooser fileChooser = new FileChooser();

    public SelectFile(Stage window) throws IOException {
        super(window, FXMLLoader.load(SelectFile.class.getResource("/fxml/loading_page.fxml")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));
    }

    @Override
    public void show() {
        super.show();
        PDDocument pdf = selectPDF();
        processPDF(Objects.requireNonNull(pdf, "Could not parse PDF"));
    }

    private PDDocument selectPDF() {
        File file = fileChooser.showOpenDialog(this.scene.getWindow());
        if (file != null) {
            System.out.println(file.getAbsolutePath());
            try {
                return PDF_Processor.toPDF(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.exit(1);
        }
        return null;
    }

    private void processPDF(PDDocument pdf) {
        final Integer firstPage = new RequestPage(null,pdf.getNumberOfPages()).expectValue();
        if (firstPage == null) {
            System.exit(0);
        }
        Integer lastPage = new RequestPage(firstPage + 1, pdf.getNumberOfPages()).expectValue();
        if (lastPage == null) {
            System.exit(0);
        }
        LinkedList<BufferedImage> images = pdfToImageArray(pdf, firstPage, lastPage);
        List<File> savedImages = FilesHandler.saveImages(images, firstPage);
        String text = convertImagesToText(savedImages, firstPage);
        System.out.println("OCR TEXT:\n" + text);
    }


    private LinkedList<BufferedImage> pdfToImageArray(PDDocument pdf, int firstPage, int lastPage) {
        try {
            return PDF_Processor.toImageArray(pdf, firstPage, lastPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error converting PDF to Images");
            System.exit(1);
        }
        return null;
    }

    private String convertImagesToText(List<File> images, int firstPage) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (File image : images) {
            sb.append("Page ");
            sb.append(firstPage + counter);
            sb.append(":\n");
            sb.append(PDF_Processor.imageToText(image));
            counter++;
        }
        return sb.toString();
    }

}
