DROP DATABASE IF EXISTS registro_app_in4bv;
CREATE DATABASE registro_app_in4bv;
USE registro_app_in4bv;

-- =========================
-- TABLA ROLES
-- =========================
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

-- =========================
-- TABLA USUARIOS
-- =========================
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    rol_id INT NOT NULL,
    CONSTRAINT fk_usuario_rol
        FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- =========================
-- TABLA GENEROS
-- =========================
CREATE TABLE IF NOT EXISTS generos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_genero VARCHAR(50) NOT NULL UNIQUE
);

-- =========================
-- TABLA CLASIFICACIONES
-- =========================
CREATE TABLE IF NOT EXISTS clasificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(5) NOT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);

-- =========================
-- TABLA PELICULAS
-- =========================
CREATE TABLE IF NOT EXISTS peliculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    genero_id INT NOT NULL,
    duracion_minutos INT NOT NULL,
    clasificacion_id INT NOT NULL,
    director VARCHAR(100) NOT NULL,
    poster_path VARCHAR(255),

    CONSTRAINT fk_pelicula_genero
        FOREIGN KEY (genero_id) REFERENCES generos(id),

    CONSTRAINT fk_pelicula_clasificacion
        FOREIGN KEY (clasificacion_id) REFERENCES clasificaciones(id)
);

-- =========================
-- ROLES INICIALES
-- =========================
INSERT INTO roles (nombre_rol) VALUES
('Administrador'),
('Usuario');

-- =========================
-- USUARIO ADMIN
-- =========================
INSERT INTO usuarios (
    nombre_completo,
    username,
    password,
    email,
    rol_id
) VALUES (
    'Administrador CinePlex',
    'admin',
    '123',
    'admin@cineplex.com',
    1
);

-- =========================
-- GENEROS
-- =========================
INSERT INTO generos (nombre_genero) VALUES
('Accion'),
('Drama'),
('Comedia'),
('Terror'),
('Animacion');

-- =========================
-- CLASIFICACIONES
-- =========================
INSERT INTO clasificaciones (codigo, descripcion) VALUES
('A', 'Apta para todo publico'),
('B', 'Publico adolescente y adulto'),
('C', 'Solo adultos');