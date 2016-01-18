# buses API engine-js

Implementación en `node.js` del API de backend para el servicio de datos abiertos sobre transporte público

## Requisitos

* `node.js` versión `v0.12.9` : https://nodesource.com/blog/chris-lea-joins-forces-with-nodesource/
* `orientdb` versión `2.1.7` o mayor

## Configuración del ambiente de desarrollo

* Definir las credenciales de `root` de orientdb en las siguientes variables de ambiente

```
BUSES_ODB_ROOT_USR
BUSES_ODB_ROOT_PWD
```

* Descargar las dependencias

```
npm install
```

* Crear la base de datos vacía y el usuario de la aplicación iniciando el engine por primera vez

```
bin/www
```

* Inicializar el esquema en la base de datos recién creada

```
node_modules/orientjs/bin/orientjs migrate up
```

## Recursos

### `POST http://localhost:3000/buses/v0.1/route`

```
POST http://localhost:3000/buses/v0.1/route
```

* Request

```json
{
    "id": "420",
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

* Response

```json
{
  "route": "421"
}
```

### `GET http://localhost:3000/buses/v0.1/route/id/{id}`

* Request

```
GET http://localhost:3000/buses/v0.1/route/id/420
```

* Response

```json
{
  "id": "420",
  "stops": [
    {
      "location": {
        "latitude": 9.894389,
        "longitude": -81.38932
      },
      "name": "Heredia",
      "routes": [
        "420",
        "400",
        "410"
      ],
      "createdOn": "2016-01-17T04:19:59.000Z",
      "lastUpdatedOn": "2016-01-17T04:19:59.000Z"
    },
    {
      "location": {
        "latitude": 9.43843,
        "longitude": -84.43784
      },
      "name": "San José",
      "routes": [
        "420",
        "415",
        "400"
      ],
      "createdOn": "2016-01-17T04:19:59.000Z",
      "lastUpdatedOn": "2016-01-17T04:19:59.000Z"
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

### `GET http://localhost:3000/buses/v0.1/route/ids`

* Request

```
GET http://localhost:3000/buses/v0.1/route/ids
```

* Response

```json
[
  "400",
  "410",
  "415",
  "420",
  "421"
]
```
