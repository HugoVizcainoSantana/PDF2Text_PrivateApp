package ocrGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import ocrGUI.controller.SelectFile;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        window.setTitle("Reconocimiento de Texto");
        new SelectFile(window).show();
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/layout.fxml"));

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStage(root);

        window.setScene(new Scene(root, 800, 600));
        window.show();
        */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
