package org.example.taller.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.DBManager;

import java.io.IOException;

public class updateDatosController {
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

    public void upNombre(MouseEvent mouseEvent) {
        String upNombre=tFnombre.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.nombre",upNombre);
        tFnombre.setText("");
    }

    public void upNumCoches(MouseEvent mouseEvent) {
        String upNumCoches=tFnumc.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"numcoches",upNumCoches);
        tFnumc.setText("");
    }

    public void upTlf(MouseEvent mouseEvent) {
        String uptlfs="{"+tFtlf.getText()+"}";
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"tlf",uptlfs);
        tFtlf.setText("");
    }

    public void upNumCasa(MouseEvent mouseEvent) {
        String upNum=tfnumDirec.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.direccion.num", String.valueOf(upNum));
        tfnumDirec.setText("");
    }

    public void upCalle(MouseEvent mouseEvent) {
        String upCalle=tFcalle.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.direccion.calle",upCalle);
        tFcalle.setText("");
    }

    public void upCiudad(MouseEvent mouseEvent) {
        String upCiudad=tFciudad.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.direccion.ciudad",upCiudad);
        tFciudad.setText("");
    }

    public void upCP(MouseEvent mouseEvent) {
        String upCP=tFcp.getText();
        DBManager.actualizarClientePorId(Integer.parseInt(tFId.getText()),"persona.direccion.cp",upCP);
        tFcp.setText("");
    }
}
