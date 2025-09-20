package clasesjava;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OcupacionDAO {

    // ====== Asignar (Entrada) ======
    public static boolean asignarVehiculoAEspacio(int idVehiculo, int idEspacio) {
        final String checkEspacioLibre = """
            SELECT 1
              FROM ocupacion
             WHERE id_espacio = ? AND fin IS NULL
        """;
        final String checkVehiculoLibre = """
            SELECT 1
              FROM ocupacion
             WHERE id_vehiculo = ? AND fin IS NULL
        """;
        final String insertOcup = """
            INSERT INTO ocupacion (id_vehiculo, id_espacio) VALUES (?, ?)
        """;
        // Si NO usas trigger, también:
        final String setOcupado = "UPDATE espacio SET estado='ocupado' WHERE id_espacio=?";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);

            // ¿Espacio ya ocupado?
            try (PreparedStatement ps = con.prepareStatement(checkEspacioLibre)) {
                ps.setInt(1, idEspacio);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        con.rollback();
                        throw new SQLException("El espacio ya está ocupado.");
                    }
                }
            }
            // ¿Vehículo ya asignado a otro espacio?
            try (PreparedStatement ps = con.prepareStatement(checkVehiculoLibre)) {
                ps.setInt(1, idVehiculo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        con.rollback();
                        throw new SQLException("El vehículo ya tiene una ocupación activa.");
                    }
                }
            }
            // Insertar ocupación
            try (PreparedStatement ps = con.prepareStatement(insertOcup)) {
                ps.setInt(1, idVehiculo);
                ps.setInt(2, idEspacio);
                ps.executeUpdate();
            }
            // Si NO usas trigger, marca ocupado:
            // try (PreparedStatement ps = con.prepareStatement(setOcupado)) {
            //     ps.setInt(1, idEspacio);
            //     ps.executeUpdate();
            // }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ====== Liberar (Salida) ======
    public static boolean liberarEspacio(int idEspacio) {
        final String closeOcup = """
            UPDATE ocupacion
               SET fin = now()
             WHERE id_espacio = ? AND fin IS NULL
        """;
        // Si NO usas trigger, marcar libre si ya no hay activas:
        final String liberarEspacio = """
            UPDATE espacio e
               SET estado = 'libre'
             WHERE e.id_espacio = ?
               AND NOT EXISTS (SELECT 1 FROM ocupacion o WHERE o.id_espacio = e.id_espacio AND o.fin IS NULL)
        """;

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            boolean updated;
            try (PreparedStatement ps = con.prepareStatement(closeOcup)) {
                ps.setInt(1, idEspacio);
                updated = ps.executeUpdate() > 0;
            }
            // Si NO usas trigger:
            // try (PreparedStatement ps = con.prepareStatement(liberarEspacio)) {
            //     ps.setInt(1, idEspacio);
            //     ps.executeUpdate();
            // }
            con.commit();
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ====== Alertas: ocupaciones activas que superan umbral ======
    public static class OcupacionAlerta {
        public int idOcupacion;
        public int idVehiculo;
        public int idEspacio;
        public Timestamp inicio;
        public long minutos; // duración en minutos
        public String placa; // opcional para UI
    }

    public static List<OcupacionAlerta> obtenerAlertas(Duration umbral) {
        // umbral en minutos
        long min = umbral.toMinutes();
        final String sql = """
            SELECT o.id_ocupacion, o.id_vehiculo, o.id_espacio, o.inicio, v.placa,
                   EXTRACT(EPOCH FROM (now() - o.inicio))/60 AS minutos
              FROM ocupacion o
              JOIN vehiculo v ON v.id_vehiculo = o.id_vehiculo
             WHERE o.fin IS NULL
               AND (now() - o.inicio) > (make_interval(mins => ?))
             ORDER BY o.inicio
        """;
        List<OcupacionAlerta> res = new ArrayList<>();
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, (int) min);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OcupacionAlerta a = new OcupacionAlerta();
                    a.idOcupacion = rs.getInt("id_ocupacion");
                    a.idVehiculo  = rs.getInt("id_vehiculo");
                    a.idEspacio   = rs.getInt("id_espacio");
                    a.inicio      = rs.getTimestamp("inicio");
                    a.minutos     = Math.round(rs.getDouble("minutos"));
                    a.placa       = rs.getString("placa");
                    res.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
