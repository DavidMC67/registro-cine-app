package com.myapp.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Clase principal de la aplicación.
 * Se encarga de arrancar el Stage principal y expone un método
 * utilitario (setScene) para que los controladores puedan navegar
 * entre vistas sin abrir ventanas nuevas.
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.getIcons().add(new Image(
                Objects.requireNonNull(App.class.getResourceAsStream("/com/myapp/view/images/app-icon.png"))
        ));
        setScene("LoginView.fxml", "Iniciar Sesión");
        primaryStage.show();
    }

    /**
     * Cambia la escena actual del Stage principal cargando el FXML indicado.
     * Se usa desde los controladores, ej:
     *   App.setScene("MainMenuView.fxml", "Menú Principal");
     */
    public static void setScene(String fxml, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(App.class.getResource("/com/myapp/view/" + fxml))
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);

        boolean esMenuPrincipal = fxml.equals("MainMenuView.fxml");
        primaryStage.setResizable(esMenuPrincipal);
        if (esMenuPrincipal) {
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
        } else {
            primaryStage.sizeToScene();
        }
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
