-- DROP DATABASE IF EXISTS "Taller";

CREATE DATABASE "Taller"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

--drop sequence sec_identificador;
create sequence sec_identificador
	start with 3  -- siempre tiene que comensar al menos x 1
	increment by 20
	minvalue 3
	maxvalue 999999
	cycle;

alter sequence sec_identificador
no cycle;

select nextval ('sec_identificador');

-----------------------Type direccion-----------------------------

--Creamos un objeto.........create type "objeto"
-- Si no pones al char varying un valor max se pone el valor max del tipo de dato
-- Si al char no le ponemos el tamaño el max es 1 asi q ¡ojoo!
-- En un objeto NO SE PUEDE PONER PRIMARY KEY
-- Los objetos se ven en Schemas/Types
-- El varchar se lo traga pero no existe en pgadmin

create type direccion_type as(
	num integer,
	calle char varying(50),
	ciudad char varying(50),
	cp char varying(5)
);

--------------------------Type Persona--------------------------

create type persona_type as(
	nombre char varying(50),
	direccion direccion_type
);

-----------------------Tabla clientes----------------------------

--Si ponemos serial no hay que decir que es un dato integer lo toma como tal x defecto
--Se puede guardar el tlf como es multivaluado como un array añadiendo []
create table clientes(
	idCliente serial primary key,
	persona persona_type,
	numCoches Integer default 0,
	tlf char varying(14)[]
);

--------------------------Type empleados---------------------------

create type empleados_type as(
	persona persona_type,
	pass char varying(50),
	nomina char varying(20),
	horario date,
	NUSS bigint
);


------------------------Tabla administradores-------------------------

--Para usar la secuencia que hemos creado hay q usar el
--Default nextval('nombre secuencia') y determinar
--El tipo de la secuencia en este caso integer.

create table administradores(
	idAdmin integer primary key default nextval('sec_identificador'),
	edad integer,
	fechaNac date,
	tlfCorporativo char varying(12),
	empleados empleados_type
);

------------------------Tabla mecanicos---------------------

create type especialidad as enum('chapista','soldador','pintor');

--si creas un trigger para incrementar el contador y n pones valor x defecto
--da error pq x defecto si n añades el default es null y n se puede incrementar un null.
create table mecanicos(
	idMecanico integer primary key default nextval('sec_identificador'),
	rol especialidad,
	contHoras integer default 0,
	seguro char varying(50),
	empleados empleados_type
);

-------------------------Tabla vehiculos---------------------------

create table vehiculos(
	matricula char varying(7) primary key,
	marca char varying(10),
	modelo char varying(10),
	fichaTecnica char varying(100),
	seguro char varying(50)
);

--------------------------Tabla clientes_vehiculos----------------------

create table clientes_vehiculos(
	matricula char varying(7),
	idCliente integer,
	primary key (matricula, idCliente),
	foreign key (matricula) references vehiculos(matricula) on delete cascade,
	foreign key (idCliente) references clientes(idCliente) on delete cascade
);

------------------------Tabla coches--------------------------

--inherits vehiculos se usa para heredar todo lo de la tabla de vehiculos
create table coches(
	puertas integer,
	software char varying(20)
)inherits (vehiculos);

-- \d+ coches para ver la descripcion de la creacion de la tabla
-- si lo comprobamos veremos que no se añaden las restricciones

alter table coches
	add primary key (matricula)
	include (matricula);

------------------------Tabla motos----------------------------

create type tiemposMotos as enum('2T','4T');

create table motos(
	tiempos tiemposMotos,
	maleta boolean
)inherits (vehiculos);

alter table motos
	add primary key(matricula)
	include(matricula);

--------------------crear tabla servicios-------------------

create table servicios(
	idServicios serial primary key,
	descripcion char varying(100),
	costo double precision,
	tiempo double precision
);

--------------------crear tabla reparaciones----------------

create table reparaciones(
	idServicio integer,
	idMecanico integer,
	matricula char varying(7),
	fecha date,
	estado char varying(50),
	primary key (idMecanico, idServicio, matricula, fecha),
	foreign key (idMecanico) references mecanicos(idMecanico),
	foreign key (idServicio) references servicios(idServicios),
	foreign key (matricula) references vehiculos(matricula)
);

