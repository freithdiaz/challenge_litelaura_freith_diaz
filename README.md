<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
</head>
<body>

<h1>Aplicación de Consulta y Registro de Libros</h1>

<h2>Descripción</h2>
<p>Esta aplicación Java con Spring Boot permite buscar y registrar libros utilizando la API de Gutendex. La información de los libros se almacena en una base de datos PostgreSQL.</p>

<h2>Funcionalidades</h2>
<ol>
    <li><strong>Buscar libro por título</strong>: Permite buscar un libro por su título en la API de Gutendex y registrarlo en la base de datos si no existe.</li>
    <li><strong>Listar libros registrados</strong>: Muestra una lista de todos los libros registrados en la base de datos.</li>
    <li><strong>Listar autores registrados</strong>: Muestra una lista de todos los autores de los libros registrados en la base de datos.</li>
    <li><strong>Listar autores vivos en un determinado año</strong>: Muestra una lista de autores que estaban vivos en un año específico.</li>
    <li><strong>Listar libros por idioma</strong>: Permite filtrar y mostrar los libros registrados según su idioma.</li>
</ol>

<h2>Requisitos</h2>
<ul>
    <li>Java 17</li>
    <li>Spring Boot</li>
    <li>PostgreSQL</li>
    <li>Jackson (para mapeo de JSON)</li>
</ul>

<h2>Instalación</h2>
<ol>
    <li>
        <strong>Clonar el repositorio</strong>
        <pre><code>git clone https://github.com/[freithdiaz]/challenge_litelaura_freith_diaz)</code></pre>
    </li>
    <li>
        <strong>Configurar la base de datos</strong>
        <p>Asegúrate de tener PostgreSQL instalado y en funcionamiento.</p>
        <p>Crea una base de datos nueva para la aplicación.</p>
        <p>Configura las credenciales de acceso en el archivo <code>application.properties</code>:</p>
        <pre><code>spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_de_tu_bd
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña</code></pre>
    </li>
    <li>
        <strong>Ejecutar la aplicación</strong>
        <pre><code>./mvnw spring-boot:run</code></pre>
    </li>
</ol>

<h2>Uso</h2>
<ol>
    <li>
        <strong>Buscar libro por título</strong>
        <p>Cuando se solicite, ingresa el título del libro que deseas buscar.</p>
        <p>Si el libro ya está registrado en la base de datos, se te notificará.</p>
        <p>Si el libro no está registrado, se buscará en la API de Gutendex y se registrará en la base de datos si se encuentra.</p>
    </li>
    <li>
        <strong>Listar libros registrados</strong>
        <p>Selecciona la opción correspondiente en el menú.</p>
        <p>Se mostrará una lista de todos los libros registrados en la base de datos.</p>
    </li>
    <li>
        <strong>Listar autores registrados</strong>
        <p>Selecciona la opción correspondiente en el menú.</p>
        <p>Se mostrará una lista de todos los autores de los libros registrados en la base de datos.</p>
    </li>
    <li>
        <strong>Listar autores vivos en un determinado año</strong>
        <p>Selecciona la opción correspondiente en el menú.</p>
        <p>Ingresa el año deseado.</p>
        <p>Se mostrará una lista de autores que estaban vivos en el año especificado.</p>
    </li>
    <li>
        <strong>Listar libros por idioma</strong>
        <p>Selecciona la opción correspondiente en el menú.</p>
        <p>Ingresa el idioma deseado.</p>
        <p>Se mostrará una lista de libros registrados en el idioma especificado.</p>
    </li>
</ol>





</body>
</html>
