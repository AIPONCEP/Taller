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
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTasksController implements Initializable {
    public TextField tFidServ;
    public TextField tFmatricula;
    public TextField tFfecha;
    public TextField tFestado;
    public ListView listViewServicios;
    public ListView listViewReparaciones;
    public ListView listViewClientesVehiculos;
    public Label idMecanicoSelect;
    public Label horasPendientes;

    /**
     * initialize
     * Se utiliza para rellenar los listview con sus correlativos datos
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String datosServicios = DBManager.mostrarServicios();
        actualizarListViewServicios(datosServicios);

        String datosReparaciones = DBManager.listarReparaciones(MainController.idMec);
        actualizarListViewReparaciones(datosReparaciones);

        String datosClientesVehiculos = DBManager.mostrarClientesVehiculos();
        actualizarListViewClientesVehiculos(datosClientesVehiculos);

        idMecanicoSelect.setText(String.valueOf(MainController.idMec));

        horasPendientes.setText(String.valueOf(DBManager.obtenerHorasPendientes(MainController.idMec)));
    }
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
        System.out.println(DBManager.obtenerHorasPendientes(MainController.idMec));
        if(DBManager.obtenerHorasPendientes(MainController.idMec)>=8){
            org.example.taller.model.Alert.showAlert("Info","No pueden asignarse más tareas a este mécanico", Alert.AlertType.INFORMATION);
        }else{
            if (!tFidServ.getText().isEmpty() &&
                    !tFmatricula.getText().isEmpty() &&
                    !tFfecha.getText().isEmpty() &&
                    !tFestado.getText().isEmpty()){
                Reparacion reparacion=new Reparacion(Integer.parseInt(tFidServ.getText()),MainController.idMec,tFmatricula.getText(),tFfecha.getText(),tFestado.getText());
                DBManager.asignarNuevaReparacion(reparacion);
                //Actualizamos las reparaciones asignadas
                String datosReparaciones = DBManager.listarReparaciones(MainController.idMec);
                actualizarListViewReparaciones(datosReparaciones);
            }else {
                org.example.taller.model.Alert.showAlert("Error", "Por favor, complete todos los campos correctamente.", javafx.scene.control.Alert.AlertType.ERROR);
            }
            horasPendientes.setText(String.valueOf(DBManager.obtenerHorasPendientes(MainController.idMec)));
        }
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

    private void actualizarListViewClientesVehiculos(String clientesVehiculos) {
        ObservableList<String> listObs = FXCollections.observableArrayList();
        // Procesa los datos y agrega a la lista observable
        String[] listClientesVehiculos= clientesVehiculos.split("¿");
        listObs.addAll(listClientesVehiculos);
        // Asigna la lista observadora al ListView
        listViewClientesVehiculos.setItems(listObs);
    }

}
