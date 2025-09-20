package clasesjava;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PersonaDAO {
    public static boolean actualizarTelefono(int idPersona, String telefono) {
        String sql = "UPDATE Persona SET telefono = ? WHERE id_persona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, telefono);
            ps.setInt(2, idPersona);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

