--tipos de datos de prueba
insert into tipo_genero (genero) values ('hombre');
insert into tipo_genero (genero) values ('mujer');

insert into tipo_horario (horario) values ('diario');
insert into tipo_horario (horario) values ('fisica');

insert into tipo_prenda (prenda) values ('camisa');
insert into tipo_prenda (prenda) values ('pantalon');

insert into tipo_talla (talla) values ('10');
insert into tipo_talla (talla) values ('s');

insert into tipo_institucion (institucion) values ('robledo');
insert into tipo_institucion (institucion) values ('tecnologico');


--empleado de prueba
insert into empleado(nombre_empleado,cedula_empleado,telefono_empleado,email_empleado,password_empleado,tipo_empleado)
            values ('carlos','1005133918','3233392630','carlos@gmail.com','$2a$10$2GebscTdDZ/bh5dZoCJcLupO7qrDhsFb3KWaRD1YmOsyakG.r3CpW','JEFE');



--clientes de prueba
INSERT INTO cliente (cedula_cliente, nombre_cliente, telefono_cliente, email_cliente, direccion_cliente)
VALUES ('1234567890', 'Juan Pérez', '3001234567', 'juan.perez@example.com', 'Calle 123 #45-67, Medellín');

INSERT INTO cliente (cedula_cliente, nombre_cliente, telefono_cliente, direccion_cliente)
VALUES ('0987654321', 'María Gómez', '3007654321', 'Carrera 8 #12-34, Bogotá');

INSERT INTO cliente (cedula_cliente, nombre_cliente, telefono_cliente, email_cliente)
VALUES ('1122334455', 'Carlos López', '3001122334', 'carlos.lopez@example.com');
