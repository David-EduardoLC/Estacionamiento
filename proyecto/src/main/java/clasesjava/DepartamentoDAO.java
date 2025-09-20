package clasesjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DepartamentoDAO {

    // OBTENER TODOS LOS DEPARTAMENTOS
    public static List<Departamento> obtenerTodosDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM Departamento ORDER BY id_departamento";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Departamento depto = new Departamento(
                    rs.getInt("id_departamento"),
                    rs.getString("nombre")
                );
                departamentos.add(depto);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener departamentos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return departamentos;
    }

    // OBTENER DEPARTAMENTO POR ID
    public static Departamento obtenerDepartamentoPorId(int id) {
        String sql = "SELECT * FROM Departamento WHERE id_departamento = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Departamento(
                    rs.getInt("id_departamento"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener departamento: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // BUSCAR DEPARTAMENTOS POR NOMBRE
    public static List<Departamento> buscarDepartamentos(String criterio) {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM Departamento WHERE nombre ILIKE ? ORDER BY id_departamento";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String parametroBusqueda = "%" + criterio + "%";
            pstmt.setString(1, parametroBusqueda);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Departamento depto = new Departamento(
                    rs.getInt("id_departamento"),
                    rs.getString("nombre")
                );
                departamentos.add(depto);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar departamentos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return departamentos;
    }

    // OBTENER DEPARTAMENTO POR NOMBRE (útil para validaciones)
    public static Departamento obtenerDepartamentoPorNombre(String nombre) {
        String sql = "SELECT * FROM Departamento WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Departamento(
                    rs.getInt("id_departamento"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener departamento por nombre: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // VERIFICAR SI EXISTE UN DEPARTAMENTO
    public static boolean existeDepartamento(String nombre) {
        String sql = "SELECT COUNT(*) FROM Departamento WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar departamento: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    // CONTAR TOTAL DE DEPARTAMENTOS
    public static int contarDepartamentos() {
        String sql = "SELECT COUNT(*) FROM Departamento";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar departamentos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // OBTENER NOMBRES DE DEPARTAMENTOS (útil para combos)
    public static String[] obtenerNombresDepartamentos() {
        List<Departamento> departamentos = obtenerTodosDepartamentos();
        String[] nombres = new String[departamentos.size()];
        
        for (int i = 0; i < departamentos.size(); i++) {
            nombres[i] = departamentos.get(i).getNombre();
        }
        
        return nombres;
    }
}