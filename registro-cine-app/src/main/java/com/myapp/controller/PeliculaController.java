package com.myapp.controller;

import com.myapp.model.Clasificacion;
import com.myapp.model.Genero;
import com.myapp.model.Pelicula;
import com.myapp.repository.PeliculaRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class PeliculaController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtPoster;
    @FXML private ImageView imgPoster;
    @FXML private ImageView imgPosterGrande;
    @FXML private ComboBox<Genero> cbGenero;
    @FXML private ComboBox<Clasificacion> cbClasificacion;
    @FXML private Label lblMensaje;
    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private Button btnExaminar;

    private final PeliculaRepository peliculaRepository = new PeliculaRepository();
    private Pelicula peliculaSeleccionada;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarCombos();
        cargarDatos();

        tablaPeliculas.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> {
                    cargarFormulario(actual);
                    imgPosterGrande.setImage(actual == null ? imagenLogoPorDefecto() : cargarImagen(actual.getPosterPath(), 150, 210));
                });
    }

    private void configurarTabla() {
        TableColumn<Pelicula, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Pelicula, String> colGenero = new TableColumn<>("Género");
        colGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        TableColumn<Pelicula, Integer> colDuracion = new TableColumn<>("Duración");
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));

        TableColumn<Pelicula, String> colClasificacion = new TableColumn<>("Clasificación");
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("codigoClasificacion"));

        TableColumn<Pelicula, String> colDirector = new TableColumn<>("Director");
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<Pelicula, String> colPoster = new TableColumn<>("Póster");
        colPoster.setCellValueFactory(new PropertyValueFactory<>("posterPath"));
        colPoster.setCellFactory(col -> new TableCell<>() {
            private final ImageView vista = new ImageView();

            @Override
            protected void updateItem(String path, boolean vacio) {
                super.updateItem(path, vacio);
                Image imagen = cargarImagen(path, 36, 50);
                vista.setImage(imagen);
                setGraphic(imagen == null ? null : vista);
            }
        });

        tablaPeliculas.setFixedCellSize(58);
        tablaPeliculas.getColumns().setAll(colTitulo, colGenero, colDuracion, colClasificacion, colDirector, colPoster);
    }

    private void cargarCombos() {
        cbGenero.setItems(FXCollections.observableArrayList(peliculaRepository.listarGeneros()));
        cbClasificacion.setItems(FXCollections.observableArrayList(peliculaRepository.listarClasificaciones()));
    }

    private void cargarDatos() {
        tablaPeliculas.setItems(FXCollections.observableArrayList(peliculaRepository.listarPeliculas()));
    }

    private void cargarFormulario(Pelicula pelicula) {
        peliculaSeleccionada = pelicula;
        if (pelicula == null) {
            return;
        }

        txtTitulo.setText(pelicula.getTitulo());
        txtDirector.setText(pelicula.getDirector());
        txtDuracion.setText(String.valueOf(pelicula.getDuracionMinutos()));
        txtPoster.setText(pelicula.getPosterPath());
        imgPoster.setImage(cargarImagen(pelicula.getPosterPath(), 42, 58));

        for (Genero g : cbGenero.getItems()) {
            if (g.getId() == pelicula.getGeneroId()) {
                cbGenero.setValue(g);
                break;
            }
        }
        for (Clasificacion c : cbClasificacion.getItems()) {
            if (c.getId() == pelicula.getClasificacionId()) {
                cbClasificacion.setValue(c);
                break;
            }
        }
    }

    @FXML
    private void handleExaminar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar póster");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        Window ventana = txtPoster.getScene().getWindow();
        var archivo = fileChooser.showOpenDialog(ventana);

        if (archivo != null) {
            txtPoster.setText(archivo.getAbsolutePath());
            imgPoster.setImage(cargarImagen(archivo.getAbsolutePath(), 42, 58));
        }
    }

    @FXML
    private void handleGuardar() {
        Pelicula pelicula = construirPeliculaDesdeFormulario();
        if (pelicula == null) {
            return;
        }

        boolean guardado = peliculaRepository.registrarPelicula(pelicula);
        if (guardado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Película registrada correctamente.");
            handleNuevo();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la película.");
        }
    }

    @FXML
    private void handleActualizar() {
        if (peliculaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona una película de la tabla.");
            return;
        }

        Pelicula pelicula = construirPeliculaDesdeFormulario();
        if (pelicula == null) {
            return;
        }
        pelicula.setId(peliculaSeleccionada.getId());

        boolean actualizado = peliculaRepository.actualizarPelicula(pelicula);
        if (actualizado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "Película actualizada correctamente.");
            handleNuevo();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la película.");
        }
    }

    @FXML
    private void handleEliminar() {
        if (peliculaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona una película de la tabla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar \"" + peliculaSeleccionada.getTitulo() + "\"?");
        confirmacion.setHeaderText(null);
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta.getButtonData().isDefaultButton()) {
                boolean eliminado = peliculaRepository.eliminarPelicula(peliculaSeleccionada.getId());
                if (eliminado) {
                    handleNuevo();
                    cargarDatos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la película.");
                }
            }
        });
    }

    @FXML
    private void handleNuevo() {
        peliculaSeleccionada = null;
        tablaPeliculas.getSelectionModel().clearSelection();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtPoster.clear();
        imgPoster.setImage(null);
        imgPosterGrande.setImage(imagenLogoPorDefecto());
        cbGenero.setValue(null);
        cbClasificacion.setValue(null);
        lblMensaje.setText("");
    }

    private Pelicula construirPeliculaDesdeFormulario() {
        String titulo = txtTitulo.getText().trim();
        String director = txtDirector.getText().trim();
        String duracionTexto = txtDuracion.getText().trim();
        Genero genero = cbGenero.getValue();
        Clasificacion clasificacion = cbClasificacion.getValue();

        if (titulo.isEmpty() || director.isEmpty() || genero == null || clasificacion == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Completa título, director, género y clasificación.");
            return null;
        }

        int duracion;
        try {
            duracion = Integer.parseInt(duracionTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "La duración debe ser un número entero.");
            return null;
        }

        if (duracion <= 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "La duración debe ser mayor a cero.");
            return null;
        }

        return new Pelicula(titulo, genero.getId(), duracion, clasificacion.getId(),
                director, txtPoster.getText());
    }

    private Image imagenLogoPorDefecto() {
        return new Image(getClass().getResourceAsStream("/com/myapp/view/images/splash-logo.png"));
    }

    private Image cargarImagen(String path, double ancho, double alto) {
        if (path == null || path.isBlank()) {
            return null;
        }
        File archivo = new File(path);
        if (!archivo.exists()) {
            return null;
        }
        return new Image(archivo.toURI().toString(), ancho, alto, true, true);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
