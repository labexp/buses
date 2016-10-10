
Tutorial prototipo de rastreo de rutas
=========================================

Propósito:
----------
En este semestre (2do del 2014) he estado trabajando en un prototipo de rastreo de rutas. El mismo rastrea la ruta por la que ha transitado la persona que posea el teléfono celular con la aplicación y la representa por medio de líneas en el mapa.


Herramientas y librerías
------------
- [Eclipse](https://www.eclipse.org/)
- [Osmdroid](https://github.com/osmdroid/osmdroid): osmdroid-android-4.1
- [OSMBonusPack](https://github.com/MKergall/osmbonuspack): osmbonuspack_v4.3
- [Genymotion](http://www.genymotion.com/)
- Android: Android 4.4.2 (API 19)

```
Nota: Para poder desarrollar el prototipo y toda aplicación en Android, es
necesario instalar el kit de desarrollo de Android (Android SDK)
```


¿Cómo funciona la interacción con el prototipo?
-----------------------------------------------

La pantalla del prototipo va a desplegar el mapa de OpenStreetMaps. Cada vez que el usuario se desplace, el mapa va a cambiar su punto central por la ubicación actual del usuario.

Para iniciar el proceso de tracking se debe presionar el botón "Start", de este modo cada vez que haya un desplazamiento se va a dibujar en el mapa el recorrido realizado por el usuario.

Para detener el proceso de tracking se debe presionar el botón, que ahora tiene el texto "Stop". Además esto hará que se elimine la ruta que se dibujó previamente en el mapa.


Implementación:
-------------------------------------

Inicialmente se debe crear el proyecto que queremos realizar y nombrarlo. Dado que el tutorial está siendo realizado en Eclipse, únicamente se debe dirigir a `File -> New -> Other`  y nos dirigimos a la carpeta `Android` y seleccionamos `Android Application Project`.

Ahora bien a nuestro paquete lo llamaremos `com.openstreetmaps.roadmanager` y a nuestro activity lo llamaremos `RoadActivity`.

Una vez que tenemos la estructura del proyecto podemos iniciar a trabajar en nuestro prototipo. Al crear el proyecto se nos crean una serie de carpetas en el directorio `res` en las cuales podemos almacenar elementos útiles para nuestras aplicaciones, como imágenes, layouts, etc. Para nuestro prototipo vamos a modificar el layout creado junto con el RoadActivity llamado `activity_main.xml`


```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:onClick="currentLocation"
    android:orientation="vertical" >

    <org.osmdroid.views.MapView
        android:id="@+id/openmapview"
        android:layout_width="fill_parent"
        android:layout_height="308dp"
        android:layout_weight="0.88"
        android:onClick="putMaker" />

    <Button
        android:id="@+id/button1"
        style="?android:attr/buttonStyleSmall"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="0.88"
        android:text="@string/start"
        android:onClick="setStartDrawing"/>
    
</LinearLayout>
```

De este layout nos vamos a enfocar únicamente en los dos elementos internos. El primero con etiqueta `org.osmdroid.views.MapView` hace referencia a la librería de Osmdroid. Su función es desplegar el mapa en la pantalla.
Por otro lado el segundo elemento con etiqueta `Button` se encarga de crear un botón en la pantalla, además se establece que se dirija al método `setStartDrawing` cada vez que sea presionado. Además el atributo `android:text` posee el valor `@string/start`, esto tiene una funcionalidad que veremos más adelante. De momento nos dirigimos a la carpeta `values` y en `strings.xml` agregamos el siguiente elemento

```
<string name="start">Start</string>
```


Una vez que se tiene la interfaz de usuario, podemos definir la lógica de nuestro prototipo. Primero que nada debemos cambiar la declaración de la clase RoadActivity, la cual debe quedar de la siguiente manera

```
public class RoadActivity extends Activity implements LocationListener
```

Esto debido a que RoadActivity debe implementar los métodos necesarios para la obtención de la posición actual del usuario.

Ahora vamos a crear una serie de variables de clase

```
private MapView mapView;
private MapController mapController;
private LocationManager locationManager;
private ItemizedOverlay<OverlayItem> myLocationOverlay;
private ResourceProxy resourceProxy;
    
private int longitude = 10019446;
private int latitude = -841970462;
    
private List<OverlayItem> items;
private List<GeoPoint> waypoints=new ArrayList<GeoPoint>();
    
Road roadToDraw;
```

`mapView` representa el mapa desplegado y `mapController` define su comportamiento. `locationManager` es el que maneja las actualizaciones en la localización actual. `myLocationOverlay` se encarga de dibujar los ítems en el mapa. `items` contiene los ítems que van a ser dibujados. `waypoints` contiene los puntos por los que ha pasado la persona, necesarios a la hora de trazar las rutas. `roadToDraw` se encarga de representar en forma de 'camino' los `waypoints`.

```
Nota: Aunque a partir de un item se pueden obtener la longitud y latitud de
donde esté presente el mismo, únicamente van a ser utilizados a la hora de
visualizarse en el mapa
```

En el método `onCreate` vamos a establecer la visualización y forma del mapa que se va a desplegar

```
resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
    
mapView = (MapView) this.findViewById(R.id.openmapview);
mapView.setTileSource(TileSourceFactory.MAPNIK);
mapView.setBuiltInZoomControls(true);
mapView.setMultiTouchControls(true);

mapController = (MapController) mapView.getController();
mapController.setZoom(15);
```

Luego creamos un par de variables locales

```
GeoPoint currentPoint = new GeoPoint(longitude, latitude);
GeoPoint itemPoint = new GeoPoint(longitude + 2000, latitude + 2000);
```

`currentPoint` tiene el punto real en el que se está posicionado, mientras que `itemPoint` tiene el punto donde se va a desplegar el item.

Ahora establecemos el centro de la pantalla con respecto a `currentPoint` y posicionamos el item en el mapa con respecto a `itemPoint`

```
mapController.setCenter(currentPoint);
        
items = new ArrayList<OverlayItem>();
items.add(new OverlayItem("Current position", itemPoint.toString(),itemPoint));

this.myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
        (OnItemGestureListener<OverlayItem>) new ItemActionHandler() , resourceProxy);
this.mapView.getOverlays().add(this.myLocationOverlay);

mapView.invalidate();
```

`mapView.invalidate()` actualiza la visualización del mapa con respecto a los elementos agregados por medio de `this.mapView.getOverlays().add(this.myLocationOverlay);`.
Por otra parte vemos que en la definición de `myLocationOverlay` estamos pasando un parámetro que corresponde a alguna implementación del interface `OnItemGestureListener`. Para ello debemos crear una clase anidada a nuestro `RoadActivity`. Antes de crear dicha clase debemos terminar la implementación del método `onCreate`. Para suerte nuestra únicamente falta definir quién y cómo se van a manejar las actualizaciones de la localización

```
locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 25,this);
```

Con esto se define que `locationManager` va a controlar las actualizaciones del GPS. Los parámetros del `requestLocationUpdates` esperan el siguiente valor en orden respectivo:

- Proveedor: nombre del proveedor de GPS registrado
- Tiempo mínimo: intervalo de tiempo en milisegundos entre cada actualización
- Distancia mínima: distancia en metros entre cada actualización
- Listener: LocationListener al cual se le va a invocar el método `onLocationChanged` cada vez que haya una actualización

Con esto concluimos el método onCreate. Como mencionamos antes, debemos implementar una clase anidada para RoadActivity

```
class ItemActionHandler implements OnItemGestureListener<OverlayItem> {
    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {
        Toast.makeText(RoadActivity.this, item.getSnippet(),
                Toast.LENGTH_LONG).show();
        return false;
    }
    
    @Override
    public boolean onItemSingleTapUp(int index, OverlayItem item) {
        Toast.makeText(RoadActivity.this, item.getTitle(),
                Toast.LENGTH_LONG).show();
        return true; // We 'handled' this event.
    }
}
```

`ItemActionHandler` únicamente se encarga de definir el comportamiento de un item al ser presionado.

Ahora debemos crear una nueva variable de clase

```
boolean startDrawing=false;
```

`startDrawing` define si se debe dibujar la ruta en el mapa o no. Para ello debemos implementar el método al cual está referenciado el botón que definimos en el layout `activity_main.xml`

```
public void setStartDrawing(View view){
    this.startDrawing=!startDrawing;
    TextView tx=(TextView) findViewById(R.id.button1);
    if(startDrawing){           
        tx.setText("Stop");
    }
    else{
        tx.setText("Start");
        waypoints.clear();
    }
}
```

`setStartDrawing` va a cambiar el valor de la variable `startDrawing` y además se encarga de cambiar el texto del botón.

Por último debemos definir el método más importante para el objetivo del prototipo. Vamos a iniciar declarando el método y explicando el código que contiene

```
public void onLocationChanged(Location location) {
    ...
```

Definimos el cambio de localización

```
...

    latitude = (int) (location.getLatitude() * 1E6);
    longitude = (int) (location.getLongitude() * 1E6);
    Toast.makeText(RoadActivity.this,
            "Location changed. Lat:" + latitude + " long:" + longitude,
            Toast.LENGTH_LONG).show();
    GeoPoint newPosition = new GeoPoint(latitude, longitude);
    mapController.setCenter(newPosition);
    items.clear();
    items.add(new OverlayItem("Current position", newPosition.toString(), newPosition));
    this.myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
            new ItemActionHandler() , resourceProxy);
    this.mapView.getOverlays().clear();
    this.mapView.getOverlays().add(this.myLocationOverlay);

...
```

Con esto cambiamos las variables `latitude` y `longitude` con los valores de la posición actual. El centro de la pantalla va a indicar la nueva posición por medio de `mapController.setCenter(newPosition)` y a su vez agregamos un nuevo item con la posición actual. En caso de querer visualizar todos los items referentes a los lugares por los cuales ha transitado la persona, se debe comentar o borrar `items.clear()`.

Por último debemos dibujar sobre el mapa, por tanto agregamos el siguiente código

```
...

    waypoints.add(newPosition);        
    if(startDrawing){                   
        for(GeoPoint g:waypoints){
            Log.v("GeoPoint", g.toString());
        }
        
        roadToDraw=new Road((ArrayList<GeoPoint>) waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(roadToDraw, this);
        mapView.getOverlays().add(roadOverlay);
    }

    mapView.invalidate();
}
```

`waypoints` va a agregar un punto referente a la nueva posición. En caso de se haya presionado el botón con texto `Start` se va a crear un nuevo 'camino' con los puntos contenidos en `waypoints`. Luego se va a utilizar la clase `PolyLine` presente en la librería de `OsmBonusPack` para dibujar en el mapa a partir del 'camino' establecido en `roadToDraw`. Se agrega el 'camino' dibujado a el mapa y se actualiza la visualización por medio de `mapView.invalidate()`


Problema del prototipo
================

El prototipo presenta una falta de precisión al ser utilizado en zonas donde la señal de gps no sea del todo buena. De este modo al ir sobre la carretera, la ruta podría ser dibujada un poco arriba o abajo de la ruta real. De igual manera si la señal del gps se pierde mientras se está trazando sobre el mapa, la próxima vez que haya señal estable se va a hacer un trazo desde el el último punto donde hubo señal hasta el momento en que se recuperó, provocando así un trazo realmente impreciso.

Solución alternativa
=================

La librería de `OsmBonusPack` posee un tipo de `RoadManager` llamado `OSRMRoadManager` el cual se conecta a un [servicio de código abierto](http://router.project-osrm.org/) el cual a partir de dos `GeoPoints`; uno inicial y otro final obtiene la lista de `waypoints` sobre la carretera de forma precisa. De este modo podemos asegurar que siempre se va a reflejar la ruta de forma real en el mapa.

Para este prototipo se decidió no utilizar el `OSRMRoadManager` puesto que no se quería depender de un servicio externo.

Código fuente
==========

Ver [Github](https://github.com/LabExperimental-SIUA/buses/tree/master/prototipo1)
