package ocrGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import ocrGUI.controller.SelectFile;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        window.setTitle("Reconocimiento de Texto");
        PDF_Processor.prepareEnviroment();
        FilesHandler.createFolders();
        new SelectFile(window).show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
