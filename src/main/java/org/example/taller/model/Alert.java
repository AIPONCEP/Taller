package org.example.taller.model;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alert {
    public static void showAlert(String title, String message, javafx.scene.control.Alert.AlertType alertType) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * showAlertConfirmation
     * Se utiliza para mostrar un cuadro de dialogo donde puedes aceptar o cancelar algo...
     * @param title
     * @param message
     * @param alertType
     * @return true o false según presionas ok o cancelas la operación
     */
    public static boolean showAlertConfimation(String title, String message, javafx.scene.control.Alert.AlertType alertType) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
