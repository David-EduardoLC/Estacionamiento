package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

public class EspacioDAO {

    // Valores permitidos por los CHECK de la BD
    private static final Set<String> TIPOS_VALIDOS   = Set.of("carro", "moto");
    private static final Set<String> ESTADOS_VALIDOS = Set.of("libre", "ocupado");

    /** Normaliza y mapea el tipo_lugar a los valores del CHECK */
    private static String normalizarTipo(String t) {
        if (t == null) return null;
        String s = t.trim().toLowerCase();
        if (s.equals("auto") || s.equals("coche")) s = "carro";
        return s;
    }

    /** Normaliza y mapea el estado a los valores del CHECK */
    private static String normalizarEstado(String e) {
        if (e == null) return null;
        String s = e.trim().toLowerCase();
        if (s.equals("disponible") || s.equals("dispon")) s = "libre";
        if (s.equals("ocupada")) s = "ocupado";
        return s;
    }

    private static boolean validar(EspacioC espacio, String tipo, String estado) {
        if (!TIPOS_VALIDOS.contains(tipo)) {
            JOptionPane.showMessageDialog(null,
                "tipo_lugar inválido. Permitidos: 'carro' o 'moto'. Recibido: " + espacio.getTipoLugar(),
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!ESTADOS_VALIDOS.contains(estado)) {
            JOptionPane.showMessageDialog(null,
                "estado inválido. Permitidos: 'libre' o 'ocupado'. Recibido: " + espacio.getEstado(),
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (espacio.getIdZona() <= 0) {
            JOptionPane.showMessageDialog(null,
                "id_zona debe ser mayor que cero.",
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /** Verifica que exista la zona antes de tocar la FK */
    private static boolean existeZona(Connection conn, int idZona) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM zona WHERE id_zona = ?")) {
            ps.setInt(1, idZona);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Devuelve el id_zona dada su descripcion (A..F) en un estacionamiento concreto */
    public static Integer obtenerIdZonaPorDescripcion(String descripcion, int idEstacionamiento) {
        if (descripcion == null) return null;
        String letra = descripcion.trim().toUpperCase();
        final String sql =
            "SELECT id_zona " +
            "FROM zona " +
            "WHERE TRIM(UPPER(descripcion)) = ? AND id_estacionamiento = ? " +
            "ORDER BY id_zona LIMIT 1";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, letra);
            ps.setInt(2, idEstacionamiento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al buscar zona por descripción: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /** Lista (id_zona, descripcion) para poblar combos en UI */
    public static List<ZonaDTO> listarZonas() {
        List<ZonaDTO> list = new ArrayList<>();
        final String sql = "SELECT id_zona, descripcion FROM zona ORDER BY id_zona";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ZonaDTO(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al listar zonas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    /** Inserción estándar recibiendo id_zona */
    public static boolean insertarEspacio(EspacioC espacio, String letraZona, int idEstacionamiento) {
    // lógica para calcular idZona y hacer el INSERT
        final String sql = "INSERT INTO espacio (tipo_lugar, estado, id_zona) VALUES (?, ?, ?)";

        String tipo   = normalizarTipo(espacio.getTipoLugar());
        String estado = normalizarEstado(espacio.getEstado());
        if (!validar(espacio, tipo, estado)) return false;

        try (Connection conn = ConexionDB.conectar()) {

            if (!existeZona(conn, espacio.getIdZona())) {
                JOptionPane.showMessageDialog(null,
                    "La zona con id " + espacio.getIdZona() + " no existe. Selecciona una zona válida.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, tipo);
                ps.setString(2, estado);
                ps.setInt(3, espacio.getIdZona());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            mostrarMensajeSQL(e, "insertar espacio");
            return false;
        }
    }

    

    public static List<EspacioC> obtenerTodosEspacios() {
        List<EspacioC> lista = new ArrayList<>();
        final String sql = "SELECT id_espacio, tipo_lugar, estado, id_zona " +
                           "FROM espacio ORDER BY id_zona, id_espacio";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new EspacioC(
                        rs.getInt("id_espacio"),
                        rs.getString("tipo_lugar"),
                        rs.getString("estado"),
                        rs.getInt("id_zona")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al obtener espacios: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public static EspacioC obtenerEspacioPorId(int idEspacio) {
        final String sql = "SELECT id_espacio, tipo_lugar, estado, id_zona " +
                           "FROM espacio WHERE id_espacio = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEspacio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EspacioC(
                            rs.getInt("id_espacio"),
                            rs.getString("tipo_lugar"),
                            rs.getString("estado"),
                            rs.getInt("id_zona")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al obtener espacio: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static List<EspacioC> obtenerEspaciosPorZona(int idZona) {
        List<EspacioC> lista = new ArrayList<>();
        final String sql = "SELECT id_espacio, tipo_lugar, estado, id_zona " +
                           "FROM espacio WHERE id_zona = ? ORDER BY id_espacio";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idZona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new EspacioC(
                            rs.getInt("id_espacio"),
                            rs.getString("tipo_lugar"),
                            rs.getString("estado"),
                            rs.getInt("id_zona")
                    ));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al obtener espacios por zona: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public static boolean actualizarEspacio(EspacioC espacio) {
        final String sql = "UPDATE espacio SET tipo_lugar = ?, estado = ?, id_zona = ? WHERE id_espacio = ?";

        String tipo   = normalizarTipo(espacio.getTipoLugar());
        String estado = normalizarEstado(espacio.getEstado());
        if (!validar(espacio, tipo, estado)) return false;

        try (Connection conn = ConexionDB.conectar()) {

            if (!existeZona(conn, espacio.getIdZona())) {
                JOptionPane.showMessageDialog(null,
                    "La zona con id " + espacio.getIdZona() + " no existe. Selecciona una zona válida.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, tipo);
                ps.setString(2, estado);
                ps.setInt(3, espacio.getIdZona());
                ps.setInt(4, espacio.getIdEspacio());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            mostrarMensajeSQL(e, "actualizar espacio");
            return false;
        }
    }

    public static boolean eliminarEspacio(int idEspacio) {
        final String sql = "DELETE FROM espacio WHERE id_espacio = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEspacio);
            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null,
                    "Espacio eliminado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se encontró el espacio a eliminar",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            mostrarMensajeSQL(e, "eliminar espacio");
            return false;
        }
    }

    // Búsqueda (PostgreSQL ILIKE)
    public static List<EspacioC> buscarEspacios(String criterio, Integer idZona) {
        List<EspacioC> lista = new ArrayList<>();
        StringBuilder sb = new StringBuilder(
            "SELECT id_espacio, tipo_lugar, estado, id_zona FROM espacio " +
            "WHERE (tipo_lugar ILIKE ? OR estado ILIKE ?)"
        );
        if (idZona != null) sb.append(" AND id_zona = ?");
        sb.append(" ORDER BY id_zona, id_espacio");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            String like = "%" + (criterio == null ? "" : criterio.trim()) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            if (idZona != null) ps.setInt(3, idZona);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new EspacioC(
                            rs.getInt("id_espacio"),
                            rs.getString("tipo_lugar"),
                            rs.getString("estado"),
                            rs.getInt("id_zona")
                    ));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al buscar espacios: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    /** Mensajes legibles para errores comunes de SQLState */
    private static void mostrarMensajeSQL(SQLException e, String operacion) {
        // 23503 = foreign key; 23514 = check constraint
        String sqlState = e.getSQLState();
        if ("23503".equals(sqlState)) {
            JOptionPane.showMessageDialog(null,
                "No se pudo " + operacion + " por violación de llave foránea (zona inexistente).",
                "FK inválida", JOptionPane.ERROR_MESSAGE);
        } else if ("23514".equals(sqlState)) {
            JOptionPane.showMessageDialog(null,
                "No se pudo " + operacion + " por violación de CHECK. Revisa tipo_lugar/estado.",
                "CHECK inválido", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Error al " + operacion + ": " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** DTO simple para combos */
    public static class ZonaDTO {
        public final int idZona;
        public final String descripcion;
        public ZonaDTO(int idZona, String descripcion) {
            this.idZona = idZona;
            this.descripcion = descripcion;
        }
        @Override public String toString() { return descripcion; } // mostrará A..F en combo
    }
}
