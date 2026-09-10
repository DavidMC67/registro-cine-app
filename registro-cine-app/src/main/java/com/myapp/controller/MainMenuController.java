package com.myapp.controller;

import com.myapp.main.App;
import com.myapp.model.Rol;
import com.myapp.model.Usuario;
import com.myapp.repository.AuthRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Label lblUsuario;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableView<Rol> tablaRoles;

    private final AuthRepository authRepository = new AuthRepository();

    @FXML
    private void initialize() {
        if (lblUsuario != null) {
            lblUsuario.setText("Bienvenido, admin");
        }

        configurarTablaUsuarios();
        configurarTablaRoles();
        cargarDatos();
    }

    private void configurarTablaUsuarios() {
        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre completo");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));

        TableColumn<Usuario, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("nombreRol"));

        tablaUsuarios.getColumns().setAll(colNombre, colUsuario, colEmail, colRol);
    }

    private void configurarTablaRoles() {
        TableColumn<Rol, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rol, String> colNombreRol = new TableColumn<>("Nombre del Rol");
        colNombreRol.setCellValueFactory(new PropertyValueFactory<>("nombreRol"));

        tablaRoles.getColumns().setAll(colId, colNombreRol);
    }

    private void cargarDatos() {
        tablaUsuarios.setItems(FXCollections.observableArrayList(authRepository.listarUsuarios()));
        tablaRoles.setItems(FXCollections.observableArrayList(authRepository.listarRoles()));
    }

    @FXML
    private void handleInicio() {
        cargarDatos(); // refresca las tablas con lo mas reciente de la BD
    }

    @FXML
    private void handlePerfil() {
        System.out.println("Sección: Perfil");
    }

    @FXML
    private void handleConfiguracion() {
        System.out.println("Sección: Configuración");
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            App.setScene("LoginView.fxml", "Iniciar Sesión");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
