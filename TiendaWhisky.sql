CREATE DATABASE IF NOT EXISTS whisky CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE whisky;

CREATE TABLE IF NOT EXISTS usuarios (
    idUsuario 		INT 			AUTO_INCREMENT 	PRIMARY KEY,
    nombre 			VARCHAR(100) 	NOT NULL,
    password		VARCHAR(128) 	NOT NULL,
    rol 			VARCHAR(100) 	NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    idCategoria 	INT 			AUTO_INCREMENT  PRIMARY KEY,
    nombre 			VARCHAR(100) 	NOT NULL,
    descripcion 	TEXT
);

CREATE TABLE IF NOT EXISTS productos (
    idProducto		INT 			AUTO_INCREMENT 	PRIMARY KEY,
    nombre 			VARCHAR(100) 	NOT NULL,
    precio 			DECIMAL(10, 2) 	NOT NULL,
    descripcion 	TEXT,
    categoria_id 	INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(idCategoria) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    idPedido		INT 		AUTO_INCREMENT 	PRIMARY KEY,
    usuario_id 		INT,
    producto_id 	INT,
    nombreProducto varchar(100),
    fechaPedido 	VARCHAR(100),
    cantidad 		INT 		DEFAULT 1,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuario) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(idProducto) ON DELETE CASCADE
);

INSERT INTO categorias (idCategoria, nombre, descripcion) VALUES
(1, 'Single Malt', 'Whiskys elaborados a partir de malta en una única destilería.'),
(2, 'Blended', 'Mezclas de diferentes tipos de whiskys de diversas destilerías.'),
(3, 'Bourbon', 'Whiskys producidos principalmente en Estados Unidos con al menos un 51% de maíz.'),
(4, 'Rye', 'Whiskys elaborados con al menos un 51% de centeno en su composición.'),
(5, 'Irlandés', 'Whiskys producidos en Irlanda, suaves y triple destilados.'),
(6, 'Escocés', 'Whiskys tradicionales de Escocia con sabores únicos y ahumados.'),
(7, 'Japonés', 'Whiskys japoneses reconocidos por su refinamiento y complejidad.'),
(8, 'Ediciones Limitadas', 'Whiskys exclusivos lanzados en cantidades reducidas.'),
(9, 'Añejo', 'Whiskys envejecidos por largos períodos para sabores intensos.'),
(10, 'Whiskys Saborizados', 'Whiskys infusionados con sabores como miel, manzana o canela.');

INSERT INTO productos (idProducto, nombre, precio, descripcion, categoria_id) VALUES
(1, 'Macallan 18 años', 320.00, 'Single malt escocés envejecido en barricas de roble español.', 1),
(2, 'Johnnie Walker Blue Label', 200.00, 'Blended premium con sabores suaves y equilibrados.', 2),
(3, 'Maker\'s Mark Bourbon', 45.00, 'Bourbon artesanal con notas de vainilla y caramelo.', 3),
(4, 'WhistlePig Rye 10 años', 85.00, 'Rye whisky con sabores especiados y toques de roble.', 4),
(5, 'Jameson Irish Whiskey', 35.00, 'Whisky irlandés suave y triple destilado.', 5),
(6, 'Laphroaig 10 años', 60.00, 'Escocés ahumado con un distintivo sabor a turba.', 6),
(7, 'Hibiki Harmony', 120.00, 'Whisky japonés con notas florales y de miel.', 7),
(8, 'Macallan Edición Limitada No. 6', 450.00, 'Whisky exclusivo con sabores ricos y afrutados.', 8),
(9, 'Glenlivet 25 años', 400.00, 'Single malt envejecido con sabores intensos y suaves.', 9),
(10, 'Jack Daniel\'s Honey', 30.00, 'Whisky de Tennessee infusionado con miel natural.', 10),
(11, 'Redbreast 12 años', 70.00, 'Whisky irlandés pot still con notas de frutos secos.', 5),
(12, 'Yamazaki 12 años', 150.00, 'Whisky japonés con matices de frutas tropicales.', 7);

insert into usuarios (idUsuario, nombre, password, rol) values (1, 'Admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Admin');