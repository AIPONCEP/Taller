module org.example.taller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens org.example.taller to javafx.fxml, java.sql, org.postgresql.jdbc;
    exports org.example.taller;

    opens org.example.taller.controller to java.sql, javafx.fxml, org.postgresql.jdbc;
    exports org.example.taller.controller;

    opens org.example.taller.model to java.sql, javafx.fxml, org.postgresql.jdbc;
    exports org.example.taller.model;
}