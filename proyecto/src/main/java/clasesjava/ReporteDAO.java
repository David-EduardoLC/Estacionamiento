package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public void crearReporte(String descripcion, String fecha, String estatus, int id_registro) {
        String sql = "INSERT INTO Reporte (descripcion, fecha_reporte, estatus, id_registro) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, descripcion);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setString(3, estatus);
            ps.setInt(4, id_registro);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Reporte creado - Registro: " + id_registro + 
                                 " - Estatus: " + estatus);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al crear reporte: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Formato de fecha incorrecto. Use YYYY-MM-DD (ej: 2025-09-18)");
        }
    }

    public List<String> leerReportes() {
        List<String> reportes = new ArrayList<>();
        String sql = "SELECT rep.id_reporte, rep.descripcion, rep.fecha_reporte, rep.estatus, " +
                    "r.id_registro, v.placa, p.nombre " +
                    "FROM Reporte rep " +
                    "JOIN Registro r ON rep.id_registro = r.id_registro " +
                    "JOIN Vehiculo v ON r.id_vehiculo = v.id_vehiculo " +
                    "JOIN Persona p ON v.id_persona = p.id_persona " +
                    "ORDER BY rep.id_reporte";
        
        try (Connection conn = ConexionDB.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                String reporte = String.format(
                    "ID: %d | Fecha: %s | Estatus: %s | Registro: %d | Placa: %s | Propietario: %s | Descripción: %s",
                    rs.getInt("id_reporte"),
                    rs.getDate("fecha_reporte"),
                    rs.getString("estatus"),
                    rs.getInt("id_registro"),
                    rs.getString("placa"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                reportes.add(reporte);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al leer reportes: " + e.getMessage());
        }
        return reportes;
    }

    public void actualizarReporte(int id_reporte, String estatus) {
        String sql = "UPDATE Reporte SET estatus = ? WHERE id_reporte = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estatus);
            ps.setInt(2, id_reporte);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Reporte actualizado - ID: " + id_reporte + 
                                 " - Nuevo estatus: " + estatus);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar reporte: " + e.getMessage());
        }
    }

    public void eliminarReporte(int id_reporte) {
        String sql = "DELETE FROM Reporte WHERE id_reporte = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id_reporte);
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("✅ Reporte eliminado - ID: " + id_reporte);
            } else {
                System.out.println("⚠️  No se encontró reporte con ID: " + id_reporte);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar reporte: " + e.getMessage());
        }
    }
}