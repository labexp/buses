# Buses

## Configuración

Crear la base de datos en OrientDB. La manera más sencilla es utilizando la consola gráfica en http://localhost:2480.

Definir la configuración en variables de ambiente, ejemplo:

```bash
export AC_TEC_BUSES_ORIENT_URL="remote:localhost/buses"
export AC_TEC_BUSES_ORIENT_USR="buses"
export AC_TEC_BUSES_ORIENT_PWD="buses"
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
    id: '400',
    stops: [
         {
             name: 'Heredia',
             location: {
                 latitude: 9.894389,
                 longitude: -81.38932
             }
         },
         {
             name: 'San José',
             location: {
                 latitude: 9.43843,
                 longitude: -84.43784
             }
         }
    ],
    path: [
        {
            latitude: 9.894389,
            longitude: -81.38932
        },
        {
            latitude: 9.32832,
            longitude: -81.38232
        },
        ...
        {
            latitude: 10.4384,
            longitude: -84.438943
        },
        {
            latitude: 9.43843,
            longitude: -84.43784
        }
    ]
}
```
