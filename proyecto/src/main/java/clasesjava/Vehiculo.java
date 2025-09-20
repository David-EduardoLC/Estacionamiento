package clasesjava;

public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;

    // Parte relacional (para base de datos)
    private int idPersona;

    // Parte orientada a objetos (para UI o lógica de negocio)
    private Persona propietario;

    // Constructor vacío
    public Vehiculo() {}

    // Constructor completo con ID y propietario
    public Vehiculo(int idVehiculo, String placa, String marca, String modelo, Persona propietario) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        setPropietario(propietario); // sincroniza idPersona
    }

    // Constructor sin ID (para inserciones)
    public Vehiculo(String placa, String marca, String modelo, Persona propietario) {
        this(0, placa, marca, modelo, propietario);
    }

    // Constructor con ID de persona (para DAO puro)
    public Vehiculo(int idVehiculo, String placa, String marca, String modelo, int idPersona) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.idPersona = idPersona;
        this.propietario = null; // explícito para evitar malentendidos
    }

    // Getters y Setters
    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
        if (this.propietario != null && this.propietario.getIdPersona() != idPersona) {
            this.propietario = null; // evita inconsistencia
        }
    }

    public Persona getPropietario() { return propietario; }
    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
        this.idPersona = (propietario != null) ? propietario.getIdPersona() : 0;
    }

    public String getTipoPropietario() {
        return (propietario != null) ? propietario.getClass().getSimpleName() : "Desconocido";
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", idPersona=" + idPersona +
                ", propietario=" + (propietario != null ? propietario.getNombreCompleto() : "null") +
                '}';
    }
}
