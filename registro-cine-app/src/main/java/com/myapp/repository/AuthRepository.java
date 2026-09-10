package com.myapp.repository;

import com.myapp.config.DataBaseConnection;
import com.myapp.model.Rol;
import com.myapp.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos: consultas SQL relacionadas a usuarios
 * y su rol (tablas usuarios y roles).
 */
public class AuthRepository {

    /**
     * Verifica credenciales y, si son válidas, devuelve el Usuario
     * con su rol ya cargado (JOIN con roles). Si no son válidas, null.
     */
    public Usuario validarCredenciales(String username, String password) {
        String sql = "SELECT u.nombre_completo, u.username, u.email, u.rol_id, r.nombre_rol "
                + "FROM usuarios u "
                + "JOIN roles r ON u.rol_id = r.id "
                + "WHERE u.username = ? AND u.password = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getString("nombre_completo"),
                            rs.getString("username"),
                            password,
                            rs.getString("email"),
                            rs.getInt("rol_id")
                    );
                    u.setNombreRol(rs.getString("nombre_rol"));
                    return u;
                }
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeUsuario(String username) {
        String sql = "SELECT id FROM usuarios WHERE username = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta un nuevo usuario. Por defecto queda con rol_id = 2 ("Usuario"),
     * a menos que el objeto Usuario ya traiga otro rolId asignado.
     */
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_completo, username, password, email, rol_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, usuario.getRolId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Trae todos los usuarios con el nombre de su rol (para mostrarlos en la tabla del Dashboard).
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.nombre_completo, u.username, u.email, u.rol_id, r.nombre_rol "
                + "FROM usuarios u "
                + "JOIN roles r ON u.rol_id = r.id "
                + "ORDER BY u.id";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nombre_completo"),
                        rs.getString("username"),
                        "", // no mostramos el password en la tabla
                        rs.getString("email"),
                        rs.getInt("rol_id")
                );
                u.setNombreRol(rs.getString("nombre_rol"));
                usuarios.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Trae todos los roles disponibles (para mostrarlos en la tabla del Dashboard).
     */
    public List<Rol> listarRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT id, nombre_rol FROM roles ORDER BY id";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id"), rs.getString("nombre_rol")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }
}
