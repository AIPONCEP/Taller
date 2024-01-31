package org.example.taller.model;

import java.sql.*;

public class DBManager {
    private static Connection con = LocalConnection.getConnection();
    private static PreparedStatement sentencia = null;
    private static ResultSet resultado = null;

    public static String mostrarClientes() {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("Select idcliente," +
                        "(persona::persona_type).nombre," +
                        "(((persona::persona_type).direccion::direccion_type).num)," +
                        "(((persona::persona_type).direccion::direccion_type).calle)," +
                        "(((persona::persona_type).direccion::direccion_type).ciudad)," +
                        "(((persona::persona_type).direccion::direccion_type).cp)," +
                        " numcoches,tlf from clientes;");
                resultado = sentencia.executeQuery();
                ResultSetMetaData metaData = resultado.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder result = new StringBuilder();
                while (resultado.next()) {
                    StringBuilder row = new StringBuilder();
                    String valuetlfformat;
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultado.getObject(columnName);
                        if (columnName.contains("tlf")) {
                            valuetlfformat = value.toString().substring(1, value.toString().length() - 1);
                            row.append(columnName).append(":").append(valuetlfformat).append("-");
                        } else {
                            row.append(columnName).append(":").append(value).append("-");
                        }
                    }
                    result.append(row).append("\n");
                }
                LocalConnection.closeConnection();
                sentencia.close();
                return result.toString();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static String mostrarMecanicos() {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("Select " +
                        "idmecanico, " +
                        "rol, " +
                        "conthoras," +
                        "seguro," +
                        "((empleados::empleados_type).persona.nombre)," +
                        "((empleados::empleados_type).persona.direccion.num)," +
                        "((empleados::empleados_type).persona.direccion.calle)," +
                        "((empleados::empleados_type).persona.direccion.ciudad)," +
                        "((empleados::empleados_type).persona.direccion.cp)," +
                        "(empleados::empleados_type).pass," +
                        "(empleados::empleados_type).nomina," +
                        "(empleados::empleados_type).horario," +
                        "(empleados::empleados_type).nuss from mecanicos;;");
                resultado = sentencia.executeQuery();
                ResultSetMetaData metaData = resultado.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder result = new StringBuilder();
                while (resultado.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultado.getObject(columnName);
                        row.append(columnName).append(": ").append(value).append("¿");
                    }
                    result.append(row).append("\n");
                }
                LocalConnection.closeConnection();
                sentencia.close();
                return result.toString();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static boolean insertarClientes(Cliente cliente, String nombre, Integer num, String calle, String ciudad, String cp, Integer numCoches, String tlfs) {
        try (Connection con = LocalConnection.getConnection();
             PreparedStatement sentencia = con.prepareStatement("INSERT INTO clientes (persona, numcoches, tlf) VALUES (ROW(?, ROW(?, ?, ?, ?)), ?, ?)")) {

            // Establecer valores en la consulta
            sentencia.setString(1, cliente.getNombre());
            sentencia.setInt(2, cliente.getNum());
            sentencia.setString(3, cliente.getCalle());
            sentencia.setString(4, cliente.getCiudad());
            sentencia.setString(5, cliente.getCp());
            sentencia.setInt(6, cliente.getNumCoches());
            sentencia.setArray(7, con.createArrayOf("VARCHAR", cliente.convertirString(tlfs)));

            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            return true;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}