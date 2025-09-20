package clasesjava;

public class UsuarioRol {
    private int idUsuarioRol;
    private Usuario usuario;
    private Rol rol;

    // Constructor
    public UsuarioRol(int idUsuarioRol, Usuario usuario, Rol rol) {
        this.idUsuarioRol = idUsuarioRol;
        this.usuario = usuario;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdUsuarioRol() {
        return idUsuarioRol;
    }

    public void setIdUsuarioRol(int idUsuarioRol) {
        this.idUsuarioRol = idUsuarioRol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
