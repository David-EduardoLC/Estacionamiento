/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class Estacionamiento {
    private int idEstacionamiento;
    private String nombre;
    private String ubicacion;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private List<Zona> zonas;
    
    public Estacionamiento(int idEstacionamiento, String nombre, String ubicacion, 
                          LocalTime horaApertura, LocalTime horaCierre) {
        this.idEstacionamiento = idEstacionamiento;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.zonas = new ArrayList<>();
    }
    
    public void consultarDisponibilidad() {
        System.out.println("Consultando disponibilidad en " + nombre);
    }
    
    public Espacio asignarEspacio(Vehiculo vehiculo) {
        System.out.println("Asignando espacio para vehículo: " + vehiculo.getPlaca());
        return null; // Devolvería el espacio asignado
    }
    
    public void generarReporte() {
        System.out.println("Generando reporte para " + nombre);
    }
    
    public void registrarEntrada(Vehiculo vehiculo) {
        System.out.println("Registrando entrada de vehículo: " + vehiculo.getPlaca());
    }
    
    public void registrarSalida(Vehiculo vehiculo) {
        System.out.println("Registrando salida de vehículo: " + vehiculo.getPlaca());
    }
    
    public int getIdEstacionamiento() {
        return idEstacionamiento;
    }
    public void setIdEstacionamiento(int idEstacionamiento) { 
        this.idEstacionamiento = idEstacionamiento; 
    }
    public String getNombre() {
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public String getUbicacion() {
        return ubicacion; 
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion; 
    }
    public LocalTime getHoraApertura() { 
        return horaApertura; 
    }
    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura; 
    }
    public LocalTime getHoraCierre() {
        return horaCierre; 
    }
    public void setHoraCierre(LocalTime horaCierre) { 
        this.horaCierre = horaCierre; 
    }
    public List<Zona> getZonas() {
        return zonas;
    }
    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }
}