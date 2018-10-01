package ocrGUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ocrGUI.PDF_Processor;
import ocrGUI.util.AppScene;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SelectFile extends AppScene {

    private final FileChooser fileChooser = new FileChooser();

    public SelectFile(Stage window) throws IOException {
        super(window, FXMLLoader.load(SelectFile.class.getResource("/fxml/loading_page.fxml")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)","*.pdf"));
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

    private void selectPDF() {
        File file = fileChooser.showOpenDialog(this.scene.getWindow());
        if (file != null) {
            System.out.println(file.getAbsolutePath());
            PDDocument pdf = null;
            try {
                pdf = PDF_Processor.toPDF(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            int firstPage = askForFirstPage(pdf.getNumberOfPages());
            int lastPage = askForLastPage(firstPage,pdf.getNumberOfPages());
            List<BufferedImage> images = null;
            try {
                images = PDF_Processor.toImageArray(pdf,firstPage,lastPage);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error converting PDF to Images");
                System.exit(1);
            }
            for (int i = firstPage-1; i < lastPage; i++) {
                System.out.println("Saving image "+i);
                File img = new File("Image-"+i+".png");
                try {
                    ImageIO.write(images.get(i), "png", img);
                } catch (IOException e) {
                    e.printStackTrace();

                    System.err.println("Error saving Image of Page "+(i+1);
                    System.exit(1);
                }
            }
        }else{
            System.exit(1);
        }
    }

    private int askForFirstPage(int numOfPages) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("PDF2Text");
        modal.setWidth(600);
        modal.setHeight(400);
        modal.initStyle(StageStyle.UTILITY);
        VBox root = new VBox();
        Label label = new Label("Introduzca la primera pagina sobre la que desea reconocer texto");
        root.getChildren().add(label);
        Spinner<Integer> pageSelector = new Spinner<>(1,numOfPages,1);
        root.getChildren().add(pageSelector);

        modal.setScene(new Scene(root));
        modal.showAndWait();

        return pageSelector.getValue();
    }

    private int askForLastPage(int firstPage,int numOfPages) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("PDF2Text");
        modal.setWidth(600);
        modal.setHeight(400);
        modal.initStyle(StageStyle.UTILITY);
        VBox root = new VBox();
        Label label = new Label("Introduzca la ultima pagina sobre la que desea reconocer texto");
        root.getChildren().add(label);
        Spinner<Integer> pageSelector = new Spinner<>(firstPage+1,numOfPages,firstPage+1);
        root.getChildren().add(pageSelector);

        modal.setScene(new Scene(root));
        modal.showAndWait();

        return pageSelector.getValue();    }
}
