# Depósito

Este proyecto es una aplicación de gestión de inventarios para una tienda de componentes de computadoras y consolas de videojuegos. Permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre productos, así como gestionar el inventario de los mismos. La aplicación está desarrollada en Java y utiliza una base de datos MySQL para almacenar la información de los productos.
![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)

## Estructura del Proyecto

La estructura del proyecto es la siguiente:
```
Integrador1_ReynaMario/
├── .idea/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── controller/
│ │ │ │ ├── conexion/
│ │ │ │ │ ├── Conexion.java
│ │ │ │ │ ├── DBCredentials.java
│ │ │ │ │ └── Validations.java
│ │ │ │ ├── DAO/
│ │ │ │ │ ├── components/
│ │ │ │ │ │ ├── CPUDAO.java
│ │ │ │ │ │ ├── GPUDAO.java
│ │ │ │ │ │ ├── MotherboardDAO.java
│ │ │ │ │ │ └── RAMDAO.java
│ │ │ │ │ ├── peripherals/
│ │ │ │ │ │ ├── HeadphonesDAO.java
│ │ │ │ │ │ ├── KeyboardDAO.java
│ │ │ │ │ │ └── MouseDAO.java
│ │ │ │ │ ├── videoGame/
│ │ │ │ │ │ └── ConsoleDAO.java
│ │ │ │ │ ├── DAO.java
│ │ │ │ │ ├── InventoryDAO.java
│ │ │ │ │ └── ProductDAO.java
│ │ │ │ ├── model/
│ │ │ │ │ ├── components/
│ │ │ │ │ │ ├── CPU.java
│ │ │ │ │ │ ├── GPU.java
│ │ │ │ │ │ ├── InstallableComponent.java
│ │ │ │ │ │ ├── Motherboard.java
│ │ │ │ │ │ └── RAM.java
│ │ │ │ │ ├── peripherals/
│ │ │ │ │ │ ├── Headphones.java
│ │ │ │ │ │ ├── Keyboard.java
│ │ │ │ │ │ └── Mouse.java
│ │ │ │ │ ├── videoGame/
│ │ │ │ │ │ ├── Console.java
│ │ │ │ │ ├── Product.java
│ │ │ │ │ ├── Inventory.java
│ │ │ │ ├── org.example/
│ │ │ │ │ └── App.java
│ │ │ └── resources/
│ │ │ └── db_credentials.json
│ │ ├── view/
│ │ ├── CreateProduct.java
│ │ ├── DeleteProduct.java
│ │ ├── InventoryCRUD.java
│ │ ├── ListProduct.java
│ │ ├── Menu.java
│ │ └── UpdateProduct.java
```

## Dependencias

Para ejecutar este proyecto, necesitas incluir las siguientes dependencias en tu archivo `pom.xml`:

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
Configuración de la Base de Datos

Antes de ejecutar la aplicación, necesitas configurar las credenciales de acceso a la base de datos MySQL. Esto se hace en el archivo db_credentials.json ubicado en la carpeta resources. Asegúrate de que el archivo tenga el siguiente formato y actualiza los valores según tu configuración:
```
{
  "url": "jdbc:mysql://localhost:3306/inventory_db?useSSL=false&serverTimezone=UTC",
  "user": "root",
  "password": "password"
}
```
url: La URL de conexión a tu base de datos MySQL.
user: El nombre de usuario para conectarse a la base de datos.
password: La contraseña para conectarse a la base de datos.

Además, debes ejecutar el script inventory_db.sql para crear la base de datos y las tablas necesarias junto con el script de carga inicial carga_inicial.sql
