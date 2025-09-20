/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

/**
 *
 * @author Carlo
 */

public class Maestro extends Persona {
    private int claveEmpleado;
    private String departamento;

    public Maestro(int idPersona, String nombre, String apellido, int claveEmpleado, String departamento) {
        super(idPersona, nombre, apellido);
        this.claveEmpleado = claveEmpleado;
        this.departamento = departamento;
    }

    public int getClaveEmpleado() { return claveEmpleado; }
    public void setClaveEmpleado(int claveEmpleado) { this.claveEmpleado = claveEmpleado; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}
