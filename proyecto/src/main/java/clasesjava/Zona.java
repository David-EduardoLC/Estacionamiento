package clasesjava;

public class Zona {
    private int idZona;
    private String descripcion;
    private int idEstacionamiento;
    private String nombreEstacionamiento;
    private String ubicacionEstacionamiento;
    
    // Constructores
    public Zona() {}
    
    public Zona(int idZona, String descripcion, int idEstacionamiento, 
                String nombreEstacionamiento, String ubicacionEstacionamiento) {
        this.idZona = idZona;
        this.descripcion = descripcion;
        this.idEstacionamiento = idEstacionamiento;
        this.nombreEstacionamiento = nombreEstacionamiento;
        this.ubicacionEstacionamiento = ubicacionEstacionamiento;
    }
    
    // Getters y Setters
    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public int getIdEstacionamiento() { return idEstacionamiento; }
    public void setIdEstacionamiento(int idEstacionamiento) { this.idEstacionamiento = idEstacionamiento; }
    
    public String getNombreEstacionamiento() { return nombreEstacionamiento; }
    public void setNombreEstacionamiento(String nombreEstacionamiento) { this.nombreEstacionamiento = nombreEstacionamiento; }
    
    public String getUbicacionEstacionamiento() { return ubicacionEstacionamiento; }
    public void setUbicacionEstacionamiento(String ubicacionEstacionamiento) { this.ubicacionEstacionamiento = ubicacionEstacionamiento; }
    
    @Override
    public String toString() {
        return "Zona{" +
                "idZona=" + idZona +
                ", descripcion='" + descripcion + '\'' +
                ", idEstacionamiento=" + idEstacionamiento +
                ", nombreEstacionamiento='" + nombreEstacionamiento + '\'' +
                ", ubicacionEstacionamiento='" + ubicacionEstacionamiento + '\'' +
                '}';
    }
}