package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZonaDAO {
    public static List<Zona> obtenerTodasLasZonas() {
        List<Zona> zonas = new ArrayList<>();
        String sql = "SELECT z.id_zona, z.descripcion, z.id_estacionamiento, " +
                    "e.nombre as nombre_estacionamiento, e.ubicacion " +
                    "FROM Zona z " +
                    "INNER JOIN Estacionamiento e ON z.id_estacionamiento = e.id_estacionamiento " +
                    "ORDER BY z.id_zona";
        
        try (Connection conn = ConexionDB.conectar();  // CAMBIADO: ConexionDB.conectar()
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Zona zona = new Zona();
                zona.setIdZona(rs.getInt("id_zona"));
                zona.setDescripcion(rs.getString("descripcion"));
                zona.setIdEstacionamiento(rs.getInt("id_estacionamiento"));
                zona.setNombreEstacionamiento(rs.getString("nombre_estacionamiento"));
                zona.setUbicacionEstacionamiento(rs.getString("ubicacion"));
                zonas.add(zona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zonas;
    }
    
    public static Zona obtenerZonaPorId(int idZona) {
        Zona zona = null;
        String sql = "SELECT z.id_zona, z.descripcion, z.id_estacionamiento, " +
                    "e.nombre as nombre_estacionamiento, e.ubicacion " +
                    "FROM Zona z " +
                    "INNER JOIN Estacionamiento e ON z.id_estacionamiento = e.id_estacionamiento " +
                    "WHERE z.id_zona = ?";
        
        try (Connection conn = ConexionDB.conectar();  // CAMBIADO: ConexionDB.conectar()
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idZona);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    zona = new Zona();
                    zona.setIdZona(rs.getInt("id_zona"));
                    zona.setDescripcion(rs.getString("descripcion"));
                    zona.setIdEstacionamiento(rs.getInt("id_estacionamiento"));
                    zona.setNombreEstacionamiento(rs.getString("nombre_estacionamiento"));
                    zona.setUbicacionEstacionamiento(rs.getString("ubicacion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zona;
    }
    
    public static boolean eliminarZona(int idZona) {
        String sql = "DELETE FROM Zona WHERE id_zona = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idZona);
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
     public static boolean guardarZona(Zona zona) {
        String sql = "INSERT INTO Zona (descripcion, id_estacionamiento) VALUES (?, ?)";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, zona.getDescripcion());
            pstmt.setInt(2, zona.getIdEstacionamiento());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar zona: " + e.getMessage());
            return false;
        }
    }
     
    
    
    
}