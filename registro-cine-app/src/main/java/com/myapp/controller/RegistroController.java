package com.myapp.controller;

import com.myapp.main.App;
import com.myapp.model.Usuario;
import com.myapp.repository.AuthRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistroController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    private final AuthRepository authRepository = new AuthRepository();

    @FXML
    private void handleGuardar() {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty() || email.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor llena todos los campos.");
            return;
        }

        if (authRepository.existeUsuario(usuario)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Usuario ya existe",
                    "Ese nombre de usuario ya está registrado. Elige otro.");
            return;
        }

        // Aquí se arma el objeto Model (Usuario) a partir de la Vista.
        Usuario nuevoUsuario = new Usuario(nombre, usuario, password, email);

        boolean guardado = authRepository.registrarUsuario(nuevoUsuario);

        if (guardado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso",
                    "Cuenta creada correctamente. Ahora puedes iniciar sesión.");
            volverAlLogin();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo guardar el usuario. Revisa la conexión a la base de datos.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelar() {
        volverAlLogin();
    }

    private void volverAlLogin() {
        try {
            App.setScene("LoginView.fxml", "Iniciar Sesión");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
