# Título del Proyecto
ERP-CACIQUE

## Empresa
Cacique Sport 

**Descripción del Proyecto**
ERP-CACIQUE es un sistema integral diseñado para gestionar de manera eficiente el inventario y las facturas de Cacique Sport. El sistema permite la creación, procesamiento y almacenamiento de facturas, garantizando un control preciso de las operaciones diarias de la empresa. A través de APIs seguras, los usuarios pueden interactuar con el inventario, la información de los clientes y los productos, facilitando la administración completa de los procesos internos.

El ERP optimiza la gestión del inventario y los productos mediante la configuración de cinco tipos de datos esenciales. Además, expone APIs especializadas para gestionar los productos, clientes y facturas, asegurando una integración fluida y eficiente. Las funcionalidades permiten la creación, búsqueda y actualización tanto de productos como de facturas, proporcionando control total sobre las transacciones y el flujo de información de la empresa.

**Objetivos**
**Objetivo 1:** Implementar un conjunto de APIs para la gestión integral del inventario, productos y facturas.
**Objetivo 2:** Facilitar la administración de clientes mediante APIs que permitan su creación, búsqueda y actualización en el sistema de forma eficiente.
**Objetivo 3:** Desarrollar un sistema de autenticación seguro para controlar el acceso de los usuarios a las funcionalidades del ERP.

**Funcionalidades (APIs Expuestas)**
**API 1:** Gestión de productos, permitiendo la creación, actualización y eliminación de productos, basada en cinco tipos de datos de configuración.
**API 2:** Gestión de clientes, permitiendo la creación, búsqueda y actualización de la información de los clientes.
**API 3:** API de autenticación para la gestión del login y control de acceso de los usuarios al sistema.
**API 4:** Gestión de facturas, permitiendo la creación, búsqueda y actualización de facturas.
**API 5:** APIs para gestionar los datos de configuración necesarios para la creación y manejo de productos, asegurando que las operaciones sean consistentes con la estructura del negocio.


## Estructura
- main
  - codigo fuente
  - java
    - config:    clases de configuración de Spring
    - controller:   controladores y APIs del servidor
    - dto:    clases DTO (Data Transfer Object) para el intercambio de información entre back y front
    - exceptions:   clases de manejo de excepciones y errores en el código
    - model:   clases y/o objetos que representan los datos 
    - repository:   clases encargadas de la persistencia de los datos 
    - service:   clases que comunican las APIs con la lógica 
    - utils:  clases para procesos generales que son vitales pero no están relacionados con la lógica de negocio 
- test 
  - pruebas unitarias de los servicios



    
    
    
    
    
    

## Cómo Ejecutarlo
Para ejecutar este proyecto a nivel local debes 

1.tener tu base de datos corriendo a nivel local o remoto

2. clonar el servidor del back en spring https://github.com/JuanSebastianGarcia/ERP-Cacique-server.git

3. clonar el servidor del fron en angular https://github.com/JuanSebastianGarcia/ERP-Cacique-interface.git

4. configurar el properties para la conexion con tu base de datos
5. ejecutar el proyecto en spring 
6. ejecutar el proyecto en angular

asegurate de descargar las dependencias de cada uno

sprign boot - > ./gradlew build --refresh-dependencies

angular - > npm install
