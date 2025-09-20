package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    // ===================== CREATE SIMPLE =====================
    public static boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (username, password) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ===================== CREATE COMPLETO (con rol) =====================
    public static boolean crearUsuario(String nombre, String apellidoPaterno, String apellidoMaterno,
                                       String telefono, String email, String password, int idRol) {
        String sql = "INSERT INTO Usuario (nombre, apellido_paterno, apellido_materno, telefono, correo, contraseña) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlRol = "INSERT INTO Usuario_Rol (id_usuario, id_rol) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtRol = conn.prepareStatement(sqlRol)) {

            // Insertar usuario
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidoPaterno);
            pstmt.setString(3, apellidoMaterno);
            pstmt.setString(4, telefono);
            pstmt.setString(5, email);
            pstmt.setString(6, password);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    // Asignar rol
                    pstmtRol.setInt(1, idUsuario);
                    pstmtRol.setInt(2, idRol);
                    pstmtRol.executeUpdate();

                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ===================== READ =====================
    public static List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario ORDER BY id_usuario";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                usuarios.add(u);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarios;
    }

    public static Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // ===================== UPDATE =====================
    public static boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET username = ?, password = ? WHERE id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setInt(3, usuario.getIdUsuario());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ===================== DELETE =====================
    public static boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario a eliminar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ===================== SEARCH =====================
    public static List<Usuario> buscarUsuarios(String criterio) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE username ILIKE ? ORDER BY id_usuario";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + criterio + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                usuarios.add(u);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarios;
    }

    // ===================== VALIDACIONES =====================
    public static boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE correo = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
}
