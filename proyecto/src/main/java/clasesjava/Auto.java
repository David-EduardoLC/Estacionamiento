package clasesjava;

/**
 * Subclase Auto que hereda de Vehiculo.
 * Representa un automóvil con un atributo adicional: tipoAuto.
 * Ejemplos: Sedán, SUV, Hatchback, Deportivo, etc.
 */
public class Auto extends Vehiculo {
    private String tipoAuto;  // 🔹 Ahora consistente con el nombre de la clase

    // Constructor vacío
    public Auto() {
        super();
    }

    // Constructor completo con ID y objeto Persona (para lógica OO o UI)
    public Auto(int idVehiculo, String placa, String marca, String modelo, Persona propietario, String tipoAuto) {
        super(idVehiculo, placa, marca, modelo, propietario);
        this.tipoAuto = tipoAuto;
    }

    // Constructor completo con ID y solo idPersona (para consultas desde la BD)
    public Auto(int idVehiculo, String placa, String marca, String modelo, int idPersona, String tipoAuto) {
        super(idVehiculo, placa, marca, modelo, idPersona);
        this.tipoAuto = tipoAuto;
    }

    // Constructor sin ID (para inserciones nuevas desde UI)
    public Auto(String placa, String marca, String modelo, Persona propietario, String tipoAuto) {
        super(placa, marca, modelo, propietario);
        this.tipoAuto = tipoAuto;
    }

    // Getter y Setter
    public String getTipoAuto() {
        return tipoAuto;
    }

    public void setTipoAuto(String tipoAuto) {
        this.tipoAuto = tipoAuto;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "idVehiculo=" + getIdVehiculo() +
                ", placa='" + getPlaca() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", idPersona=" + getIdPersona() +
                ", propietario=" + (getPropietario() != null ? getPropietario().getNombreCompleto() : "null") +
                ", tipoAuto='" + tipoAuto + '\'' +
                '}';
    }
}
