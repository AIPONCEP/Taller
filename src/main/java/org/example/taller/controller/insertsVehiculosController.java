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
import org.example.taller.model.Coche;
import org.example.taller.model.DBManager;
import org.example.taller.model.Moto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class insertsVehiculosController implements Initializable {
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
    public void registrarVehiculo(MouseEvent mouseEvent) {
        if (tipoVehiculo.getValue().equals("Coche")) {
            Coche coche = new Coche(tFmatricula.getText(), tFmarca.getText(), tFmodelo.getText(), tFfichaTecnica.getText(), tFseguro.getText(), Integer.parseInt(tFnumPuertas.getText()), tFsoftware.getText());
            DBManager.insertarCoche(coche);
        }
        if (tipoVehiculo.getValue().equals("Moto")) {
            String valorRadioB = ((RadioButton) groupRadioButtonMaleta.getSelectedToggle()).getText();
            boolean maleta = valorRadioB.equals("Si");
            System.out.println(tiempos.getValue());
            Moto moto = new Moto(tFmatricula.getText(), tFmarca.getText(), tFmodelo.getText(), tFfichaTecnica.getText(), tFseguro.getText(), String.valueOf(tiempos.getValue()), maleta);
            DBManager.insertarMoto(moto);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoVehiculo.getItems().addAll("Coche", "Moto");
        tiempos.getItems().addAll("2T", "4T");
    }
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
}