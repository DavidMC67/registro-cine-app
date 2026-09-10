package com.myapp.model;

/**
 * POJO que representa un usuario del sistema, relacionado con un Rol.
 */
public class Usuario {

    private String nombreCompleto;
    private String username;
    private String password;
    private String email;
    private int rolId;       // FK hacia roles.id
    private String nombreRol; // solo para mostrar (viene de un JOIN, no se guarda)

    public Usuario() {
    }

    // Constructor usado al registrar (por defecto rol_id = 2 -> "Usuario")
    public Usuario(String nombreCompleto, String username, String password, String email) {
        this(nombreCompleto, username, password, email, 2);
    }

    public Usuario(String nombreCompleto, String username, String password, String email, int rolId) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rolId = rolId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + nombreRol + '\'' +
                '}';
    }
}
