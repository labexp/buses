# Configuración del servidor

# Requerimientos
-intellij Idea 15.0 build 143.381

-jre 1.7.0

-jvm: openjdk 64 bit server VM

-orientdb-community 2.1.5

-Gradle 2.6

## Ejecución
Crear la base de datos en OrientDB. Se recomienda usar la versión gráfica en
http://localhost:2480.

La base de datos se puede llamar como se desee, en este caso se llama proyecto

El password y usuario se eligen al crear la base

Es autowired, por lo cual no se deben crear tablas dentro de la base nueva, el programa las creara automaticamente

Definir la configuración en variables de ambiente en intellij esto es a base de la información de la base de datos:
![idea](https://cloud.githubusercontent.com/assets/1479846/12601375/30f11264-c466-11e5-8506-f077dee26c94.jpg)

![idea2](https://cloud.githubusercontent.com/assets/1479846/12601417/7dac2792-c466-11e5-821f-2aff30228292.jpg)


![idea3](https://cloud.githubusercontent.com/assets/1479846/12601442/ada44222-c466-11e5-9d69-b1a9d06704ed.jpg)

-Correr el proyecto de intellij con el botón de run.

-En caso de que de errores por el autowiring, ir a consola y correr el gradle manualmente. Esto se logra llendo al folder root del proyecto y escribiendo

$> gradle clean
$> gradle build


## Insertar en batch

Con `postman` o `curl` enviar un `POST` a `http://localhost:8080/roadmap/batch` con el siguiente `JSON` en el body:

```json
{
    "stops": [
        {
            "name": "12",
            "latitude": 121.34,
            "longitude": 2341.23,
            "price": 200,
            "routes":["ruta1", "ruta2"]
            
        },
        {
            "name": "13",
            "latitude": 124.34,
            "longitude": 2355.23,
            "price": 200,
            "routes":["ruta1", "ruta3"]
        }
    ],

    "routes": [
        {
            "from": "12",
            "to": "13",
            "distance": 100,
            "coordinate": [234.45,564.34]
        }
      
    ]
}


Si todo es correcto, Postman deberia retornar un estado 200 OK <br />
 y en la direccion http://localhost:2480 deberia verse las <br />
 tablas "routes" y "stops" creada con la informacion ingresada <br />
```