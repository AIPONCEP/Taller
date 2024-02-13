package org.example.taller.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.Alert;
import org.example.taller.model.Cliente;
import org.example.taller.model.DBManager;

import java.io.IOException;

public class InsertsController {
    public TextField tFnombre;
    public TextField tFnumc;
    public TextField tFtlf;
    public TextField tfnumDirec;
    public TextField tFcalle;
    public TextField tFciudad;
    public TextField tFcp;

    /**
     * Método insertar
     * se utiliza para insertar un nuevo cliente en la base de datos
     * llama al método " insertarClientes()" para ello.
     * controla que los campos no esten vacios,
     * que el codigo postal y el telefono contengan datos tipo númerico....
     * @param mouseEvent
     */
    public void insertar(MouseEvent mouseEvent) {
        if(!tFnombre.getText().isEmpty() && !tFtlf.getText().isEmpty() &&
        !tfnumDirec.getText().isEmpty() && !tFcalle.getText().isEmpty() &&
        !tFciudad.getText().isEmpty() && !tFcp.getText().isEmpty()){
           if(esTipoNumerico(tFtlf.getText())){
               if(esTipoNumerico(tFcp.getText())){
                   if(tFnumc.getText().isEmpty()){
                       //si no intruce nada el usuario lo pone en 0 por defecto para el num de coches
                       tFnumc.setText("0");
                       Cliente cliente=new Cliente(
                               tFnombre.getText(),Integer.parseInt(tfnumDirec.getText()),
                               tFcalle.getText(),tFciudad.getText(),tFcp.getText(),
                               Integer.parseInt(tFnumc.getText()),tFtlf.getText()
                       );
                       DBManager.insertarClientes(cliente);
                   }else {
                       Cliente cliente=new Cliente(
                               tFnombre.getText(),Integer.parseInt(tfnumDirec.getText()),
                               tFcalle.getText(),tFciudad.getText(),tFcp.getText(),
                               Integer.parseInt(tFnumc.getText()),tFtlf.getText()
                       );
                       DBManager.insertarClientes(cliente);
                   }
               }else {
                   Alert.showAlert("Error","Código postal erroneo", javafx.scene.control.Alert.AlertType.ERROR);
               }
           }else {
               Alert.showAlert("Error","Teléfono erroneo", javafx.scene.control.Alert.AlertType.ERROR);
           }
        }else {
            Alert.showAlert("Error","Los campos nombre, teléfono, número, calle, ciudad y código postal son obligatorios", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
    /**
     * Método esTipoNumerico
     * contiene una exp regular para ver si un dato que le
     * pasa el usuario tipo string esta formado por números o no
     * @param dato
     * @return
     */
    private boolean esTipoNumerico(String dato) {
        return dato.matches("\\d+");
    }
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
}
