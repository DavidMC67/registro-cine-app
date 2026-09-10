package com.myapp.repository;

import com.myapp.config.DataBaseConnection;
import com.myapp.model.Clasificacion;
import com.myapp.model.Genero;
import com.myapp.model.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeliculaRepository {

    public List<Pelicula> listarPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT p.id, p.titulo, p.genero_id, g.nombre_genero, "
                + "p.duracion_minutos, p.clasificacion_id, c.codigo, p.director, p.poster_path "
                + "FROM peliculas p "
                + "JOIN generos g ON p.genero_id = g.id "
                + "JOIN clasificaciones c ON p.clasificacion_id = c.id "
                + "ORDER BY p.id";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                peliculas.add(mapearPelicula(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return peliculas;
    }

    public boolean registrarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO peliculas (titulo, genero_id, duracion_minutos, clasificacion_id, director, poster_path) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParametros(stmt, pelicula);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarPelicula(Pelicula pelicula) {
        String sql = "UPDATE peliculas SET titulo = ?, genero_id = ?, duracion_minutos = ?, "
                + "clasificacion_id = ?, director = ?, poster_path = ? WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParametros(stmt, pelicula);
            stmt.setInt(7, pelicula.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPelicula(int id) {
        String sql = "DELETE FROM peliculas WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Genero> listarGeneros() {
        List<Genero> generos = new ArrayList<>();
        String sql = "SELECT id, nombre_genero FROM generos ORDER BY nombre_genero";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                generos.add(new Genero(rs.getInt("id"), rs.getString("nombre_genero")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generos;
    }

    public List<Clasificacion> listarClasificaciones() {
        List<Clasificacion> clasificaciones = new ArrayList<>();
        String sql = "SELECT id, codigo, descripcion FROM clasificaciones ORDER BY codigo";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clasificaciones.add(new Clasificacion(
                        rs.getInt("id"), rs.getString("codigo"), rs.getString("descripcion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clasificaciones;
    }

    private void setParametros(PreparedStatement stmt, Pelicula pelicula) throws SQLException {
        stmt.setString(1, pelicula.getTitulo());
        stmt.setInt(2, pelicula.getGeneroId());
        stmt.setInt(3, pelicula.getDuracionMinutos());
        stmt.setInt(4, pelicula.getClasificacionId());
        stmt.setString(5, pelicula.getDirector());
        stmt.setString(6, pelicula.getPosterPath());
    }

    private Pelicula mapearPelicula(ResultSet rs) throws SQLException {
        Pelicula p = new Pelicula(
                rs.getString("titulo"),
                rs.getInt("genero_id"),
                rs.getInt("duracion_minutos"),
                rs.getInt("clasificacion_id"),
                rs.getString("director"),
                rs.getString("poster_path")
        );
        p.setId(rs.getInt("id"));
        p.setNombreGenero(rs.getString("nombre_genero"));
        p.setCodigoClasificacion(rs.getString("codigo"));
        return p;
    }
}
