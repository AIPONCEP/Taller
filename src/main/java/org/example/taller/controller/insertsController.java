package org.example.taller.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.Cliente;
import org.example.taller.model.DBManager;

import java.io.IOException;

public class insertsController {
    public TextField tFnombre;
    public TextField tFnumc;
    public TextField tFtlf;
    public TextField tfnumDirec;
    public TextField tFcalle;
    public TextField tFciudad;
    public TextField tFcp;

    public void insertar(MouseEvent mouseEvent) {
        Cliente cliente=new Cliente(
                tFnombre.getText(),Integer.parseInt(tfnumDirec.getText()),
                tFcalle.getText(),tFciudad.getText(),tFcp.getText(),
                Integer.parseInt(tFnumc.getText()),tFtlf.getText()
        );
        DBManager.insertarClientes(cliente,tFnombre.getText(),Integer.parseInt(tfnumDirec.getText()),tFcalle.getText(),tFciudad.getText(),tFcp.getText(),Integer.parseInt(tFnumc.getText()),tFtlf.getText());
    }
    public void volver(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/Main-view.fxml")));
            Stage window = (Stage) tFnombre.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
