/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import java.time.LocalTime;

public class VehiculoHistorialDTO {
    public final int idVehiculo;
    public final String placa;
    public final LocalTime horaEnt;
    public final LocalTime horaSalida;
    public final Integer idEspacio;

    public VehiculoHistorialDTO(int idVehiculo, String placa, LocalTime horaEnt, LocalTime horaSalida, Integer idEspacio) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.horaEnt = horaEnt;
        this.horaSalida = horaSalida;
        this.idEspacio = idEspacio;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public String getPlaca() { return placa; }
    public LocalTime getHoraEnt() { return horaEnt; }
    public LocalTime getHoraSalida() { return horaSalida; }
    public Integer getIdEspacio() { return idEspacio; }
}
