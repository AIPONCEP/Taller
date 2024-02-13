package org.example.taller.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.taller.model.Alert;
import org.example.taller.model.Cliente;
import org.example.taller.model.DBManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TableView tablaClientes;
    public ListView listViewMecanico;
    public TextField idMecanicoSelect;
    public static Integer idMec=0;

    /**
     * initialize
     * Se utiliza para rellenar tabla clientes y el listview mecanicos.
     * @param url
     * @param resourceBundle
     */
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

        rellenarTablaClientes();

        String datosMecanicos = DBManager.mostrarMecanicos();
        actualizarListView(datosMecanicos);
    }

    /**
     * Método mostrarC
     * Se usa para refrescar la tabla clientes actualizando los datos
     * @param mouseEvent
     */
    public void mostrarC(MouseEvent mouseEvent) {
        rellenarTablaClientes();
    }

    /**
     * Método eliminarC
     * Se utiliza para eliminar los clientes que se seleccionen en la tabla
     * @param mouseEvent
     */
    public void eliminarC(MouseEvent mouseEvent) {
        Cliente clienteSelec = (Cliente) tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelec == null) {
            Alert.showAlert("Advertencia", "Por favor, seleccione un cliente para eliminar.", javafx.scene.control.Alert.AlertType.WARNING);
        } else {
            if (Alert.showAlertConfimation("Confirmar", "¿Está seguro de eliminar el cliente?", javafx.scene.control.Alert.AlertType.CONFIRMATION)) {
                DBManager.borrarClientePorId(Integer.parseInt(clienteSelec.getIdClient().toString()));
            } else {
                Alert.showAlert("Información", "Operación Cancelada", javafx.scene.control.Alert.AlertType.INFORMATION);
            }
        }
    }

    /**
     * Método rellenarTablaClientes
     * se utiliza para rellenar la tabla con todos los datos de clientes...
     */
    public void rellenarTablaClientes(){
        ObservableList<Cliente> listObs = FXCollections.observableArrayList();
        String[] separarxguion = DBManager.mostrarClientes().split("-");
        ArrayList<Cliente> listaDatosClientes = new ArrayList<>();
        String nombre, calle,ciudad,cp,tlf;
        Integer num,numeroCoches,id;
        //se realiza tratamiento de string para poder recoger datos y asignarlos en sus correlativas columnas
        for (int i = 0; i < separarxguion.length; i++) {
            if (!separarxguion[i].contains(":")) {
                continue;
            }
            //lo comentado es una alternativa que funciona igual pero sin la exprecion regular.
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
                tlf = separarxguion[i + 7].substring(separarxguion[i + 7].indexOf(":") + 1);
                listaDatosClientes.add(new Cliente(id,nombre,num,calle,ciudad,cp,numeroCoches, tlf));
            }
        }
        for (Cliente e : listaDatosClientes) {
            listObs.add(e);
        }
        tablaClientes.setItems(listObs);
    }

    /**
     * Método actualizarListView
     * este método se utiliza para poder pasar los datos al listview
     * @param datosMecanicos
     */
    private void actualizarListView(String datosMecanicos) {
        ObservableList<String> listObs = FXCollections.observableArrayList();
        // Procesa los datos y agrega a la lista observable
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

    public void upClientes(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/updateView.fxml")));
            Stage window = (Stage) tablaClientes.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método asignarTareas
     * Se utiliza para cambiar de vista y pasar el id del mecánico que corresponda
     * mediante la utilización de la variable idMec y asi asignarle después las tareas.
     * @param mouseEvent
     */
    public void asignarTareas(MouseEvent mouseEvent) {
        //Se comprueba que el textfield no este vacio
        if (!idMecanicoSelect.getText().isEmpty()) {
            //Se asigna el valor a la variable idMec convirtiendo a entero
            idMec = Integer.valueOf(idMecanicoSelect.getText());
            //Se comprueba que no sea 0 idMec
            if(idMec!=0){
                //Se verifica que el mécanico existe
                if(DBManager.verificarExistenciaMecanico(idMec)){
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/addTasks-view.fxml")));
                        Stage window = (Stage) tablaClientes.getScene().getWindow();
                        window.setTitle("");
                        window.setScene(scene);
                        window.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert.showAlert("Error","El Id introducido no se corresponde con ninguno registrado para los mécanicos de la base de datos", javafx.scene.control.Alert.AlertType.ERROR);
                }
            }
        } else {
            // Maneja el error en el caso de que el campo esté vacío
            Alert.showAlert("Error","Debe indicar el id del mecánico para asignarle las tareas", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

    public void addVehiculos(MouseEvent mouseEvent) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/taller/insertsVehiculos-view.fxml")));
            Stage window = (Stage) tablaClientes.getScene().getWindow();
            window.setTitle("");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}