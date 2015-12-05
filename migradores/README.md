# Migradores de datos abierto
## Componentes de software para consumir y exportar conjuntos de datos sobre rutas de transporte público en Costa Rica

Este repositorio contiene componentes de software que permiten mover datos desde y hacia los repositorios de datos del proyecto de información de transporte público del Laboratorio Experimental TEC-SIUA.

### Componentes

#### Consumidor de datos abiertos Portal CTP

El componente `buses-ctp` consume datos del portal de datos abiertos del Consejo de Transporte Público de Costa Rica (http://datos.ctp.go.cr/home).
El portal de datos abiertos del CTP contiene datos geoposicionales de los recorridos realizados por diversas rutas de autobús en Costa Rica. Este componente consume estos datos y los importa en la base de datos de este proyecto.

#### Productor de datos abiertos OSM

El componente `buses-osm` exporta las rutas de transporte público almacenadas en la base de datos de este proyecto y las importa en OpenStreetMap (https://www.openstreetmap.org/).

#### Visualizador de rutas

La aplicación móvil `buses-vis` permite visualizar los recorridos de rutas de autobús almacenados en la base de datos de este proyecto.

