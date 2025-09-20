package clasesjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioRolDAO {

    // INSERTAR USUARIO_ROL
    public boolean insertarUsuarioRol(UsuarioRol usuarioRol) {
        String sql = "INSERT INTO Usuario_Rol (id_usuario, id_rol) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioRol.getUsuario().getIdUsuario());
            pstmt.setInt(2, usuarioRol.getRol().getIdRol());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // OBTENER TODOS LOS USUARIO_ROL
    public List<UsuarioRol> obtenerTodosUsuarioRol() {
        List<UsuarioRol> usuarioRoles = new ArrayList<>();
        String sql = "SELECT ur.id_usuario_rol, ur.id_usuario, ur.id_rol, " +
                     "u.username, u.password, r.nombre as rol_nombre " +
                     "FROM Usuario_Rol ur " +
                     "INNER JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                     "INNER JOIN Rol r ON ur.id_rol = r.id_rol " +
                     "ORDER BY ur.id_usuario_rol";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("rol_nombre")
                );

                UsuarioRol usuarioRol = new UsuarioRol(
                    rs.getInt("id_usuario_rol"),
                    usuario,
                    rol
                );

                usuarioRoles.add(usuarioRol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarioRoles;
    }

    // OBTENER USUARIO_ROL POR ID
    public UsuarioRol obtenerUsuarioRolPorId(int id) {
        String sql = "SELECT ur.id_usuario_rol, ur.id_usuario, ur.id_rol, " +
                     "u.username, u.password, r.nombre as rol_nombre " +
                     "FROM Usuario_Rol ur " +
                     "INNER JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                     "INNER JOIN Rol r ON ur.id_rol = r.id_rol " +
                     "WHERE ur.id_usuario_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("rol_nombre")
                );

                return new UsuarioRol(
                    rs.getInt("id_usuario_rol"),
                    usuario,
                    rol
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario_rol por ID: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // OBTENER ROLES POR USUARIO
    public List<UsuarioRol> obtenerRolesPorUsuario(int idUsuario) {
        List<UsuarioRol> usuarioRoles = new ArrayList<>();
        String sql = "SELECT ur.id_usuario_rol, ur.id_usuario, ur.id_rol, " +
                     "u.username, u.password, r.nombre as rol_nombre " +
                     "FROM Usuario_Rol ur " +
                     "INNER JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                     "INNER JOIN Rol r ON ur.id_rol = r.id_rol " +
                     "WHERE ur.id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("rol_nombre")
                );

                UsuarioRol usuarioRol = new UsuarioRol(
                    rs.getInt("id_usuario_rol"),
                    usuario,
                    rol
                );

                usuarioRoles.add(usuarioRol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener roles del usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarioRoles;
    }

    // OBTENER USUARIOS POR ROL
    public List<UsuarioRol> obtenerUsuariosPorRol(int idRol) {
        List<UsuarioRol> usuarioRoles = new ArrayList<>();
        String sql = "SELECT ur.id_usuario_rol, ur.id_usuario, ur.id_rol, " +
                     "u.username, u.password, r.nombre as rol_nombre " +
                     "FROM Usuario_Rol ur " +
                     "INNER JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                     "INNER JOIN Rol r ON ur.id_rol = r.id_rol " +
                     "WHERE ur.id_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idRol);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("rol_nombre")
                );

                UsuarioRol usuarioRol = new UsuarioRol(
                    rs.getInt("id_usuario_rol"),
                    usuario,
                    rol
                );

                usuarioRoles.add(usuarioRol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios por rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarioRoles;
    }

    // ACTUALIZAR USUARIO_ROL
    public boolean actualizarUsuarioRol(UsuarioRol usuarioRol) {
        String sql = "UPDATE Usuario_Rol SET id_usuario = ?, id_rol = ? WHERE id_usuario_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioRol.getUsuario().getIdUsuario());
            pstmt.setInt(2, usuarioRol.getRol().getIdRol());
            pstmt.setInt(3, usuarioRol.getIdUsuarioRol());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ELIMINAR USUARIO_ROL
    public boolean eliminarUsuarioRol(int idUsuarioRol) {
        String sql = "DELETE FROM Usuario_Rol WHERE id_usuario_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuarioRol);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Asignación de rol eliminada correctamente");
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    // VERIFICAR SI EXISTE USUARIO_ROL
    public boolean existeUsuarioRol(int idUsuario, int idRol) {
        String sql = "SELECT COUNT(*) FROM Usuario_Rol WHERE id_usuario = ? AND id_rol = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idRol);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    // ELIMINAR TODOS LOS ROLES DE UN USUARIO
    public boolean eliminarRolesDeUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario_Rol WHERE id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar roles del usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // CONTAR ROLES DE UN USUARIO
    public int contarRolesDeUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM Usuario_Rol WHERE id_usuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar roles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // BUSCAR USUARIO_ROL POR CRITERIO
    public List<UsuarioRol> buscarUsuarioRol(String criterio) {
        List<UsuarioRol> usuarioRoles = new ArrayList<>();
        String sql = "SELECT ur.id_usuario_rol, ur.id_usuario, ur.id_rol, " +
                     "u.username, u.password, r.nombre as rol_nombre " +
                     "FROM Usuario_Rol ur " +
                     "INNER JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                     "INNER JOIN Rol r ON ur.id_rol = r.id_rol " +
                     "WHERE u.username ILIKE ? OR r.nombre ILIKE ? " +
                     "ORDER BY ur.id_usuario_rol";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            pstmt.setString(2, parametroBusqueda);
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("rol_nombre")
                );

                UsuarioRol usuarioRol = new UsuarioRol(
                    rs.getInt("id_usuario_rol"),
                    usuario,
                    rol
                );

                usuarioRoles.add(usuarioRol);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuario_rol: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usuarioRoles;
    }
}