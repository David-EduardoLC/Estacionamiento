/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

/**
 *
 * @author MSI A15
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    // CREATE (Persona -> Alumno)
    public static boolean crear(Alumno a, String apellidoM, String telefono, String estado, String descripcion) {
        String sqlPersona = "INSERT INTO Persona (nombre, apellidoP, apellidoM, telefono) " +
                            "VALUES (?, ?, ?, ?) RETURNING id_persona";
        String sqlAlumno  = "INSERT INTO Alumno (id_persona, matricula, semestre, estado, descripcion) " +
                            "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);
            int idPersona;

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getApellido());     // mapeo: apellido -> apellidoP
                ps.setString(3, apellidoM);           // segundo apellido (opcional)
                ps.setString(4, telefono);            // contacto en Persona
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    idPersona = rs.getInt(1);
                    a.setIdPersona(idPersona);
                }
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlAlumno)) {
                ps2.setInt(1, a.getIdPersona());
                ps2.setString(2, a.getNumeroControl()); // mapeo: numeroControl -> matricula
                ps2.setInt(3, a.getSemestre());
                ps2.setString(4, estado);
                ps2.setString(5, descripcion);
                ps2.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // READ por id
    public static Alumno obtenerPorId(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, a.matricula, a.semestre, a.estado, a.descripcion " +
                     "FROM Alumno a JOIN Persona p ON p.id_persona = a.id_persona " +
                     "WHERE p.id_persona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // tu POJO no tiene estado/descripcion; puedes leerlos si los quieres mostrar
                    return new Alumno(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellidoP"),
                        rs.getString("matricula"),
                        /* carrera: no existe en DB */ "",
                        rs.getInt("semestre")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // LISTAR
    public static List<Alumno> listarTodos() {
        List<Alumno> list = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, a.matricula, a.semestre, a.estado " +
                     "FROM Alumno a JOIN Persona p ON p.id_persona = a.id_persona " +
                     "ORDER BY p.nombre, p.apellidoP";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Alumno(
                    rs.getInt("id_persona"),
                    rs.getString("nombre"),
                    rs.getString("apellidoP"),
                    rs.getString("matricula"),
                    "", // carrera no existe en tu esquema
                    rs.getInt("semestre")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // BUSCAR (nombre, apellidos, matricula)
    public static List<Alumno> buscar(String texto) {
        List<Alumno> list = new ArrayList<>();
        String like = "%" + texto + "%";
        String sql = "SELECT p.id_persona, p.nombre, p.apellidoP, a.matricula, a.semestre " +
                     "FROM Alumno a JOIN Persona p ON p.id_persona = a.id_persona " +
                     "WHERE p.nombre ILIKE ? OR p.apellidoP ILIKE ? OR a.matricula ILIKE ? " +
                     "ORDER BY p.nombre, p.apellidoP";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Alumno(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellidoP"),
                        rs.getString("matricula"),
                        "",
                        rs.getInt("semestre")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // UPDATE (Persona + Alumno)
    public static boolean actualizar(Alumno a, String apellidoM, String telefono, String estado, String descripcion) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, apellidoP = ?, apellidoM = ?, telefono = ? " +
                            "WHERE id_persona = ?";
        String sqlAlumno  = "UPDATE Alumno SET matricula = ?, semestre = ?, estado = ?, descripcion = ? " +
                            "WHERE id_persona = ?";

        try (Connection con = ConexionDB.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlPersona)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getApellido());
                ps.setString(3, apellidoM);
                ps.setString(4, telefono);
                ps.setInt(5, a.getIdPersona());
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlAlumno)) {
                ps2.setString(1, a.getNumeroControl()); // matricula
                ps2.setInt(2, a.getSemestre());
                ps2.setString(3, estado);
                ps2.setString(4, descripcion);
                ps2.setInt(5, a.getIdPersona());
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
