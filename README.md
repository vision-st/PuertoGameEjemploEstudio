# Proyecto PuertoGames -- Gestión de videojuegos con colecciones y archivos Excel

## 1. Descripción general del proyecto

Este proyecto implementa un pequeño sistema de consulta de videojuegos
llamado **PuertoGames**, desarrollado en Java utilizando:

-   Organización en paquetes (`ui`, `data`, `model`).
-   Colecciones (`List`, `ArrayList`).
-   Lectura de archivos Excel (`.xlsx`) con Apache POI.
-   Mecanismos de carga tanto desde rutas de sistema como desde recursos
    empacados en un `.jar`.

## 2. Objetivos de aprendizaje

El estudiante podrá: 1. Comprender la separación de responsabilidades.
2. Aplicar colecciones genéricas. 3. Implementar búsquedas sobre listas.
4. Utilizar Apache POI para leer Excel. 5. Entender la diferencia entre
carga por ruta y carga desde el `.jar`. 6. Comprender el rol del archivo
`pom.xml`.

## 3. Arquitectura del proyecto

-   **model**: contiene `Videojuego`, representando el modelo de
    dominio.
-   **data**: contiene `GestorVideojuegos`, encargado de la carga y
    gestión de datos.
-   **ui**: contiene `Main`, punto de entrada del sistema.

## 4. Modelo de dominio: Videojuego

Clase que encapsula título, género y precio, utilizando encapsulamiento
y sobrescritura de `toString()`.

## 5. Gestión de datos: GestorVideojuegos

Incluye: - Colección interna `List<Videojuego>`. - Listado y búsqueda
por género. - `cargarDesdeExcel`: lectura desde archivo del sistema. -
`cargarDesdeJarExcel`: lectura desde recursos dentro del `jar`.

## 6. Carga de archivos Excel

### 6.1 Desde ruta del sistema

Utiliza `FileInputStream`. Funciona en el IDE pero no en el jar por
tratar de acceder a una ruta de archivo que ya no existe físicamente
dentro del empaquetado.

### 6.2 Desde recursos del jar

Utiliza:

    getClass().getClassLoader().getResourceAsStream(ruta)

Este mecanismo permite leer archivos ubicados en `src/main/resources` y
empacados dentro del jar.

Incluye métodos auxiliares para lectura segura de celdas: -
`getStringCellValue` - `getNumericCellValue`

## 7. Apache POI

La biblioteca Apache POI permite abrir libros, hojas, filas y celdas del
archivo `.xlsx`.

El manejo incluye: - Validación de filas nulas. - Conversión segura de
datos. - Lectura de contenido mixto (texto y números).

## 8. Rol del pom.xml

El archivo `pom.xml` se encarga de: - Declarar dependencias (ej. Apache
POI). - Establecer la estructura estándar de un proyecto Maven. -
Definir cómo se empaqueta el proyecto. - Insertar recursos dentro del
jar final.

## 9. Ejecución del proyecto

### Desde el IDE

La carga por ruta funciona solo si el archivo existe en el directorio de
ejecución.

### Desde el jar

Se debe usar `cargarDesdeJarExcel`

Ejemplo:

    java -jar puertogames.jar

## 10. Conclusiones pedagógicas

Este proyecto permite enseñar: - Modelado de datos. - Colecciones y
filtrado. - Lectura de archivos Excel. - Problemas reales de empaquetado
y rutas. - Separación de capas y responsabilidad única.
