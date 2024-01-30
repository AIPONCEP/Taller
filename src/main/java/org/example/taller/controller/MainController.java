package org.example.taller.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.Cliente;
import org.example.taller.model.DBManager;
import org.example.taller.model.LocalConnection;
import org.example.taller.model.Persona;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TableView tablaClientes;
    public ListView listViewMecanico;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Cliente, Integer> columnId = new TableColumn<>("IdCliente");
        columnId.setCellValueFactory(new PropertyValueFactory<>("idClient"));

        TableColumn<Cliente, String> columnNombre = new TableColumn<>("Nombre");
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, Integer> columnNum = new TableColumn<>("Número");
        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));

        TableColumn<Cliente, String> columnCalle = new TableColumn<>("Calle");
        columnCalle.setCellValueFactory(new PropertyValueFactory<>("calle"));

        TableColumn<Cliente, String> columnCiudad = new TableColumn<>("Ciudad");
        columnCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        TableColumn<Cliente, String> columnCP = new TableColumn<>("Código Postal");
        columnCP.setCellValueFactory(new PropertyValueFactory<>("cp"));

        TableColumn<Cliente, String> columnTlf = new TableColumn<>("Teléfono");
        columnTlf.setCellValueFactory(new PropertyValueFactory<>("tlfs"));

        TableColumn<Cliente, String> columNumCoches = new TableColumn<>("Nº Coches");
        columNumCoches.setCellValueFactory(new PropertyValueFactory<>("numCoches"));

        tablaClientes.getColumns().addAll(columnId,columnNombre,columnNum,columnCalle,columnCiudad,columnCP,columNumCoches,columnTlf);

        columnId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idClient"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        columnNum.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("num"));
        columnCalle.setCellValueFactory(new PropertyValueFactory<Cliente, String>("calle"));
        columnCiudad.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ciudad"));
        columnCP.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cp"));
        columNumCoches.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numCoches"));
        columnTlf.setCellValueFactory(new PropertyValueFactory<Cliente,String>("tlfs"));

        rellenarTabla();

        String datosMecanicos = DBManager.mostrarMecanicos();
        actualizarListView(datosMecanicos);
    }

    public void mostrarC(MouseEvent mouseEvent) {
        rellenarTabla();
    }
    public void eliminarC(MouseEvent mouseEvent) {

    }
    public void mostrarM(MouseEvent mouseEvent) {
        String datosMecanicos = DBManager.mostrarMecanicos();
        actualizarListView(datosMecanicos);
    }
    public void eliminarM(MouseEvent mouseEvent) {
    }
    public void rellenarTabla(){
        ObservableList<Cliente> listObs = FXCollections.observableArrayList();
        String[] separarxguion = DBManager.mostrarClientes().split("-");
        ArrayList<Cliente> listaDatosClientes = new ArrayList<>();
        String nombre, tlfArray,calle,ciudad,cp;
        Integer num,numeroCoches,id;
        for (int i = 0; i < separarxguion.length; i++) {
            if (!separarxguion[i].contains(":")) {
                continue;
            }
            //if (separarxguion[i].substring(0, separarxguion[i].indexOf(":")).equals("idcliente") ||
            // separarxguion[i].substring(0, separarxguion[i].indexOf(":")).equals("\nidcliente")){
            if (separarxguion[i].matches("^idcliente:(.+)$") ||separarxguion[i].matches("^\nidcliente:(.+)$")) {
                id=Integer.parseInt(separarxguion[i].substring(separarxguion[i].indexOf(":") +1));
                nombre = separarxguion[i+1].substring(separarxguion[i+1].indexOf(":") + 1);
                num = Integer.parseInt(separarxguion[i + 2].substring(separarxguion[i + 2].indexOf(":") + 1));
                calle=separarxguion[i+3].substring(separarxguion[i+3].indexOf(":") + 1);
                ciudad=separarxguion[i+4].substring(separarxguion[i+4].indexOf(":") + 1);
                cp=separarxguion[i+5].substring(separarxguion[i+5].indexOf(":") + 1);
                numeroCoches = Integer.parseInt( separarxguion[i+6].substring(separarxguion[i+6].indexOf(":")+1));
                tlfArray = separarxguion[i+7].substring(separarxguion[i+7].indexOf(":")+1);
                listaDatosClientes.add(new Cliente(id,nombre,num,calle,ciudad,cp,numeroCoches,tlfArray));
            }
        }
        for (Cliente e : listaDatosClientes) {
            listObs.add(e);
        }
        tablaClientes.setItems(listObs);
    }
    private void actualizarListView(String datosMecanicos) {
        ObservableList<String> listObs = FXCollections.observableArrayList();
        // Procesa tus datos y agrega a la lista observadora
        // Por ejemplo, asumiendo que los datos están separados por guiones y cada mecanico está en una línea
        String[] mecanicos = datosMecanicos.split("¿");
        listObs.addAll(mecanicos);
        // Asigna la lista observadora al ListView
        listViewMecanico.setItems(listObs);
    }

    public void addClientes(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/insertsView.fxml")));
            Stage window = (Stage) tablaClientes.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}