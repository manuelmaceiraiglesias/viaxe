package dam.viaxe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViaxeAplicacion extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViaxeAplicacion.class.getResource("viaxe-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hola!");
        stage.setScene(scene);
        //cambia o icono da ventana
        stage.getIcons().add(new Image(Objects.requireNonNull(ViaxeAplicacion.class.getResourceAsStream("holopreico.png"))));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("estilo.css")).toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}