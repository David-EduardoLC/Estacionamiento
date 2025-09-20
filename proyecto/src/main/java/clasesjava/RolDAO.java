package clasesjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RolDAO {

    // OBTENER TODOS LOS ROLES
    public static List<Rol> obtenerTodosRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM Rol ORDER BY id_rol";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre")
                );
                roles.add(rol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener roles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return roles;
    }

    // OBTENER ROL POR ID
    public static Rol obtenerRolPorId(int id) {
        String sql = "SELECT * FROM Rol WHERE id_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // BUSCAR ROLES POR NOMBRE
    public static List<Rol> buscarRoles(String criterio) {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM Rol WHERE nombre ILIKE ? ORDER BY id_rol";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre")
                );
                roles.add(rol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar roles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return roles;
    }

    // OBTENER ROL POR NOMBRE (útil para validaciones)
    public static Rol obtenerRolPorNombre(String nombre) {
        String sql = "SELECT * FROM Rol WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener rol por nombre: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // VERIFICAR SI EXISTE UN ROL
    public static boolean existeRol(String nombre) {
        String sql = "SELECT COUNT(*) FROM Rol WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    // CONTAR TOTAL DE ROLES
    public static int contarRoles() {
        String sql = "SELECT COUNT(*) FROM Rol";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar roles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }
}