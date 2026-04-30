INSERT INTO autor (aut_id, aut_nombre, aut_nacionalidad, aut_estado) VALUES
(1,'Mario Vargas Llosa','Peruana',1),
(2,'Cesar Vallejo Mendoza','Peruana',1),
(3,'José María Arguedas','Peruana',1);

INSERT INTO libro (lib_id, lib_titulo, lib_isbn, lib_fecha_publicacion, lib_estado, lib_aut_id) VALUES
(1,'La ciudad y los perros','9788420471839','1963-01-01',1,1),
(2,'Conversación en La Catedral','9788420471860','1969-01-01',1,1),
(3,'Los heraldos negros','9788498956993','1919-01-01',1,2);