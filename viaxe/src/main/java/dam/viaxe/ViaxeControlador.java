package dam.viaxe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    public TableColumn<Viaxe, Void> colboton;
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
        try {

        } catch (Exception e) {
            errar();
            System.out.println(e.getMessage());
        }
        colfecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        coldistancia.setCellValueFactory(new PropertyValueFactory<>("distancia"));
        coldesnivel.setCellValueFactory(new PropertyValueFactory<>("desnivel"));
        collugar.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        addButtonToTable();
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
            ViaxeRepositorio.engadir(v);
            avisar();
          /*  GridPane gp =(GridPane)txtdistancia.getParent();
            List<Node> nodes=gp.getChildren();  //alternativo para borrar con un bucle
            for(Node nod:nodes){
                if(nod instanceof TextField){
                   TextField text=(TextField) nod;
                   text.setText("");
                }
            }*/
            txtdistancia.setText("");
            txtdesnivel.setText("");
            txtlugar.setText("");
            datepicker.setValue(null);
            ponerTabla();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errar();
        }
    }

    /**
     * comproba o textfield buscar e busca todalas entradas que conteñan ese string no campo comentario
     */
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

    /**
     * enche a tabla con todolos viaxes da base de datos
     */
    public void ponerTabla() {
        lista.clear();
        lista.addAll(ViaxeRepositorio.verViaxes());
        tabla.setItems(lista);
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
        alerta.setHeaderText("Viaje añadido");
        alerta.showAndWait();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Viaxe, Void>, TableCell<Viaxe, Void>> cellFactory = new Callback<TableColumn<Viaxe, Void>, TableCell<Viaxe, Void>>() {
            @Override
            public TableCell<Viaxe, Void> call(final TableColumn<Viaxe, Void> param) {
                final TableCell<Viaxe, Void> cell = new TableCell<Viaxe, Void>() {
                    private final Button btn = new Button("Editar");
                    private final ImageView imageView = new ImageView(getClass().getResource("editar.png").toExternalForm());

                    {   imageView.setFitHeight(20);
                        imageView.setFitWidth(20);
                        btn.setStyle("-fx-background-color:transparent;-fx-text-fill:black;-fx-pref-width: 100;");
                        btn.setGraphic(imageView);
                        btn.setOnAction((ActionEvent event) -> {
                            try{
                                Viaxe v = getTableView().getItems().get(getIndex());
                                FXMLLoader fxmlLoader = new FXMLLoader(ViaxeAplicacion.class.getResource("editor.fxml"));
                                Editor editor=new Editor(); //uso un editor manual para engadirlle o viaxe
                                editor.setViaxe(v);
                                fxmlLoader.setController(editor);
                                Scene scene = new Scene(fxmlLoader.load());
                                Stage stage = new Stage();
                                stage.setTitle("Editor");
                                stage.setScene(scene);
                                //cambia o icono da ventana
                                stage.getIcons().add(new Image(Objects.requireNonNull(ViaxeAplicacion.class.getResourceAsStream("holopreico.png"))));
                                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("estilo.css")).toExternalForm());
                                stage.setResizable(false);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.showAndWait();//para aqui e asi podo chamar depois a ponertatabla para actualizar
                                ponerTabla();
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colboton.setCellFactory(cellFactory);
    }

}