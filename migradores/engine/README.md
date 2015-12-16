# Buses

## Configuración

Crear la base de datos en OrientDB. 
Se supone que el motor de orientdb fue instalado en `/opt`. 

Para ejecutar el servicio:

```
/opt/orientdb-community-2.1.7/bin/server.sh
```

Para crear la base de datos:

```
/opt/orientdb-community-2.1.7/bin/console.sh
```

una vez en la consola:

```
orientdb> create database remote:localhost/buses root root plocal graph
```

Definir la configuración en variables de ambiente, ejemplo:

```bash
export AC_TEC_BUSES_ORIENT_URL="remote:localhost/buses"
export AC_TEC_BUSES_ORIENT_USR="root"
export AC_TEC_BUSES_ORIENT_PWD="root"
```

Nota: se recomienda el uso de direnv (https://github.com/direnv/direnv) para manejar variables de ambiente.

## Compilar y ejecutar

```bash
./gradlew clean build && java -jar build/libs/buses-engine-0.0.1.jar
```

## Insertar en batch

Con `postman` o `curl` enviar un `POST` a `http://localhost:8080/buses/v0.1/route` con el siguiente `JSON` en el body:

```json
{
    "id": "400",
    "stops": [
         {
             "name": "Heredia",
             "location": {
                 "latitude": 9.894389,
                 "longitude": -81.38932
             }
         },
         {
             "name": "San José",
             "location": {
                 "latitude": 9.43843,
                 "longitude": -84.43784
             }
         }
    ],
    "path": [
        {
            "latitude": 9.894389,
            "longitude": -81.38932
        },
        {
            "latitude": 9.32832,
            "longitude": -81.38232
        },
        {
            "latitude": 10.4384,
            "longitude": -84.438943
        },
        {
            "latitude": 9.43843,
            "longitude": -84.43784
        }
    ]
}
```
