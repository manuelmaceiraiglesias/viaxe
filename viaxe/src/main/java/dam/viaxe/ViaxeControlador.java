package dam.viaxe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViaxeControlador implements Initializable {
    public TextField txtdistancia;
    public TextField txtdesnivel;
    public TextField txtlugar;
    public Button btengadir;
    public Button btver;
    public TableView<Viaxe> tabla;
    public TableColumn<Viaxe, LocalDate> colfecha;
    public TableColumn<Viaxe, Double> coldistancia;
    public TableColumn<Viaxe, Integer> coldesnivel;
    public TableColumn<Viaxe, String> collugar;
    public DatePicker datepicker;
    public TextField txtbuscar;
    public Button btbuscar;
    public VBox raiz;
    private ObservableList<Viaxe> lista = FXCollections.observableArrayList();

    /**
     * son unha serie de metodos que se cargan o inicio para preparar e encher a taboa
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colfecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        coldistancia.setCellValueFactory(new PropertyValueFactory<>("distancia"));
        coldesnivel.setCellValueFactory(new PropertyValueFactory<>("desnivel"));
        collugar.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        ponerTabla();
    }

    /**
     * enche a taboa enseñando todos os viaxes
     */
    public void vertodo(ActionEvent actionEvent) {
        ponerTabla();
    }

    /**
     * comproba os textfields e engade unha nova viaxe se todo e correcto
     */
    public void engadir(ActionEvent actionEvent) {
        double distancia;
        int desnivel;
        String lugar;
        LocalDate fecha = null;
        try {
            distancia = Double.parseDouble(txtdistancia.getText());
            desnivel = Integer.parseInt(txtdesnivel.getText());
            lugar = txtlugar.getText();
            if (lugar.isEmpty()) {
                txtlugar.requestFocus();
                throw new Exception("Lugar debe tener contenido");
            }
            fecha = datepicker.getValue();
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            Viaxe v = new Viaxe(fecha, distancia, desnivel, lugar);
            ViaxeRepositorio.engadir(v);
            avisar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errar();
        }
    }

    public void buscarLugar(ActionEvent actionEvent) {

        try {
            String lugar;
            lugar = txtbuscar.getText();
            if (lugar.isEmpty()) {
                throw new Exception("Lugar debe tener contenido");
            }
            lista.clear();
            lista.addAll(ViaxeRepositorio.buscarViaxes(lugar));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errar();
        }

    }

    public void ponerTabla() {
        lista.clear();
        lista.addAll(ViaxeRepositorio.verViaxes());
        tabla.setItems(lista);
    }

    public void errar() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Hay campos incorrectos");
        alerta.showAndWait();
    }

    public void avisar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Viaje añadido");
        alerta.showAndWait();
    }

}