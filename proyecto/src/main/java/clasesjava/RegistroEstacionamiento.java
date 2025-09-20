/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author Carlo
 */
public class RegistroEstacionamiento {
    private int idRegistro;
    private Vehiculo vehiculoAsociado;
    private Espacio espacioAsignado;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    
    public RegistroEstacionamiento(int idRegistro, Vehiculo vehiculoAsociado, 
                                  Espacio espacioAsignado, LocalDateTime horaEntrada) {
        this.idRegistro = idRegistro;
        this.vehiculoAsociado = vehiculoAsociado;
        this.espacioAsignado = espacioAsignado;
        this.horaEntrada = horaEntrada;
    }
    
    public void finalizar(LocalDateTime hora) {
        this.horaSalida = hora;
        System.out.println("Registro finalizado para vehículo: " + vehiculoAsociado.getPlaca());
    }
    
    public Duration calcularDuracion() {
        if (horaSalida != null) {
            return Duration.between(horaEntrada, horaSalida);
        } else {
            return Duration.between(horaEntrada, LocalDateTime.now());
        }
    }
    
    public int getIdRegistro() { 
        return idRegistro; 
    }
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    public Vehiculo getVehiculoAsociado() { 
        return vehiculoAsociado;
    }
    public void setVehiculoAsociado(Vehiculo vehiculoAsociado) {
        this.vehiculoAsociado = vehiculoAsociado;
    }
    public Espacio getEspacioAsignado() {
        return espacioAsignado;
    }
    public void setEspacioAsignado(Espacio espacioAsignado) { 
        this.espacioAsignado = espacioAsignado;
    }
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public LocalDateTime getHoraSalida() { 
        return horaSalida; 
    }
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }
    
}
