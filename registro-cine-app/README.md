# Proyecto Integrador - Bimestre 1 (JavaFX MVC)

Ejemplo base de la aplicación Login ↔ Registro ↔ Dashboard, siguiendo
la estructura de paquetes pedida en la hoja de trabajo.

## Estructura

```
com.myapp.main         -> App.java (arranca la app y maneja el cambio de escenas)
com.myapp.model        -> Usuario.java (POJO)
com.myapp.controller   -> LoginController, RegistroController, MainMenuController
com.myapp.view         -> LoginView.fxml, RegistroView.fxml, MainMenuView.fxml, styles.css
```

## Cómo correrlo

### Opción A: NetBeans 21
1. Abrir NetBeans -> File -> Open Project -> seleccionar la carpeta `registro-cine-app`.
2. Asegurarse de tener el plugin/librerías de JavaFX 21 configuradas (o dejar que
   Maven las descargue, ya vienen en el `pom.xml`).
3. Ejecutar `App.java` (botón derecho -> Run File), o correr el proyecto completo.

### Opción B: Terminal (con Maven instalado)
```
mvn clean javafx:run
```

## Credenciales de prueba
- Usuario: `admin`
- Contraseña: `123`

## Cómo funciona la navegación

`App.java` guarda una referencia estática al `Stage` principal y expone
`App.setScene(fxml, titulo)`. Cada controlador llama a ese método para
cambiar la escena **sobre el mismo Stage**, así nunca se acumulan ventanas
y "Cerrar Sesión" simplemente vuelve a cargar `LoginView.fxml`.

## Qué falta / qué puedes ampliar (según lo que te pidan en clase)

- Conectar una base de datos real (ahora mismo el login es fijo y el
  registro solo imprime en consola).
- Cargar contenido dinámico dentro del `StackPane` (`contentArea`) del
  dashboard cuando se pulsan los botones del menú lateral.
- Agregar validaciones de campos vacíos / formato de email en el registro.

## Mapeo rápido a los criterios de evaluación

- **MVC**: separación real vista/controlador/modelo, inyección `@FXML` correcta.
- **Navegación**: un solo Stage, `App.setScene()` reutilizable, login->registro->login,
  login->dashboard->logout, todo sin acumular ventanas.
- **CSS**: `styles.css` externo, enlazado con `stylesheets="@styles.css"` en cada FXML.
- **Buenas prácticas**: paquetes `main/model/controller/view` tal cual los pide el enunciado.
