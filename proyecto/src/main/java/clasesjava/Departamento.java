package clasesjava;

public class Departamento {
    private int idDepartamento;
    private String nombre;

    // Constructor
    public Departamento(int idDepartamento, String nombre) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // toString para que se muestre correctamente en los JComboBox
    @Override
    public String toString() {
        return nombre;
    }
}