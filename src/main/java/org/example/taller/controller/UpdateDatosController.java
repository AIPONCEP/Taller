package org.example.taller.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.Alert;
import org.example.taller.model.DBManager;
import java.io.IOException;

/**
 * Clase UpdateDatosController
 * Esta clase se utiliza para actualizar los datos de los clientes
 * en función de los datos que se introduzcan para realizar las modificaciones
 */
public class UpdateDatosController {
    public TextField tFnombre;
    public TextField tFnumc;
    public TextField tFtlf;
    public TextField tfnumDirec;
    public TextField tFcalle;
    public TextField tFciudad;
    public TextField tFcp;
    public TextField tFId;

    public void volver(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/main-view.fxml")));
            Stage window = (Stage) tFnombre.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Método upNombre
     * actualiza el campo nombre del cliente
     * @param mouseEvent
     */
    public void upNombre(MouseEvent mouseEvent) {
        if (!tFnombre.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upNombre = tFnombre.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()), "persona.nombre", upNombre);
            tFnombre.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y nombre", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upNumCoches
     * actualiza el campo numcoches del cliente
     * @param mouseEvent
     */
    public void upNumCoches(MouseEvent mouseEvent) {
        if (!tFnumc.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upNumCoches=tFnumc.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"numcoches",upNumCoches);
            tFnumc.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y número de coches", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upTlf
     * actualiza el campo tlf del cliente
     * @param mouseEvent
     */
    public void upTlf(MouseEvent mouseEvent) {
        if (!tFtlf.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String uptlfs="{"+tFtlf.getText()+"}";
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"tlf",uptlfs);
            tFtlf.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y  teléfono", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upNumCasa
     * actualiza el campo num del cliente
     * @param mouseEvent
     */
    public void upNumCasa(MouseEvent mouseEvent) {
        if (!tfnumDirec.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upNum=tfnumDirec.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.direccion.num", String.valueOf(upNum));
            tfnumDirec.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo número id y dirección", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upCalle
     * actualiza el campo calle del cliente
     * @param mouseEvent
     */
    public void upCalle(MouseEvent mouseEvent) {
        if (!tFcalle.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upCalle = tFcalle.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()), "persona.direccion.calle", upCalle);
            tFcalle.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y calle", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upCiudad
     * actualiza el campo ciudad del cliente
     * @param mouseEvent
     */
    public void upCiudad(MouseEvent mouseEvent) {
        if (!tFciudad.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upCiudad = tFciudad.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()), "persona.direccion.ciudad", upCiudad);
            tFciudad.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y ciudad", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método upCP
     * actualiza el campo cp del cliente
     * @param mouseEvent
     */
    public void upCP(MouseEvent mouseEvent) {
        if (!tFcp.getText().isEmpty() && !tFId.getText().isEmpty()) {
            String upCP = tFcp.getText();
            DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()), "persona.direccion.cp", upCP);
            tFcp.setText("");
        }else {
            Alert.showAlert("Error","De rellenar el campo id y código postal", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
}