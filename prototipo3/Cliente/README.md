# Configuración del cliente

# Requerimientos
-Android Studio 1.3.2

-jre 1.7.0

-jvm: openjdk 64 bit server VM

-Gradle 2.6

-Android API: 23 o posterior

-Emulador de android (Opcional: puede ser ejecutado directamente en un dispositivo móvil)

## Ejecución
Inicialmente se deben resolver las dependencias necesarias especificadas en el archivo *build.gradle*.
```
$> gradle clean
$> gradle build
```

Seguidamente se debe importar el proyecto. Para ello se debe ir a **_File -> New -> Import Project..._**. Una vez que se abre la ventana se procede a buscar el proyecto llamado **EnrutaTEC**.

Para ejecutar la aplicación se recomienda el uso de un emulador externo en vez de utilizar el emulador del ADB de android.
Se recomienda utilizar Genymotion, dada su facilidad para realizar pruebas en la aplicación dado que cuenta con un GPS y un mapa que ayuda a simular las rutas recorridas.

Utilizando Android Studio se debe construir; **_Build -> Make Project_** o utilizando el shortcut **_Ctrl + F9_**. Seguidamente se corre el código yendo a **_Run -> Run 'Enrutatec'_** o simplemente presionando **_Mayús + F10_**.

Se recomienda que antes de correr la aplicación se haga un **_Clean Project_**.

Una vez que se ejecuta la aplicación se selecciona el dispositivo o emulador que se vaya a utilizar.

##Consideraciones
En caso de querer cambiar el servidor al que va a enviar datos el cliente, se debe cambiar la variable **_URL_** presente en la clase **_MainActivity.java_** del paquete **_com.enrutatec.application_**.

```
...
private static String URL = "<URL>";
...
```
