package clasesjava;

public class Carrera {
    private int idCarrera;
    private String nombre;

    // Constructor
    public Carrera(int idCarrera, String nombre) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
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