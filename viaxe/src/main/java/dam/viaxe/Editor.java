package dam.viaxe;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Editor implements Initializable {
    public TextField txtdistancia;
    public TextField txtdesnivel;
    public TextField txtlugar;
    public DatePicker datepicker;
    public Button btmodificar;
    public Button btcancelar;
    public VBox modal; //existe polo css
    private Viaxe viaxe;  //non uso setuserdata, se non un controlador manual

    /**
     * enche os campos de texto e o datepicker cos datos do viaxe a modificar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtdistancia.setText("" + viaxe.getDistancia());
        txtdesnivel.setText("" + viaxe.getDesnivel());
        txtlugar.setText(viaxe.getComentario());
        datepicker.setValue(viaxe.getFecha());
    }

    public Viaxe getViaxe() {
        return viaxe;
    }

    public void setViaxe(Viaxe viaxe) {
        this.viaxe = viaxe;
    }

    /**
     * permite modificar un viaxe
     */
    public void modificar(ActionEvent actionEvent) {
        double distancia;
        int desnivel;
        String lugar;
        LocalDate fecha = null;
        try {
            distancia = Double.parseDouble(txtdistancia.getText());
            desnivel = Integer.parseInt(txtdesnivel.getText());
            lugar = txtlugar.getText().toLowerCase();
            if (lugar.isEmpty()) {
                txtlugar.requestFocus();
                throw new Exception("Lugar debe tener contenido");
            }
            fecha = datepicker.getValue();
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            Viaxe v = new Viaxe(fecha, distancia, desnivel, lugar);
            ViaxeRepositorio.modificar(v);
            avisar();
            Scene scene = btmodificar.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errar();
        }
    }

    /**
     * sae sen facer nada
     */
    public void cancelar(ActionEvent actionEvent) {
        Scene scene = btcancelar.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }


    /**
     * lanza unha xanela modal informando dun erro
     */
    public void errar() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Hay campos incorrectos");
        alerta.showAndWait();
    }

    /**
     * lanza unha xanela modal informando dunha operacion exitosa
     */
    public void avisar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Viaje modificado");
        alerta.showAndWait();
    }
}
