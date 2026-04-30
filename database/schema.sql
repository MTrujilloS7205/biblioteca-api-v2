CREATE TABLE autor (
  aut_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  aut_nombre VARCHAR(50) NOT NULL,
  aut_nacionalidad VARCHAR(50) NOT NULL,
  aut_estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE libro (
  lib_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  lib_titulo VARCHAR(150) NOT NULL,
  lib_isbn VARCHAR(30) NOT NULL,
  lib_fecha_publicacion DATE NOT NULL,
  lib_estado BOOLEAN DEFAULT TRUE,
  lib_aut_id BIGINT,
  CONSTRAINT fk_libro_autor
    FOREIGN KEY (lib_aut_id) REFERENCES autor(aut_id)
);