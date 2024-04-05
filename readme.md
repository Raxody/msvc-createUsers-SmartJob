# Prueba técnica - Juan Andrés López Ávila

Este proyecto es una prueba técnica que puede ser ejecutada en cualquier IDE de programación o ejecutando el archivo JAR. A continuación se detallan los pasos para ejecutar el proyecto utilizando IntelliJ IDEA como ejemplo.

## Pasos para ejecutar el proyecto:

1. **Clonar el repositorio:**
   - Clona el repositorio desde la siguiente URL: [https://github.com/Raxody/msvc-createUsers-SmartJob.git](https://github.com/Raxody/msvc-createUsers-SmartJob.git)

2. **Abrir el proyecto en IntelliJ IDEA:**
   - Abre IntelliJ IDEA.
   - Selecciona `File -> Open`.
   - Selecciona el proyecto clonado y espera a que se indexe.

3. **Compilar el proyecto:**
   - Cuando el proyecto haya sido indexado y esté listo para compilar, selecciona el icono de play verde en la parte superior del menú.
   - Espera a que el proyecto compile.

4. **Preparar el ambiente de pruebas:**
   - Abre Postman o Insomnia para realizar las pruebas.

5. **Realizar una solicitud POST:**
   - Utiliza la siguiente URL para realizar la solicitud: `http://localhost:9494/technicalTest/register`.
   - Selecciona el método POST.
   - En el cuerpo de la solicitud, selecciona el formato RAW y cambia de tipo texto a JSON.

6. **Agregar el cuerpo del JSON:**
   - Copia y pega el siguiente cuerpo de JSON en el editor:

```json
{
  "name": "Nombre de usuario",
  "email": "correos@example.com",
  "password": "contraseña123",
  "phones": [
    {
      "number": "123456789",
      "cityCode": "123",
      "countryCode": "1"
    },
    {
      "number": "987654321",
      "cityCode": "456",
      "countryCode": "2"
    }
  ]
}
```



7. **Respuestas esperadas:**
   - En la primera ejecución, se espera recibir un mensaje de respuesta similar al siguiente:

```json
{
    "name": "Nombre de usuario",
    "uuid": "7c62283f-bbbe-486f-bd64-77e7adf6c70a",
    "creationDate": "2024-03-25T19:15:48.644+00:00",
    "lastLogin": "2024-03-25T19:15:48.644+00:00",
    "modifiedDate": null,
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb3JyZW9zQGV4YW1wbGUuY29tIiwidXVpZCI6ImNvbnRyYXNlw7FhMTIzIiwiZW1haWwiOiJjb3JyZW9zQGV4YW1wbGUuY29tIiwiaWF0IjoxNzExMzk0MTQ4LCJleHAiOjE3MTEzOTc3NDh9.spVmygqlHUanKySSnwYVXYbFrrudc-YCRbb2pzCyoqg",
    "active": true
}
```

8. **Errores posibles:**
   - Si hay algún problema durante la ejecución o con el JSON de entrada, es posible que recibas mensajes de error similares a estos:

```json
{
    "mensaje": "El correo ya está registrado"
},

{
    "mensaje": "El campo nombre no puede contener valores distintos a letras"
},

{
    "mensaje": "El campo código de ciudad del teléfono 123456789 debe ser numérico"
}
```

9. **Correr Test:**
   - Pueden ejecutar los test 1 a 1 yendo a las clases de src/test
   - O se pueden ejecutar todos los test para revisar que si funcionen`Maven -> technicaltest -> Lifecycle -> test`

# Notas:

1. El parámetro que se encuentra en el archivo `application.properties` llamado:

   ```properties
   regular.expression.password=.+
   ```

   es con el cual se puede configurar el regex que se valida en la aplicación.

2. Para acceder a la base de datos en memoria, ingresa la siguiente URL en el navegador:

   [http://localhost:9494/h2-console/](http://localhost:9494/h2-console/)

   Usuario: root  
   Contraseña: root

3. En el archivo `application.properties` se encuentra la configuración general. Para el tema de JWT, actualmente se cuenta con 2 parámetros importantes:

   ```properties
   jwt.cron.renew.key=0 0 12 1 * *
   jwt.secret=F24D6254F608F613B661851DD194CF625341D7EB08FD71A2736BFC21F3EFD8F6
   ```

   El primero indica cada cuánto tiempo se debe generar un nuevo token (está parametrizado para ser mensualmente), por políticas de seguridad. Para ejecutar esta renovación, utiliza el siguiente endpoint de tipo GET: [http://localhost:9494/technicalTest/generateNewSecretKey](http://localhost:9494/technicalTest/generateNewSecretKey). Después, detén y vuelve a subir la aplicación para que los cambios surtan efecto.

4. La aplicación cuenta con todos los endpoints de Spring Actuator habilitados para monitoreo del mismo.