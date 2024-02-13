package org.example.taller.model;

import java.sql.*;

/**
 * DBManager
 * clase que contiene los métodos del crud de la base de datos taller
 */
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

    /**
     * Método para verificar si un mecánico existe dado su ID
     * Si rowCount > 0, el mecánico existe
     * Si algo sale mal, retornar falso
     * @param idMecanico
     * @return
     */
    public static boolean verificarExistenciaMecanico(int idMecanico) {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("SELECT COUNT(*) FROM mecanicos WHERE idmecanico = ?");
                sentencia.setInt(1, idMecanico);
                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    int rowCount = resultado.getInt(1);
                    LocalConnection.closeConnection();
                    sentencia.close();
                    return rowCount > 0;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (resultado != null) {
                        resultado.close();
                    }
                    if (sentencia != null) {
                        sentencia.close();
                    }
                    LocalConnection.closeConnection();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return false;
    }
    public static boolean insertarClientes(Cliente cliente) {

        try (Connection con = LocalConnection.getConnection();
            PreparedStatement sentencia = con.prepareStatement("INSERT INTO clientes (persona, numcoches, tlf) VALUES (ROW(?, ROW(?, ?, ?, ?)), ?, ?)")) {

            // Establecer valores en la consulta
            sentencia.setString(1, cliente.getNombre());
            sentencia.setInt(2, cliente.getNum());
            sentencia.setString(3, cliente.getCalle());
            sentencia.setString(4, cliente.getCiudad());
            sentencia.setString(5, cliente.getCp());
            sentencia.setInt(6, cliente.getNumCoches());
            sentencia.setArray(7, con.createArrayOf("VARCHAR", Cliente.convertirString(cliente.getTlfs())));

            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            sentencia.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método actualizarClientePorId
     * se utiliza para actualizar los datos de los clientes segun el id que se le pasa por parametros,
     * comprueba el valor del dato y en funcion del mismo hace una actualización u otra
     * @param idCliente
     * @param columNameUpdate
     * @param dato
     * @return
     */
    public static boolean actualizarClientePorId(int idCliente, String columNameUpdate, String dato) {
        try (Connection con = LocalConnection.getConnection();
             PreparedStatement sentencia = con.prepareStatement("UPDATE clientes SET "+columNameUpdate+" = '"+dato+"' WHERE idCliente = "+idCliente+";")) {
            // Establecer valores en la consulta
            if(dato.contains("nombre")){
                sentencia.setString(1, dato);
            }
            if(dato.contains("num")){
                sentencia.setInt(2, Integer.parseInt(dato));
            }
            if(dato.contains("calle")){
                sentencia.setString(3, dato);
            }
            if(dato.contains("ciudad")){
                sentencia.setString(4, dato);
            }
            if(dato.contains("cp")){
                sentencia.setString(5, dato);
            }
            if(dato.contains("numcoches")){
                sentencia.setInt(6, Integer.parseInt(dato));
            }
            if(dato.contains("tlf")){
               sentencia.setArray(7, con.createArrayOf("VARCHAR", Cliente.convertirString(dato)));
            }
            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            sentencia.close();
            return filasAfectadas > 0;  // Retorna true si se actualizó al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de actualización: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método borrarClientePorId
     * le pasamos el id de un cliente y hace un delete si encuentra el mismo en la base de datos.
     * @param idCliente
     * @return
     */
    public static boolean borrarClientePorId(int idCliente) {
        try (Connection con = LocalConnection.getConnection();
             PreparedStatement sentencia = con.prepareStatement("DELETE FROM clientes WHERE idCliente = ?")) {
            // Establecer el valor del ID en la consulta
            sentencia.setInt(1, idCliente);
            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            sentencia.close();
            return filasAfectadas > 0;  // Retorna true si se borró al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de borrado: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertarCoche(Coche coche) {
        try (Connection con = LocalConnection.getConnection();
            PreparedStatement sentencia = con.prepareStatement("INSERT INTO coches (matricula,marca,modelo,fichatecnica,seguro,puertas,software) VALUES (?,?,?,?,?,?,?)")) {

            // Establecer valores en la consulta
            sentencia.setString(1,coche.getMatricula());
            sentencia.setString(2, coche.getMarca());
            sentencia.setString(3, coche.getModelo());
            sentencia.setString(4, coche.getFichaTecnica());
            sentencia.setString(5, coche.getSeguro());
            sentencia.setInt(6, coche.getPuertas());
            sentencia.setString(7, coche.getSoftware());

            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            sentencia.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertarMoto(Moto moto) {
        try (Connection con = LocalConnection.getConnection();
             PreparedStatement sentencia = con.prepareStatement("INSERT INTO motos (matricula,marca,modelo,fichatecnica,seguro,tiempos,maleta) VALUES (?,?,?,?,?,?::tiemposmotos,?)")) {

            // Establecer valores en la consulta
            sentencia.setString(1,moto.getMatricula());
            sentencia.setString(2, moto.getMarca());
            sentencia.setString(3, moto.getModelo());
            sentencia.setString(4, moto.getFichaTecnica());
            sentencia.setString(5, moto.getSeguro());
            sentencia.setString(6, moto.getTiempos());
            sentencia.setBoolean(7, moto.isMaleta());

            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertarClientesVehiculos(String matricula,Integer idCliente) {
        try (Connection con = LocalConnection.getConnection();
            PreparedStatement sentencia = con.prepareStatement("INSERT INTO clientes_vehiculos (matricula,idcliente) VALUES (?,?)")) {
            // Establecer valores en la consulta
            sentencia.setString(1,matricula);
            sentencia.setInt(2, idCliente);
            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static String mostrarServicios() {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("Select * from servicios;");
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
    public static String listarReparaciones(Integer id) {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("select listarReparaciones("+id+");");
                resultado = sentencia.executeQuery();
                ResultSetMetaData metaData = resultado.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder result = new StringBuilder();
                while (resultado.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultado.getObject(columnName);
                        row.append(value).append("¿");
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
    public static boolean asignarNuevaReparacion(Reparacion reparacion) {
        try (Connection con = LocalConnection.getConnection();
            PreparedStatement sentencia = con.prepareStatement("INSERT INTO reparaciones (idservicio,idmecanico,matricula,fecha,estado) VALUES (?,?,?,?::date,?)")) {
            // Establecer valores en la consulta
            sentencia.setInt(1,reparacion.getIdServicio());
            sentencia.setInt(2, reparacion.getIdMecanico());
            sentencia.setString(3, reparacion.getMatricula());
            sentencia.setString(4, reparacion.getFecha());
            sentencia.setString(5, reparacion.getEstado());
            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            LocalConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static String mostrarClientesVehiculos() {
        con = LocalConnection.getConnection();
        if (con != null) {
            try {
                sentencia = con.prepareStatement("Select * from clientes_vehiculos;");
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

    /**
     * Método obtenerHorasPendientes
     * usa la function horas_pendientes_total()
     * le pasas el codMecanico y calcula el total de horas de trabajos asignados
     * @param codMecanico
     * @return
     */
    public static int obtenerHorasPendientes(int codMecanico) {
        con = LocalConnection.getConnection();
        if (con != null) {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                String query = "SELECT horas_pendientes_total(?)";
                statement = con.prepareStatement(query);
                statement.setInt(1, codMecanico);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener las horas pendientes: " + e.getMessage());
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (statement != null) statement.close();
                    LocalConnection.closeConnection();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar recursos: " + e.getMessage());
                }
            }
        }
        return 0;
    }
}