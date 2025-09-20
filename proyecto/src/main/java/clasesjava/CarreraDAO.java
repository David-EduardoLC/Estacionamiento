package clasesjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CarreraDAO {

    // OBTENER TODAS LAS CARRERAS
    public static List<Carrera> obtenerTodasCarreras() {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT * FROM Carrera ORDER BY id_carrera";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Carrera carrera = new Carrera(
                    rs.getInt("id_carrera"),
                    rs.getString("nombre")
                );
                carreras.add(carrera);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener carreras: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return carreras;
    }

    // OBTENER CARRERA POR ID
    public static Carrera obtenerCarreraPorId(int id) {
        String sql = "SELECT * FROM Carrera WHERE id_carrera = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Carrera(
                    rs.getInt("id_carrera"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener carrera: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // BUSCAR CARRERAS POR NOMBRE
    public static List<Carrera> buscarCarreras(String criterio) {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT * FROM Carrera WHERE nombre ILIKE ? ORDER BY id_carrera";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carrera carrera = new Carrera(
                    rs.getInt("id_carrera"),
                    rs.getString("nombre")
                );
                carreras.add(carrera);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar carreras: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return carreras;
    }

    // OBTENER CARRERA POR NOMBRE (útil para validaciones)
    public static Carrera obtenerCarreraPorNombre(String nombre) {
        String sql = "SELECT * FROM Carrera WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Carrera(
                    rs.getInt("id_carrera"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener carrera por nombre: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // VERIFICAR SI EXISTE UNA CARRERA
    public static boolean existeCarrera(String nombre) {
        String sql = "SELECT COUNT(*) FROM Carrera WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar carrera: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    // CONTAR TOTAL DE CARRERAS
    public static int contarCarreras() {
        String sql = "SELECT COUNT(*) FROM Carrera";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar carreras: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // OBTENER NOMBRES DE CARRERAS (útil para combos)
    public static String[] obtenerNombresCarreras() {
        List<Carrera> carreras = obtenerTodasCarreras();
        String[] nombres = new String[carreras.size()];
        
        for (int i = 0; i < carreras.size(); i++) {
            nombres[i] = carreras.get(i).getNombre();
        }
        
        return nombres;
    }

    // OBTENER CARRERAS PARA COMBO (retorna lista de objetos)
    public static Carrera[] obtenerCarrerasParaCombo() {
        List<Carrera> carreras = obtenerTodasCarreras();
        return carreras.toArray(new Carrera[0]);
    }
}