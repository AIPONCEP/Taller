package org.example.taller.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.DBManager;
import org.example.taller.model.Reparacion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addTasksController implements Initializable {
    public TextField tFidServ;
    public TextField tFmatricula;
    public TextField tFfecha;
    public TextField tFestado;
    public ListView listViewServicios;
    public ListView listViewReparaciones;

    public void volver(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/main-view.fxml")));
            Stage window = (Stage) listViewServicios.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void registrarVehiculo(MouseEvent mouseEvent) {
        Reparacion reparacion=new Reparacion(Integer.parseInt(tFidServ.getText()),MainController.idMec,tFmatricula.getText(),tFfecha.getText(),tFestado.getText());
        DBManager.asignarNuevaReparacion(reparacion);
        //Actualizamos las reparaciones asignadas
        String datosReparaciones = DBManager.listarReparaciones(MainController.idMec);
        actualizarListViewReparaciones(datosReparaciones);
    }

    private void actualizarListViewReparaciones(String reparaciones) {
        ObservableList<String> listObs = FXCollections.observableArrayList();
        // Procesa los datos y agrega a la lista observable
        String[] listReparaciones = reparaciones.split("¿");
        listObs.addAll(listReparaciones);
        // Asigna la lista observadora al ListView
        listViewReparaciones.setItems(listObs);
    }
    private void actualizarListViewServicios(String datosServicios) {
        ObservableList<String> listObs = FXCollections.observableArrayList();
        // Procesa los datos y agrega a la lista observable
        String[] servicios = datosServicios.split("¿");
        listObs.addAll(servicios);
        // Asigna la lista observadora al ListView
        listViewServicios.setItems(listObs);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String datosServicios = DBManager.mostrarServicios();
        actualizarListViewServicios(datosServicios);

        String datosReparaciones = DBManager.listarReparaciones(MainController.idMec);
        actualizarListViewReparaciones(datosReparaciones);
    }
}