-------------------------------------------------------------

--Para ver solo los vehiculos
--select * from only vehiculos;

--select * from clientes;
--select (persona).nombre from clientes;

--insert into clientes(persona, tlf) values (row('juan',row(3,'ana luisa b','guanarteme','35017')),'{"555555555","666666666"}');

--update clientes set persona.nombre='Pepa' where idCliente=1;
--update clientes set tlf=array_append(tlf,'222222222') where idCliente=1;

--ALTER TYPE public.direccion_type ALTER ATTRIBUTE calle SET DATA TYPE character varying(25);


----------------------Funciones-------------------------------

--El $$ es el delimiter que aquí n se pone
--create function todosLosCoches() returns void as $$

--$$ language;

----------------------fun borrar coche------------------------

create function borrarCoche(matriculaKey char varying) returns void as $$
	delete from coches where matricula=matriculaKey;
$$ language sql;

--select borrarCoche('matriculacoche');

-----------------fun incrementar precios---------------------

--Crear una función que incremente los precios de los servicios
--en una cantidad pasada como parámetro

create function precioIncrement(cantidad double precision) returns void as $$
	update servicios set costo = costo + cantidad;
$$ language sql;

--select precioIncrement(01.60);

-------------------------------------------------------------

--Crear una función que devuelva el precio con IGIC (dos decimales)

create function precioIGIC(idServ integer) returns double precision as $$
	declare precio double precision;
	begin
		select costo into precio from servicios where idServicio = idServ;
		return round((precio::numeric) * 1.07, 2);
	end
$$ language plpgsql;

--------------------------------------------------------------

-- Modificar la subida de precios con una cantidad para que no supere
-- un límite de precio (pasado como parámetro)

create function limitadorPrecios(idServ integer, incremento double precision) returns double precision as $$
	declare precio double precision ;
	begin
		select costo into precio from servicios where idServicio = idServ;
		if incremento+precio <=50 then
			update costo set costo=incremento+precio where idServicio =idServ;
		end if;
	end
$$ language plpgsql;

-----------------------fun Para casa-----------------------------

-- Reiniciar el num de horas de los mecánicos

--Select * from mecanicos;

create or replace function reiniciarHoras(idEmpleadoTaller integer, horasNuevas integer) returns void as $$
	begin
		update mecanicos set conthoras=horasNuevas where idmecanico = idEmpleadoTaller;
	end
$$ language plpgsql;

--select reiniciarHoras(23,2);

-- mostrar contenido de un enum
select enum_range(null::especialidad);

----------------------------------------------------------------------

--Listar reparaciones de un mecánico dado un id
/*
select * from only reparaciones;

create or replace function listarReparaciones(idTrabajador integer) returns text as $$
	declare resultado text;
	begin
		select array_to_string(array_agg(matricula),',') into resultado from reparaciones
		where idmecanico=idTrabajador;
		return resultado;
	end
$$ language plpgsql;
*/

create or replace function listarReparaciones(idTrabajador integer)
returns text as $$
declare
    resultado text := '';
    registro record;
begin
    for registro in
        select idServicio, array_to_string(array_agg(matricula), ',') as matriculas
        from reparaciones
        where idmecanico = idTrabajador
        group by idServicio
    loop
        resultado := resultado || 'ID Servicio: ' || registro.idServicio || ', Matriculas: ' || registro.matriculas || E'\n';
    end loop;
    return resultado;
end
$$ language plpgsql;


--select listarReparaciones(23);
--select reparaciones.matricula, servicios.descripcion, reparaciones.idmecanico
	--from reparaciones inner join servicios on reparaciones.idservicio= servicios.idservicio;
----------------------------------------------------------------------------------

/* Ejemplo de tigger creado para actualizar en una table padre antes de insertar en su hijo datos nuevos
CREATE OR REPLACE FUNCTION before_insert_empleado()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO persona(nombre, edad) VALUES (NEW.nombre, NEW.edad);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_empleado_trigger
BEFORE INSERT ON empleado
FOR EACH ROW
EXECUTE FUNCTION before_insert_empleado();
*/

