package ocrGUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ocrGUI.PDF_Processor;
import ocrGUI.util.AppScene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SelectFile extends AppScene {

    private final FileChooser fileChooser = new FileChooser();

    public SelectFile(Stage window) throws IOException {
        super(window, FXMLLoader.load(SelectFile.class.getResource("/fxml/loading_page.fxml")));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("PDF File","*.pdf"));
    }

    @Override
    public void show() {
        super.show();
        try {
            // Simulate loading
            Thread.sleep(500);
            selectPDF();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void selectPDF() throws IOException {
        File pdf = fileChooser.showOpenDialog(this.scene.getWindow());
        if (pdf != null) {
            System.out.println(pdf.getAbsolutePath());
            List<BufferedImage> images = PDF_Processor.toImageArray(pdf);
            for (int i = 0; i < 5; i++) {
                System.out.println("Saving image "+i);
                File img = new File("Image-"+i+".png");
                ImageIO.write(images.get(i), "png", img);
            }
        }
    }
}
