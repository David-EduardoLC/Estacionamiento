package clasesjava;

public class EspacioC {
    private int idEspacio;
    private String tipoLugar;
    private String estado;
    private int idZona;

    public EspacioC(int idEspacio, String tipoLugar, String estado, int idZona) {
        this.idEspacio = idEspacio;
        this.tipoLugar = tipoLugar;
        this.estado = estado;
        this.idZona = idZona;
    }

    public EspacioC(String tipoLugar, String estado, int idZona) {
        this.tipoLugar = tipoLugar;
        this.estado = estado;
        this.idZona = idZona;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public boolean isOcupado() {
        return "ocupado".equalsIgnoreCase(estado);
    }

    public void ocupar() {
        this.estado = "ocupado";
    }

    public void desocupar() {
        this.estado = "libre"; 
    }

    @Override
    public String toString() {
        return "EspacioC{" +
               "idEspacio=" + idEspacio +
               ", tipoLugar='" + tipoLugar + '\'' +
               ", estado='" + estado + '\'' +
               ", idZona=" + idZona +
               '}';
    }
}
