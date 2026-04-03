# Backend TP - Gestion de Alquiler de Camiones

## Objetivo
Backend para gestionar alquiler de camiones para fletes.

Tecnologias definidas para el TP:
- Java
- Maven
- JPA (Hibernate)
- PostgreSQL
- EJB3 (siguiente etapa)
- JAX-RS (siguiente etapa)

## Estado actual del proyecto
La parte de Datos y Persistencia ya quedo iniciada con:
- Dependencias JPA, Hibernate, PostgreSQL y Java EE en pom.xml
- Entidades JPA:
	- Camion
	- Reserva
- Capa DAO:
	- GenericDAO
	- CamionDAO
	- ReservaDAO
- Utilidades de persistencia:
	- JPAUtil
	- DataInitializer
- Configuracion JPA en persistence.xml

## Estructura principal
src/main/java/tp/backend
- App.java
- entities/
- dao/
- util/
- service/
- service/impl/
- rest/
- rest/config/
- rest/dto/
- rest/mapper/

src/main/resources
- META-INF/persistence.xml

## Configuracion de base de datos
Archivo a editar:
- src/main/resources/META-INF/persistence.xml

No editar:
- target/classes/META-INF/persistence.xml
porque se regenera automaticamente en cada build.

Completar estos valores con tus credenciales locales:

```xml
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/backend_tp"/>
<property name="javax.persistence.jdbc.user" value="TU_USUARIO"/>
<property name="javax.persistence.jdbc.password" value="TU_PASSWORD"/>
```

Notas:
- hibernate.hbm2ddl.auto esta en create-drop para desarrollo inicial.
- Si quieres conservar datos entre ejecuciones, cambiar a update.

## Requisitos locales
1. Tener PostgreSQL instalado y levantado.
2. Crear una base llamada backend_tp.
3. Configurar usuario y password en persistence.xml.
4. Tener Java 8+ disponible.

## Compilacion Java
El proyecto compila con target Java 8 (definido en pom.xml), aunque tengas JDK 21 instalado.

## Ejecucion inicial
La clase App ejecuta DataInitializer.seedData() y carga datos de ejemplo si no existen camiones.

Datos de ejemplo creados:
- 3 camiones
- 3 reservas (2 confirmadas y 1 cancelada)

## Distribucion de tareas

### Persona 1 - Datos y Persistencia (hecho/iniciado)
- Modelo de entidades
- Relaciones JPA
- DAO y acceso a datos
- Configuracion de BD
- Datos de prueba

### Persona 2 - Logica de negocio (EJB)
Trabajar sobre carpetas:
- src/main/java/tp/backend/service
- src/main/java/tp/backend/service/impl

Responsabilidades:
- Crear servicios EJB (Stateless)
- Validaciones de negocio
- Reglas de disponibilidad de camiones
- Coordinacion de DAOs

### Persona 3 - API REST (JAX-RS)
Trabajar sobre carpetas:
- src/main/java/tp/backend/rest
- src/main/java/tp/backend/rest/config
- src/main/java/tp/backend/rest/dto
- src/main/java/tp/backend/rest/mapper

Responsabilidades:
- Endpoints REST
- DTOs de entrada/salida
- Mapeo Entity <-> DTO
- Configuracion de aplicacion JAX-RS

## Siguiente paso recomendado
1. Persona 2: crear interfaces y servicios EJB para Camion y Reserva.
2. Persona 3: exponer endpoints basados en esos servicios.
