package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {

    public void crearRegistro(String hora_ent, int id_vehiculo, int id_espacio) {
        String sql = "INSERT INTO Registro (hora_ent, id_vehiculo, id_espacio) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Convertir String a Time
            Time horaEntrada = Time.valueOf(hora_ent + ":00");
            
            ps.setTime(1, horaEntrada);
            ps.setInt(2, id_vehiculo);
            ps.setInt(3, id_espacio);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Registro creado correctamente - Vehículo: " + id_vehiculo + 
                                 " en Espacio: " + id_espacio + " a las: " + hora_ent);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al crear registro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Formato de hora incorrecto. Use HH:MM (ej: 08:30)");
        }
    }

    public List<String> leerRegistros() {
        List<String> registros = new ArrayList<>();
        String sql = "SELECT r.id_registro, r.hora_ent, r.hora_salida, " +
                    "v.placa, p.nombre, e.id_espacio, z.descripcion as zona " +
                    "FROM Registro r " +
                    "JOIN Vehiculo v ON r.id_vehiculo = v.id_vehiculo " +
                    "JOIN Persona p ON v.id_persona = p.id_persona " +
                    "JOIN Espacio e ON r.id_espacio = e.id_espacio " +
                    "JOIN Zona z ON e.id_zona = z.id_zona " +
                    "ORDER BY r.id_registro";
        
        try (Connection conn = ConexionDB.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                String registro = String.format(
                    "ID: %d | Entrada: %s | Salida: %s | Placa: %s | Propietario: %s | Espacio: %d | Zona: %s",
                    rs.getInt("id_registro"),
                    rs.getTime("hora_ent"),
                    rs.getTime("hora_salida") != null ? rs.getTime("hora_salida") : "Pendiente",
                    rs.getString("placa"),
                    rs.getString("nombre"),
                    rs.getInt("id_espacio"),
                    rs.getString("zona")
                );
                registros.add(registro);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al leer registros: " + e.getMessage());
        }
        return registros;
    }

    public void actualizarRegistro(int id_registro, String hora_salida) {
        String sql = "UPDATE Registro SET hora_salida = ? WHERE id_registro = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            Time horaSalida = Time.valueOf(hora_salida + ":00");
            ps.setTime(1, horaSalida);
            ps.setInt(2, id_registro);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Registro actualizado - ID: " + id_registro + 
                                 " - Hora salida: " + hora_salida);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar registro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Formato de hora incorrecto. Use HH:MM (ej: 14:30)");
        }
    }

    public void eliminarRegistro(int id_registro) {
        String sql = "DELETE FROM Registro WHERE id_registro = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id_registro);
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("✅ Registro eliminado - ID: " + id_registro);
            } else {
                System.out.println("⚠️  No se encontró registro con ID: " + id_registro);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar registro: " + e.getMessage());
        }
    }
}