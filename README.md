# Proyecto de Gestión de Inventario

Este proyecto es una aplicación de consola para gestionar el inventario de una tienda de componentes y periféricos de computación. Permite crear, eliminar, modificar y mostrar productos, así como gestionar el inventario.

![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)

# Requisitos

- Java 8 o superior
- Maven
- MySQL

# Configuración

1. Clona este repositorio en tu máquina local:

    ```bash
    git clone https://github.com/tu-usuario/tu-repositorio.git
    cd tu-repositorio
    ```

2. Configura la base de datos MySQL. Ejecuta el script `inventory_db.sql` para crear la base de datos y las tablas necesarias.

3. Carga los datos iniciales en la base de datos ejecutando el script `initial_data.sql`.

4. Modifica el archivo `db_credentials.json` con tus credenciales de acceso a la base de datos:

    ```json
    {
      "url": "jdbc:mysql://localhost:3306/inventory_db?useSSL=false&serverTimezone=UTC",
      "user": "root",
      "password": "password"
    }
    ```

# Dependencias

Asegúrate de que tu archivo `pom.xml` incluya las siguientes dependencias:

```xml
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.29</version>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.17.1</version>
    </dependency>
</dependencies>
```

# Estructura del Proyecto

    controller: Contiene las clases de validación y conexión a la base de datos.
    DAO: Contiene las clases DAO (Data Access Object) para cada tipo de producto.
    model: Contiene las clases de modelo para cada tipo de producto.
    view: Contiene las clases para la interacción con el usuario y la lógica del menú.
    resources: Contiene el archivo db_credentials.json para la configuración de la base de datos.
    org.example: Contiene la clase App que es el punto de entrada de la aplicación.

# Menú de Opciones

La aplicación presenta un menú principal con varias opciones para gestionar el inventario. A continuación se describe cada opción y sus submenús:

## Menú Principal
### Menú de opciones:
1. Crear Producto
2. Eliminar Producto
3. Modificar Producto
4. Mostrar Productos
5. Inventario

### Crear Producto
1. Crear Console
2. Crear CPU
3. Crear GPU
4. Crear Motherboard
5. Crear RAM
6. Crear Headphones
7. Crear Keyboard
8. Crear Mouse

### Eliminar Producto

Permite eliminar un producto del inventario mediante su SKU.

### Modificar Producto

Permite modificar los detalles de un producto existente en el inventario mediante su SKU.
### Mostrar Productos
1. Mostrar todos los productos
2. Mostrar consolas
3. Mostrar componentes de PC
4. Mostrar periféricos
5. Mostrar por SKU

### Inventario
1. Listar Inventario
2. Buscar Stock por SKU
3. Modificar Stock por SKU

## Ejecución

Para ejecutar la aplicación, usa el siguiente comando en la raíz del proyecto:
```
mvn clean install
java -jar target/tu-proyecto.jar
```

## Autor
_**Mario Reyna**_

Si necesitas algún cambio o ajuste, no dudes en decírmelo. :shipit:

