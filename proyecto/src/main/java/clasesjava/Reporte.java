/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class Reporte {
    private int idReporte;
    private String descripcion;
    private LocalDateTime fechaReporte;
    private String estatus;
    
    public Reporte(int idReporte, String descripcion, LocalDateTime fechaReporte, String estatus) {
        this.idReporte = idReporte;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.estatus = estatus;
    }
    
    public List<Vehiculo> generarListaVehiculos() {
        System.out.println("Generando lista de vehículos");
        return new ArrayList<>();
    }
    
    public List<Vehiculo> calcularPermanenciaLarga() {
        System.out.println("Calculando vehículos con permanencia larga");
        return new ArrayList<>();
    }
    
    public int getIdReporte() { 
        return idReporte; 
    }
    public void setIdReporte(int idReporte) { 
        this.idReporte = idReporte;
    }
    public String getDescripcion() { 
        return descripcion;
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }
    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    public String getEstatus() {
        return estatus;
    }
    public void setEstatus(String estatus) {
        this.estatus = estatus; 
    }
    
}
