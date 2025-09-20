package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VehiculoDAO {

    public static boolean insertarVehiculo(Vehiculo v) {
        final String sql = "INSERT INTO vehiculo (placa, modelo, id_persona) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getIdPersona());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar vehículo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Vehiculo> obtenerTodosVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        final String sql = "SELECT id_vehiculo, placa, modelo, id_persona " +
                           "FROM vehiculo ORDER BY id_vehiculo DESC";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setModelo(rs.getString("modelo"));
                v.setIdPersona(rs.getInt("id_persona"));
                lista.add(v);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vehículos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public static Vehiculo obtenerVehiculoPorId(int idVehiculo) {
        final String sql = "SELECT id_vehiculo, placa, modelo, id_persona " +
                           "FROM vehiculo WHERE id_vehiculo = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setModelo(rs.getString("modelo"));
                    v.setIdPersona(rs.getInt("id_persona"));
                    return v;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vehículo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static Vehiculo obtenerVehiculoPorPlaca(String placa) {
        final String sql = "SELECT id_vehiculo, placa, modelo, id_persona " +
                           "FROM vehiculo WHERE placa = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setModelo(rs.getString("modelo"));
                    v.setIdPersona(rs.getInt("id_persona"));
                    return v;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar por placa: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static List<Vehiculo> obtenerVehiculosPorPersona(int idPersona) {
        List<Vehiculo> lista = new ArrayList<>();
        final String sql = "SELECT id_vehiculo, placa, modelo, id_persona " +
                           "FROM vehiculo WHERE id_persona = ? ORDER BY id_vehiculo DESC";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setModelo(rs.getString("modelo"));
                    v.setIdPersona(rs.getInt("id_persona"));
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener vehículos por persona: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public static boolean actualizarVehiculo(Vehiculo v) {
        final String sql = "UPDATE vehiculo SET placa = ?, modelo = ?, id_persona = ? " +
                           "WHERE id_vehiculo = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getIdPersona());
            ps.setInt(4, v.getIdVehiculo());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vehículo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean eliminarVehiculo(int idVehiculo) {
        final String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVehiculo);
            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Vehículo eliminado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el vehículo a eliminar",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar vehículo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /** PostgreSQL usa ILIKE (insensible a mayúsculas). En MySQL usar LOWER(...) LIKE ... */
    public static List<Vehiculo> buscarVehiculos(String criterio, Integer idPersona) {
        List<Vehiculo> lista = new ArrayList<>();
        StringBuilder sb = new StringBuilder(
            "SELECT id_vehiculo, placa, modelo, id_persona " +
            "FROM vehiculo WHERE (placa ILIKE ? OR modelo ILIKE ?)"
        );
        if (idPersona != null) sb.append(" AND id_persona = ?");
        sb.append(" ORDER BY id_vehiculo DESC");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            String like = "%" + (criterio == null ? "" : criterio.trim()) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            if (idPersona != null) ps.setInt(3, idPersona);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setModelo(rs.getString("modelo"));
                    v.setIdPersona(rs.getInt("id_persona"));
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar vehículos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    /** Verifica unicidad de la placa. Si excluirId != null, ignora ese registro (para editar). */
    public static boolean existePlaca(String placa, Integer excluirId) {
        String sql = "SELECT 1 FROM vehiculo WHERE placa = ?";
        if (excluirId != null) sql += " AND id_vehiculo <> ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, placa);
            if (excluirId != null) ps.setInt(2, excluirId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar placa: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return true; // para prevenir inserción si hay error
        }
    }
    
    public static int insertarVehiculoYRetornarId(Vehiculo v) {
    String sql = "INSERT INTO vehiculo (placa, modelo, id_persona) VALUES (?, ?, ?) RETURNING id_vehiculo";
    try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, v.getPlaca());
        ps.setString(2, v.getModelo());
        ps.setInt(3, v.getIdPersona());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_vehiculo");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // Si falla, devuelve -1
}
    public static List<VehiculoHistorialDTO> historialPorPersona(int idPersona) {
        String sql = """
            SELECT v.id_vehiculo, v.placa, r.hora_ent, r.hora_salida, r.id_espacio
            FROM Vehiculo v
            LEFT JOIN Registro r ON r.id_vehiculo = v.id_vehiculo
            WHERE v.id_persona = ?
            ORDER BY r.hora_ent DESC NULLS LAST
        """;
        List<VehiculoHistorialDTO> out = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Time ent = rs.getTime("hora_ent");
                    Time sal = rs.getTime("hora_salida");
                    out.add(new VehiculoHistorialDTO(
                        rs.getInt("id_vehiculo"),
                        rs.getString("placa"),
                        ent != null ? ent.toLocalTime() : null,
                        sal != null ? sal.toLocalTime() : null,
                        (Integer) rs.getObject("id_espacio")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return out;
    }

}
