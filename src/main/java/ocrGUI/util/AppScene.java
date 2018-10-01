package ocrGUI.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AppScene {
    protected Stage window;
    protected Parent fxml;
    protected Scene scene;

    protected AppScene(Stage window, Parent fxml) {
        this.window = window;
        this.fxml=fxml;
        this.scene = new Scene(this.fxml);

    }

    public void show(){
        window.setScene(this.scene);
        window.show();
    }
}
