/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

/**
 *
 * @author Carlo
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EstacionamientoDAO {
    
    public static boolean insertarEstacionamiento(Estacionamiento estacionamiento) {
        String sql = "INSERT INTO Estacionamiento (nombre, ubicacion, hora_apertura, hora_cierre) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estacionamiento.getNombre());
            pstmt.setString(2, estacionamiento.getUbicacion());
            pstmt.setTime(3, Time.valueOf(estacionamiento.getHoraApertura()));
            pstmt.setTime(4, Time.valueOf(estacionamiento.getHoraCierre()));
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar estacionamiento: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static List<Estacionamiento> obtenerTodosEstacionamientos() {
        List<Estacionamiento> estacionamientos = new ArrayList<>();
        String sql = "SELECT * FROM Estacionamiento ORDER BY id_estacionamiento";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Estacionamiento est = new Estacionamiento(
                    rs.getInt("id_estacionamiento"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getTime("hora_apertura").toLocalTime(),
                    rs.getTime("hora_cierre").toLocalTime()
                );
                estacionamientos.add(est);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener estacionamientos: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return estacionamientos;
    }
    
    public static Estacionamiento obtenerEstacionamientoPorId(int id) {
        String sql = "SELECT * FROM Estacionamiento WHERE id_estacionamiento = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Estacionamiento(
                    rs.getInt("id_estacionamiento"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getTime("hora_apertura").toLocalTime(),
                    rs.getTime("hora_cierre").toLocalTime()
                );
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener estacionamiento: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public static boolean actualizarEstacionamiento(Estacionamiento estacionamiento) {
        String sql = "UPDATE Estacionamiento SET nombre = ?, ubicacion = ?, hora_apertura = ?, hora_cierre = ? WHERE id_estacionamiento = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estacionamiento.getNombre());
            pstmt.setString(2, estacionamiento.getUbicacion());
            pstmt.setTime(3, Time.valueOf(estacionamiento.getHoraApertura()));
            pstmt.setTime(4, Time.valueOf(estacionamiento.getHoraCierre()));
            pstmt.setInt(5, estacionamiento.getIdEstacionamiento());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar estacionamiento: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean eliminarEstacionamiento(int idEstacionamiento) {
        String checkZonas = "SELECT COUNT(*) FROM Zona WHERE id_estacionamiento = ?";
        String sql = "DELETE FROM Estacionamiento WHERE id_estacionamiento = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement checkStmt = conn.prepareStatement(checkZonas);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            checkStmt.setInt(1, idEstacionamiento);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, 
                    "No se puede eliminar el estacionamiento porque tiene zonas asociadas",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            pstmt.setInt(1, idEstacionamiento);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Estacionamiento eliminado correctamente",
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el estacionamiento a eliminar",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar estacionamiento: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static List<Estacionamiento> buscarEstacionamientos(String criterio) {
        List<Estacionamiento> estacionamientos = new ArrayList<>();
        String sql = "SELECT * FROM Estacionamiento WHERE nombre ILIKE ? OR ubicacion ILIKE ? ORDER BY id_estacionamiento";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            pstmt.setString(2, parametroBusqueda);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Estacionamiento est = new Estacionamiento(
                    rs.getInt("id_estacionamiento"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getTime("hora_apertura").toLocalTime(),
                    rs.getTime("hora_cierre").toLocalTime()
                );
                estacionamientos.add(est);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar estacionamientos: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return estacionamientos;
    }
    
    
    
    
    
    
    
}