CREATE OR REPLACE FUNCTION insertarVehiculos()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO vehiculos VALUES (NEW.matricula,NEW.marca,NEW.modelo,NEW.fichatecnica,NEW.seguro);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_bi_coches BEFORE INSERT ON coches FOR EACH ROW EXECUTE FUNCTION insertarVehiculos();

----------------------------------------------------------------------
-- Calcular las horas pendientes (por trabajar) a un mecánico en función del servicio de reparación

create or replace function horasPendientes(codServicio integer, codMecanico integer) returns integer as $$
	declare horasMecanico integer;
	declare horasServicio integer;
	begin
		select conthoras into horasMecanico from mecanicos where idmecanico = codMecanico;
		select tiempo into horasServicio from servicios where idservicios = codServicio;
		if horasMecanico + horasServicio > 20 then
		   		return 0;
			else
				return (horasMecanico + horasServicio);
		end if;
	end;
$$ language plpgsql;

--select * from mecanicos;

-------------------------------------------------------------

-- Calcular el costo total de la reparación





------------------------Trigger-------------------------------

-- IMPORTANTE: No se puede ejecutar el cuerpo un trigger directamente hay que
-- programar previamente el contenido con una funcion.

-- Actualizar los nombres de los clientes durante
-- las inserciones para que se guarden en mayúsculas

-- Hay que poner que retorna un trigger pq se usa en un trigger :/
create function funActualizarNombreClientes() returns trigger as $$
	begin
		new.persona.nombre := upper((new.persona).nombre);
		return new;
	end;
$$ language plpgsql;

create or replace trigger triggerActualizarNombresClientes before insert or update
on clientes for each row execute function funActualizarNombreClientes();

-------------------------------------------------------------------

-- Cuando se registre un coche de un cliente, el atributo num_vehiculos (derivado) debe incrementarse.

create or replace function actualizarCoches() returns trigger as $$
begin
	update clientes set numCoches=numCoches+1 where clientes.idCliente=NEW.idCliente;
	return new;
end;
$$ language plpgsql;

create or replace trigger trigger_bi_actualizarCoches before insert on clientes_vehiculos
for each row execute function actualizarCoches();

----------------------Inserts-----------------------------
Select * from clientes_vehiculos;
Select * from clientes;
insert into clientes(persona,numcoches, tlf) values (('Pepa', (27,'Bravo Murillo','Las Palmas de GC','35016')),1,'{"+34928457817"}');
insert into clientes(persona,numcoches, tlf) values (('Pepe', (27,'Bravo Murillo','Las Palmas de GC','35016')),1,'{"+34928457817"}');
insert into clientes(persona,numcoches, tlf) values (('María', (14,'Aconcagua','Las Palmas de GC','35016')),2,'{"+34828336220"}');
insert into clientes(persona,numcoches, tlf) values (('Ana', (15,'Caroni','Las Palmas de GC','35016')),1,'{"+34928418775","+34658866970"}');

Select * from motos;

Select * from coches;
insert into coches values ('1981GYK','Mazda', '2', 'rutaficha', 'Mafre', '5','GPS Integrado');
insert into coches values ('1888DFZ','Citroen', 'Berlingo', 'rutaficha', 'Mafre', '5','GPS Integrado');

Select * from vehiculos;

Select * from servicios;
insert into servicios (descripcion, costo, tiempo) values('Cambio de ruedas', 30.00, 02.00);
insert into servicios (descripcion, costo, tiempo) values('Cambio de aceite', 15.00, 01.00);

Select * from mecanicos;
Insert into mecanicos(rol,conthoras,seguro,empleados) values
('chapista',8,'A todo riesgo',
 row(('Manolo',row(17,'San Nicolás','Las Palmas',35021)),
	 '321','rutaNominaManolo','2024/05/02'::date,123456789101113));

Insert into mecanicos(rol,conthoras,seguro,empleados) values
('soldador',8,'A todo riesgo',
 row(('Luis',row(11,'Amazonas','Las Palmas',35014)),
	 '123','rutaNominaLuis','2024/05/02'::date,321456784101214));

select enum_range(null::especialidad);

Select * from reparaciones;
insert into reparaciones values(1,23,'1981GYK','2024\02\23','Noinit');
