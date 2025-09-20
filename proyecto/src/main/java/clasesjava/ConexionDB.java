package clasesjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:postgresql://localhost:5432/estacionamiento_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "david";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Driver de PostgreSQL registrado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: No se pudo registrar el driver de PostgreSQL");
            System.err.println("   Asegúrate de tener la dependencia en pom.xml:");
            System.err.println("   <dependency>");
            System.err.println("       <groupId>org.postgresql</groupId>");
            System.err.println("       <artifactId>postgresql</artifactId>");
            System.err.println("       <version>42.6.0</version>");
            System.err.println("   </dependency>");
            e.printStackTrace();
        }
    }

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos:");
            System.err.println("   URL: " + URL);
            System.err.println("   Usuario: " + USER);
            System.err.println("   Error: " + e.getMessage());

            if (e.getMessage().contains("Connection refused")) {
                System.err.println("   → Verifica que PostgreSQL esté ejecutándose");
            } else if (e.getMessage().contains("database") && e.getMessage().contains("exist")) {
                System.err.println("   → La base de datos 'estacionamiento' no existe");
            } else if (e.getMessage().contains("password authentication failed")) {
                System.err.println("   → Usuario o contraseña incorrectos");
            }

            // En lugar de retornar null, lanzamos una excepción para evitar errores silenciosos
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }

    public static boolean probarConexion() {
        try (Connection conn = conectar()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException | RuntimeException e) {
            return false;
        }
    }
}
