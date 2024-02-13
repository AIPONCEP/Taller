package org.example.taller.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.taller.model.Alert;
import org.example.taller.model.Coche;
import org.example.taller.model.DBManager;
import org.example.taller.model.Moto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase InsertsVehiculosController
 * Se utiliza para registrar nuevos vehiculos.
 */
public class InsertsVehiculosController implements Initializable {
    public TextField tFmatricula;
    public TextField tFmarca;
    public TextField tFmodelo;
    public TextField tFfichaTecnica;
    public TextField tFseguro;
    public ComboBox tipoVehiculo;
    public Pane paneCoches;
    public TextField tFnumPuertas;
    public TextField tFsoftware;
    public Pane paneMotos;
    public ComboBox tiempos;
    public RadioButton rbSi;
    public ToggleGroup groupRadioButtonMaleta;
    public RadioButton rdNo;
    public TextField tfIdCliente;

    /**
     * Método initialize
     * se utiliza para rellenar los combos de tiempos y tipoVehiculo
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoVehiculo.getItems().addAll("Coche", "Moto");
        tiempos.getItems().addAll("2T", "4T");
    }
    /**
     * Método tipoVehiculo
     * se utiliza para mostrar un panel u otro en funcion del tipo de vehiculo seleccionado en el combobox
     * @param actionEvent
     */
    public void tipoVehiculoOa(ActionEvent actionEvent) {
        if (tipoVehiculo.getValue().equals("Coche")) {
            paneCoches.setVisible(true);
            paneMotos.setVisible(false);
        }
        if (tipoVehiculo.getValue().equals("Moto")) {
            paneCoches.setVisible(false);
            paneMotos.setVisible(true);
        }
    }
    public void volver(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/main-view.fxml")));
            Stage window = (Stage) tFmatricula.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Método registrarVehiculo
     * se utiliza para registrar un coche o moto nueva en la base de datos
     * primero inserta el coche o moto según lo que seleccione el usuario y
     * después llama al método insertarClientesVehiculos.
     * Este método inserta en la tabla clientes_vehiculos lo que
     * permite determinar a que cliente pertence el vehiculo registrado.
     * @param mouseEvent
     */
    public void registrarVehiculo(MouseEvent mouseEvent) {
        if(tipoVehiculo.getValue()==null){
            Alert.showAlert("Error","Debe seleccionar un tipo de vehículo", javafx.scene.control.Alert.AlertType.ERROR);
        }else{
            if (tipoVehiculo.getValue().equals("Coche")) {
                if(camposNoVacios() &&
                !tFsoftware.getText().isEmpty() &&
                tFnumPuertas.getText().matches("\\d+")){
                    Coche coche = new Coche(tFmatricula.getText(), tFmarca.getText(), tFmodelo.getText(), tFfichaTecnica.getText(), tFseguro.getText(), Integer.parseInt(tFnumPuertas.getText()), tFsoftware.getText());
                    DBManager.insertarCoche(coche);
                    DBManager.insertarClientesVehiculos(tFmatricula.getText(),Integer.parseInt(tfIdCliente.getText()));
                }else {
                    Alert.showAlert("Error", "Por favor, complete todos los campos correctamente.", javafx.scene.control.Alert.AlertType.ERROR);
                }
            }
            if (tipoVehiculo.getValue().equals("Moto")) {
                if(camposNoVacios() &&  tiempos.getValue() != null){
                    String valorRadioB = ((RadioButton) groupRadioButtonMaleta.getSelectedToggle()).getText();
                    boolean maleta = valorRadioB.equals("Si");
                    Moto moto = new Moto(tFmatricula.getText(), tFmarca.getText(), tFmodelo.getText(), tFfichaTecnica.getText(), tFseguro.getText(), String.valueOf(tiempos.getValue()), maleta);
                    DBManager.insertarMoto(moto);
                    DBManager.insertarClientesVehiculos(tFmatricula.getText(),Integer.parseInt(tfIdCliente.getText()));
                }else {
                    Alert.showAlert("Error", "Por favor, complete todos los campos correctamente.", javafx.scene.control.Alert.AlertType.ERROR);
                }
            }
        }
    }

    /**
     * Método camposVacios
     * Verifica que los campos no estén vacíos
     * @return
     */
    private boolean camposNoVacios() {
        return !tFmatricula.getText().isEmpty() &&
                !tFmarca.getText().isEmpty() &&
                !tFmodelo.getText().isEmpty() &&
                !tFfichaTecnica.getText().isEmpty() &&
                !tFseguro.getText().isEmpty();
    }
}