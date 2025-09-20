/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaestroDAO {

    // CREATE
    public static boolean crear(Maestro m, String apellidoM, String telefono) {
        String sqlPersona = "INSERT INTO Persona (nombre, apellidoP, apellidoM, telefono) " +
                            "VALUES (?, ?, ?, ?) RETURNING id_persona";
        String sqlMaestro = "INSERT INTO Maestro (id_persona, numero_empleado) VALUES (?, ?)";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            int idPersona;

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, m.getNombre());
                ps.setString(2, m.getApellido());
                ps.setString(3, apellidoM);
                ps.setString(4, telefono);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    idPersona = rs.getInt(1);
                    m.setIdPersona(idPersona);
                }
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlMaestro)) {
                ps2.setInt(1, m.getIdPersona());
                ps2.setInt(2, m.getClaveEmpleado()); // mapeo claveEmpleado -> numero_empleado
                ps2.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // READ por id
    public static Maestro obtenerPorId(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, m.numero_empleado " +
                     "FROM Maestro m JOIN Persona p ON p.id_persona = m.id_persona " +
                     "WHERE p.id_persona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Maestro(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellidoP"),
                        rs.getInt("numero_empleado"),
                        /* departamento en POJO: no está en BD */ ""
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // LISTAR
    public static List<Maestro> listarTodos() {
        List<Maestro> list = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, m.numero_empleado " +
                     "FROM Maestro m JOIN Persona p ON p.id_persona = m.id_persona " +
                     "ORDER BY p.nombre, p.apellidoP";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Maestro(
                    rs.getInt("id_persona"),
                    rs.getString("nombre"),
                    rs.getString("apellidoP"),
                    rs.getInt("numero_empleado"),
                    ""
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // BUSCAR (nombre, apellido, numero_empleado)
    public static List<Maestro> buscar(String texto) {
        List<Maestro> list = new ArrayList<>();
        String like = "%" + texto + "%";
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, m.numero_empleado " +
                     "FROM Maestro m JOIN Persona p ON p.id_persona = m.id_persona " +
                     "WHERE p.nombre ILIKE ? OR p.apellidoP ILIKE ? OR CAST(m.numero_empleado AS TEXT) ILIKE ? " +
                     "ORDER BY p.nombre, p.apellidoP";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Maestro(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellidoP"),
                        rs.getInt("numero_empleado"),
                        ""
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // UPDATE (Persona + Maestro)
    public static boolean actualizar(Maestro m, String apellidoM, String telefono) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, apellidoP = ?, apellidoM = ?, telefono = ? " +
                            "WHERE id_persona = ?";
        String sqlMaestro = "UPDATE Maestro SET numero_empleado = ? WHERE id_persona = ?";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, m.getNombre());
                ps.setString(2, m.getApellido());
                ps.setString(3, apellidoM);
                ps.setString(4, telefono);
                ps.setInt(5, m.getIdPersona());
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlMaestro)) {
                ps2.setInt(1, m.getClaveEmpleado());
                ps2.setInt(2, m.getIdPersona());
                ps2.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // DELETE (CASCADE)
    public static boolean eliminar(int idPersona) {
        String sql = "DELETE FROM Persona WHERE id_persona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
