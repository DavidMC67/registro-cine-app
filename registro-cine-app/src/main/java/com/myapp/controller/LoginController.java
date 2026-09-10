package com.myapp.controller;

import com.myapp.main.App;
import com.myapp.model.Usuario;
import com.myapp.repository.AuthRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    private final AuthRepository authRepository = new AuthRepository();

    @FXML
    private void handleIngresar() {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("Completa usuario y contraseña.");
            return;
        }

        Usuario usuarioValidado = authRepository.validarCredenciales(usuario, password);

        if (usuarioValidado != null) {
            System.out.println("Ingreso: " + usuarioValidado.getUsername()
                    + " | Rol: " + usuarioValidado.getNombreRol());
            try {
                App.setScene("MainMenuView.fxml", "Menú Principal");
            } catch (IOException e) {
                mostrarError("No se pudo cargar el menú principal.");
            }
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void handleCrearCuenta() {
        try {
            App.setScene("RegistroView.fxml", "Crear Cuenta");
        } catch (IOException e) {
            mostrarError("No se pudo abrir el registro.");
        }
    }

    private void mostrarError(String mensaje) {
        if (lblError != null) {
            lblError.setText(mensaje);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
            alert.showAndWait();
        }
    }
}
