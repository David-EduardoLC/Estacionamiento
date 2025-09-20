package complemetos_;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class consultasUsuario {
    
    private Connection conexion;
    
    public consultasUsuario(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    public boolean verificarUsuario(String email, String password) {
         // ✅ CONSULTA CORREGIDA - Usa los nombres exactos de tu BD
    String sql = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
    //                ✅ Mayúscula  ✅ con ñ
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        
        ResultSet rs = pstmt.executeQuery();
        return rs.next(); // Si hay resultados, credenciales son válidas
        
    } catch (SQLException e) {
        System.err.println("Error en verificarUsuario: " + e.getMessage());
        return false;
    }
    }
    
  
    public boolean registrarUsuario(String nombre, String apellido, String email, String password) {
        String sql = "INSERT INTO Usuario (nombre, apellido, email, password) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, email);
            pst.setString(4, password); // Contraseña en texto plano
            
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    
   
    public boolean asignarRolUsuario(String email, int idRol) {
        String sql = "INSERT INTO UsuarioRol (id_usuario, id_rol) " +
                    "SELECT u.id_usuario, ? FROM Usuario u WHERE u.email = ?";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(1, idRol);
            pst.setString(2, email);
            
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al asignar rol: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) as count FROM Usuario WHERE email = ?";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar email: " + e.getMessage());
        }
        
        return false;
    }
    
   
    public int obtenerIdUsuario(String email) {
        String sql = "SELECT id_usuario FROM Usuario WHERE correo = ?";
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("id_usuario");
        }
        return -1; // No encontrado
        
    } catch (SQLException e) {
        System.err.println("Error al obtener ID: " + e.getMessage());
        return -1;
    }
    }
    
   
    public boolean actualizarPassword(String email, String nuevaPassword) {
        String sql = "UPDATE Usuario SET password = ? WHERE email = ?";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, nuevaPassword); // Contraseña en texto plano
            pst.setString(2, email);
            
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar contraseña: " + e.getMessage());
            return false;
        }
    }
    
   
    public String obtenerRolesUsuario(String email) {
        String sql = "SELECT r.nombre FROM Rol r " +
                "JOIN Usuario_Rol ur ON r.id_rol = ur.id_rol " +
                "JOIN Usuario u ON ur.id_usuario = u.id_usuario " +
                "WHERE u.correo = ?";
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        
        StringBuilder roles = new StringBuilder();
        while (rs.next()) {
            if (roles.length() > 0) roles.append(", ");
            roles.append(rs.getString("nombre"));
        }
        return roles.toString();
        
    } catch (SQLException e) {
        System.err.println("Error al obtener roles: " + e.getMessage());
        return "Sin roles";
    }
    }
    
    
    public ResultSet obtenerUsuarioCompleto(String email) {
        String sql = "SELECT u.*, r.nombre as rol FROM Usuario u " +
                    "LEFT JOIN UsuarioRol ur ON u.id_usuario = ur.id_usuario " +
                    "LEFT JOIN Rol r ON ur.id_rol = r.id_rol " +
                    "WHERE u.email = ?";
        
        try {
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, email);
            return pst.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }
    
    
    public boolean tienePermiso(String email, String permiso) {
        String sql = "SELECT COUNT(*) as count FROM Usuario u " +
                    "JOIN UsuarioRol ur ON u.id_usuario = ur.id_usuario " +
                    "JOIN RolPermiso rp ON ur.id_rol = rp.id_rol " +
                    "JOIN Permiso p ON rp.id_permiso = p.id_permiso " +
                    "WHERE u.email = ? AND p.nombre = ?";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setString(2, permiso);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar permiso: " + e.getMessage());
        }
        
        return false;
    }
}