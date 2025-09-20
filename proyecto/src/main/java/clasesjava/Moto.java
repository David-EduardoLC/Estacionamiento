package clasesjava;

/**
 * Clase que representa la tabla 'moto' en la base de datos.
 * Hereda de Vehiculo y agrega el campo 'tipoMoto'.
 */
public class Moto extends Vehiculo {
    private String tipoMoto;

    // Constructor completo con ID y objeto Persona (para lógica OO o UI)
    public Moto(int idVehiculo, String placa, String marca, String modelo, Persona propietario, String tipoMoto) {
        super(idVehiculo, placa, marca, modelo, propietario);
        this.tipoMoto = tipoMoto;
    }

    // Constructor completo con ID y solo idPersona (para consultas desde la BD)
    public Moto(int idVehiculo, String placa, String marca, String modelo, int idPersona, String tipoMoto) {
        super(idVehiculo, placa, marca, modelo, idPersona);
        this.tipoMoto = tipoMoto;
    }

    // Constructor sin ID (para inserciones nuevas desde UI)
    public Moto(String placa, String marca, String modelo, Persona propietario, String tipoMoto) {
        super(placa, marca, modelo, propietario);
        this.tipoMoto = tipoMoto;
    }

    public String getTipoMoto() {
        return tipoMoto;
    }

    public void setTipoMoto(String tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    @Override
    public String toString() {
        return "Moto{" +
               "idVehiculo=" + getIdVehiculo() +
               ", placa='" + getPlaca() + '\'' +
               ", marca='" + getMarca() + '\'' +
               ", modelo='" + getModelo() + '\'' +
               ", idPersona=" + getIdPersona() +
               ", propietario=" + (getPropietario() != null ? getPropietario().getNombreCompleto() : "null") +
               ", tipoMoto='" + tipoMoto + '\'' +
               '}';
    }
}
