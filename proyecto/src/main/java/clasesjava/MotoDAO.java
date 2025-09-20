package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {

    public static int crearMotoCompleta(Moto moto) {
        final String sqlVehiculo = """
            INSERT INTO vehiculo (placa, marca, modelo, id_persona)
            VALUES (?, ?, ?, ?)
            RETURNING id_vehiculo
        """;
        final String sqlMoto = """
            INSERT INTO moto (id_vehiculo, tipo_moto)
            VALUES (?, ?)
        """;

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);

            int idVehiculo;
            try (PreparedStatement psV = con.prepareStatement(sqlVehiculo)) {
                psV.setString(1, moto.getPlaca());
                psV.setString(2, moto.getMarca());
                psV.setString(3, moto.getModelo());
                psV.setInt(4, moto.getIdPersona());

                try (ResultSet rs = psV.executeQuery()) {
                    if (!rs.next()) throw new SQLException("No se obtuvo id_vehiculo.");
                    idVehiculo = rs.getInt(1);
                }
            }

            try (PreparedStatement psM = con.prepareStatement(sqlMoto)) {
                psM.setInt(1, idVehiculo);
                psM.setString(2, moto.getTipoMoto());
                psM.executeUpdate();
            }

            con.commit();
            return idVehiculo;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean insertarMoto(int idVehiculo, String tipoMoto) {
        final String sql = "INSERT INTO moto (id_vehiculo, tipo_moto) VALUES (?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            ps.setString(2, tipoMoto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Moto obtenerPorIdVehiculo(int idVehiculo) {
        final String sql = """
            SELECT v.id_vehiculo, v.placa, v.marca, v.modelo, v.id_persona, m.tipo_moto
            FROM vehiculo v
            JOIN moto m ON m.id_vehiculo = v.id_vehiculo
            WHERE v.id_vehiculo = ?
        """;
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Moto(
                        rs.getInt("id_vehiculo"),
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("id_persona"),
                        rs.getString("tipo_moto")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Moto> listarTodas() {
        final String sql = """
            SELECT v.id_vehiculo, v.placa, v.marca, v.modelo, v.id_persona, m.tipo_moto
            FROM vehiculo v
            JOIN moto m ON m.id_vehiculo = v.id_vehiculo
            ORDER BY v.placa
        """;
        List<Moto> lista = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Moto(
                    rs.getInt("id_vehiculo"),
                    rs.getString("placa"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("id_persona"),
                    rs.getString("tipo_moto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean actualizarTipoMoto(int idVehiculo, String nuevoTipoMoto) {
        final String sql = "UPDATE moto SET tipo_moto = ? WHERE id_vehiculo = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoTipoMoto);
            ps.setInt(2, idVehiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarVehiculoDeMoto(int idVehiculo, String placa, String marca, String modelo, int idPersona) {
        final String sql = """
            UPDATE vehiculo
               SET placa = ?, marca = ?, modelo = ?, id_persona = ?
             WHERE id_vehiculo = ?
        """;
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, placa);
            ps.setString(2, marca);
            ps.setString(3, modelo);
            ps.setInt(4, idPersona);
            ps.setInt(5, idVehiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean existeMotoPorVehiculo(int idVehiculo) {
        final String sql = "SELECT 1 FROM moto WHERE id_vehiculo = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarMoto(int idVehiculo) {
        final String sql = "DELETE FROM moto WHERE id_vehiculo = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
}
