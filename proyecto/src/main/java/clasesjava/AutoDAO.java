package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO {

    // ================= Helpers =================
    private static Auto mapRow(ResultSet rs) throws SQLException {
        // Si tu tabla vehiculo NO tiene 'marca', usa null en su lugar y
        // asegúrate de tener un constructor que la acepte como null.
        return new Auto(
            rs.getInt("id_vehiculo"),
            rs.getString("placa"),
            /* marca */ null,                  // <-- ajusta si manejas 'marca'
            rs.getString("modelo"),
            rs.getInt("id_persona"),
            rs.getString("tipo_carro")
        );
    }

    private static String normTipo(String t) {
        return t == null ? null : t.trim().toUpperCase();
    }

    // ================= CREATE ===================
    /** Inserta en vehiculo y luego en carro dentro de UNA transacción */
    public static int insertarAuto(Auto a) {
        final String sqlVeh = "INSERT INTO vehiculo (placa, modelo, id_persona) VALUES (?, ?, ?) RETURNING id_vehiculo";
        final String sqlCar = "INSERT INTO carro (id_vehiculo, tipo_carro) VALUES (?, ?)";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(sqlVeh)) {

                ps1.setString(1, a.getPlaca());
                ps1.setString(2, a.getModelo());
                ps1.setInt(3, a.getIdPersona());

                int idVehiculo;
                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next()) throw new SQLException("No se generó id_vehiculo");
                    idVehiculo = rs.getInt(1);
                }
                a.setIdVehiculo(idVehiculo);

                try (PreparedStatement ps2 = con.prepareStatement(sqlCar)) {
                    ps2.setInt(1, idVehiculo);
                    ps2.setString(2, normTipo(a.getTipoAuto()));
                    ps2.executeUpdate();
                }

                con.commit();
                return idVehiculo;

            } catch (SQLException ex) {
                try { con.rollback(); } catch (SQLException ignore) {}
                ex.printStackTrace();
                return -1;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignore) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ================= UPDATE ===================
    /** Actualiza vehiculo y carro dentro de UNA transacción */
    public static boolean actualizarAuto(Auto a) {
        final String sqlVeh = "UPDATE vehiculo SET placa = ?, modelo = ?, id_persona = ? WHERE id_vehiculo = ?";
        final String sqlCar = "UPDATE carro SET tipo_carro = ? WHERE id_vehiculo = ?";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(sqlVeh);
                 PreparedStatement ps2 = con.prepareStatement(sqlCar)) {

                ps1.setString(1, a.getPlaca());
                ps1.setString(2, a.getModelo());
                ps1.setInt(3, a.getIdPersona());
                ps1.setInt(4, a.getIdVehiculo());
                ps1.executeUpdate();

                ps2.setString(1, normTipo(a.getTipoAuto()));
                ps2.setInt(2, a.getIdVehiculo());
                ps2.executeUpdate();

                con.commit();
                return true;

            } catch (SQLException ex) {
                try { con.rollback(); } catch (SQLException ignore) {}
                ex.printStackTrace();
                return false;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignore) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE ===================
    /** Si NO tienes ON DELETE CASCADE en carro.id_vehiculo, borra primero 'carro' */
    public static boolean eliminarAuto(int idVehiculo) {
        final String delCar = "DELETE FROM carro WHERE id_vehiculo = ?";
        final String delVeh = "DELETE FROM vehiculo WHERE id_vehiculo = ?";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(delCar);
                 PreparedStatement ps2 = con.prepareStatement(delVeh)) {

                ps1.setInt(1, idVehiculo);
                ps1.executeUpdate();

                ps2.setInt(1, idVehiculo);
                boolean ok = ps2.executeUpdate() > 0;

                con.commit();
                return ok;

            } catch (SQLException ex) {
                try { con.rollback(); } catch (SQLException ignore) {}
                ex.printStackTrace();
                return false;
            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignore) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Útil si tu FK en carro tiene ON DELETE CASCADE */
    public static boolean eliminarVehiculoYDependencias(int idVehiculo) {
        final String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= READs ====================
    public static Auto obtenerAutoPorId(int idVehiculo) {
        final String sql =
            "SELECT v.id_vehiculo, v.placa, v.modelo, v.id_persona, c.tipo_carro " +
            "  FROM vehiculo v JOIN carro c ON c.id_vehiculo = v.id_vehiculo " +
            " WHERE v.id_vehiculo = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Auto obtenerAutoPorPlaca(String placa) {
        final String sql =
            "SELECT v.id_vehiculo, v.placa, v.modelo, v.id_persona, c.tipo_carro " +
            "  FROM vehiculo v JOIN carro c ON c.id_vehiculo = v.id_vehiculo " +
            " WHERE v.placa = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Auto> listarAutos() {
        final String sql =
            "SELECT v.id_vehiculo, v.placa, v.modelo, v.id_persona, c.tipo_carro " +
            "  FROM vehiculo v JOIN carro c ON c.id_vehiculo = v.id_vehiculo " +
            " ORDER BY v.id_vehiculo DESC";
        List<Auto> lista = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static List<Auto> obtenerAutosPorPersona(int idPersona) {
        final String sql =
            "SELECT v.id_vehiculo, v.placa, v.modelo, v.id_persona, c.tipo_carro " +
            "  FROM vehiculo v JOIN carro c ON c.id_vehiculo = v.id_vehiculo " +
            " WHERE v.id_persona = ? " +
            " ORDER BY v.id_vehiculo DESC";
        List<Auto> lista = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ================ SEARCH ====================
    public static List<Auto> buscarAutos(String criterio, Integer idPersona) {
        String like = "%" + (criterio == null ? "" : criterio.trim()) + "%";
        StringBuilder sb = new StringBuilder(
            "SELECT v.id_vehiculo, v.placa, v.modelo, v.id_persona, c.tipo_carro " +
            "  FROM vehiculo v JOIN carro c ON c.id_vehiculo = v.id_vehiculo " +
            " WHERE (v.placa ILIKE ? OR v.modelo ILIKE ? OR c.tipo_carro ILIKE ?)"
        );
        if (idPersona != null) sb.append(" AND v.id_persona = ?");
        sb.append(" ORDER BY v.id_vehiculo DESC");

        List<Auto> lista = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sb.toString())) {

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            if (idPersona != null) ps.setInt(4, idPersona);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ============== Validación ==================
    public static boolean existePlaca(String placa, Integer excluirIdVehiculo) {
        String sql = "SELECT 1 FROM vehiculo WHERE placa = ?";
        if (excluirIdVehiculo != null) sql += " AND id_vehiculo <> ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, placa);
            if (excluirIdVehiculo != null) ps.setInt(2, excluirIdVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // por seguridad, evita duplicados si falla
        }
    }
    public static boolean actualizarVehiculoDeAuto(int idVehiculo, String placa, String marca, String modelo, int idPersona) {
    Auto a = obtenerAutoPorId(idVehiculo);
    if (a == null) return false;
    a.setPlaca(placa);
    a.setModelo(modelo);
    a.setIdPersona(idPersona);
    if (marca != null) a.setMarca(marca); // si manejas marca
    return actualizarAuto(a);
}

public static boolean actualizarTipoAuto(int idVehiculo, String nuevoTipoAuto) {
    Auto a = obtenerAutoPorId(idVehiculo);
    if (a == null) return false;
    a.setTipoAuto(nuevoTipoAuto);
    return actualizarAuto(a);
}

}
