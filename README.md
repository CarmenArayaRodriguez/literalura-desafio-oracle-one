# Desafio Literalura

## Descripción
Desafio Literalura es una aplicación construida para gestionar un catálogo de libros y autores. Permite a los usuarios buscar libros por título, listar libros por idioma, y consultar autores vivos en un año específico. Este proyecto se desarrolló como parte del programa ONE, enfocándose en demostrar habilidades en la construcción de aplicaciones con Spring Boot y JPA.

## Características
- Buscar libros por título.
- Mostrar todos los libros buscados previamente.
- Mostrar libros por idioma especificado.
- Listar todos los autores.
- Listar autores que estaban vivos en un año especificado.
- Exhibir estadísticas sobre la cantidad de libros en un determinado idioma.

## Tecnologías Utilizadas
- Java
- Spring Boot
- PostgreSQL
- Maven

## Requisitos
Para ejecutar este proyecto, necesitarás:
- JDK 11 o superior.
- Maven 3.6 o superior.
- PostgreSQL instalado y configurado.

## Configuración
Configura la base de datos PostgreSQL editando el archivo `application.properties` con tus credenciales locales:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```
## Instalación
Clona este repositorio en tu máquina local usando:

```
git clone https://github.com/CarmenArayaRodriguez/literalura-desafio-oracle-one.git
```

Navega al directorio del proyecto y compila el proyecto usando Maven:

```
cd desafio-literalura
mvn install
```

## Ejecución
Para ejecutar la aplicación, utiliza:

```
java -jar target/desafio-literalura-0.0.1-SNAPSHOT.jar
```

O puedes ejecutarlo directamente con Maven usando:

```
mvn spring-boot:run
```

## Uso
Una vez que la aplicación esté corriendo, puedes interactuar con ella a través de la consola. Sigue las instrucciones en pantalla para explorar las funcionalidades del catálogo.

