/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

import clasesjava.Persona;
import clasesjava.Persona;

/**
 *
 * @author Carlo
 */
public class Alumno extends Persona {
    private String numeroControl;
    private String carrera;
    private int semestre;
    
    public Alumno(int idPersona, String nombre, String apellido, String numeroControl, String carrera, int semestre) {
        super(idPersona, nombre, apellido);
        this.numeroControl = numeroControl;
        this.carrera = carrera;
        this.semestre = semestre;
    }
    
    // Getters y setters
    public String getNumeroControl() {
        return numeroControl; 
    }
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) { 
        this.carrera = carrera;
    }
    public int getSemestre() { 
        return semestre; 
    }
    public void setSemestre(int semestre) { 
        this.semestre = semestre; 
    }
}
