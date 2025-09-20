/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesjava;

/**
 *
 * @author Carlo
 */
public class Espacio {
    private String idEspacio;
    private String tipo;
    private boolean estaOcupado;
    
    public Espacio(String idEspacio, String tipo, boolean estaOcupado) {
        this.idEspacio = idEspacio;
        this.tipo = tipo;
        this.estaOcupado = estaOcupado;
    }
    
    public void ocupar() {
        this.estaOcupado = true;
        System.out.println("Espacio " + idEspacio + " ocupado");
    }
    
    public void desocupar() {
        this.estaOcupado = false;
        System.out.println("Espacio " + idEspacio + " desocupado");
    }
    
    public boolean ocupacionActual() {
        return estaOcupado;
    }
    
    // Getters y setters
    public String getIdEspacio() {
        return idEspacio; 
    }
    public void setIdEspacio(String idEspacio) { 
        this.idEspacio = idEspacio;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public boolean isOcupado() {
        return estaOcupado; 
    }
    public void setOcupado(boolean estaOcupado) {
        this.estaOcupado = estaOcupado;
    }
    
    
    
}
