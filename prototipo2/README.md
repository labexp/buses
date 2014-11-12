
Tutorial prototipo de una línea de metro
=========================================

Propósito:
----------
Actualmente soy integrante del grupo experimental del Instituto Tecnológico de Costa Rica en la sede de Alajuela. Estamos trabajando en una aplicación de plataforma tecnológica para proveer información en vivo para usuarios del transporte público en Costa Rica.

Parte de mi trabajo es desarrollar la interfaz de usuario. Uno de los primeros trabajos es desarrollar los diferentes widgets que se van a utilizar.

Herramientas y librerías
-------------------------
- [AndroidStudio](https://developer.android.com/sdk/installing/studio.html)
- Android: Android:Android 4.1(API 16) (Jelly Bean)

``` 
Nota: Para poder desarrollar el prototipo y toda aplicación en Android, es
necesario instalar el kit de desarrollo de Android (Android SDK)
``` 

¿Cómo funciona la interacción con el prototipo?
-----------------------------------------------

Se observa una línea de metro con diferentes puntos en ella.

1. El usuario toca cualquier lugar en la pantalla, entonces el punto 1 cambia de color, ese cambio de color significa que ese tramo ya ha sido recorrido.

2. El usuario toca cualquier lugar en la pantalla, entonces el punto 2 y el segmento de línea entre el punto1 y el punto2 cambian de color y esto se repite sucesivamente hasta colorear todos los puntos restantes.

**Se pretende crear un widget como prototipo inicial como el de la siguiente imagen:**

- El cual consiste en una pequeña línea de tren, que va a permitir al usuario ver de manera gráfica las instrucciones que tiene que seguir para llegar a su destino, marcando conforme se va avanzando en los diferentes tramos.


<center>
![wiframe-linea-tren](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/Wireframe2-07.png)
</center>

Desarrollando el prototipo del Widget:
-------------------------------------
- Primero empezamos creando un nuevo proyecto al cual le llamaremos widget_linea_tren.
 	
- Una vez con el proyecto creado, en el directorio res dentro del main creamos una carpeta con el nombre drawable, en la cual se van a agregar la imágenes que vamos a utilizar.

<center>
![ejemplo1](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/ejemplo1.png)
</center>

**Estas serán las imágenes utilizadas para el prototipo**
<center>
![L1](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_06.png) ![L2](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_02.png) ![L3](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_03.png) ![L4](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_09.png)

![L5](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_08.png) ![L6](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_04.png) ![L7](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_05.png) ![L8](https://github.com/LabExperimental-SIUA/buses/blob/master/prototipo2/images/l_10.png)
</center>

- Nos dirigimos a modificar el archivo main.xml del directorio res > layout del proyecto, en el cual vamos a definir un elemento LinearLayout con algunos atributos de este mismo. 

**Antes hay que entender, qué es un LinearLayout y cuáles son algunos atributos importantes usados:**

- Este tipo de Layout es uno de los diseños más simples y más empleados. Simplemente establece los componentes visuales uno junto al otro, ya sea horizontal o verticalmente.

- Existen varios atributos para este tipo de Layout, los más importantes son explicados a continuación:
    1. Android:orientation: Permite orientar los elementos de dos formas: vertical u horizontal.
    2. Android:background: Permite insertar una imágen o color de fondo.
    3. Android:layout_wigth: Especifica el ancho máximo de la vista.
    4. Android:layout_height: Especifica al altura máxima de la vista.

Para últimos 2 atributos se puede especificar las dimensiones escibiendo algún valor, pero para que se adapte a cualquier dispositivo se puede hacer de forma más eficiente y general especificando las distintas constantes dentro de los atributos:

- "fill_parent": Para que el control hijo tome la dimensión de su layout contenedor.
- "wrap_content": Para que el control hijo tome la dimensión de su contenido(que la vista sea suficientemente grande para encerrar su relleno).

**Se muestra el ejemplo utilizado: **

``` 
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"

    tools:context=".MainActivity"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical"
    android:background="#778899 ">
                 ...
/> 
```
**Pasamos a la creación de las imágenes de forma estática, esto quiere decir que ya las tendremos definidas en el xml para su posterior uso.**

**Agunas definiciones: **
    -ImageView: este control nos permite mostrar imágenes en la aplicación.
**atributos relevantes: **
    -android:id : funciona para relacionar un id(nombre) con la imagen.
    -android:layout_width: Especifica el ancho máximo de la imágen.
    -android:layout_height: Especifica la altura máxima de la imágen.
    -android:layout_gravity: Especifica el posicionamiento.
    -android:background: Especifica en fondo de la imagen, de igual manera se puede hacer con android:src que permite indicar la imagen a mostrar.

 Este sería un ejemplo de un ImageView el cual tendrá asignado los siguientes atributos:

```
                ...
<ImageView
        android:id="@+id/inicio"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/inicio" />
                ...
```
- Para nuestro prototipo como ejemplo crearemos varios ImageViews, en general nuestro xml se vería de esta manera:

```

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"

    tools:context=".MainActivity"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical"
    android:background="#778899 ">

    <ImageView
        android:id="@+id/inicio"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/inicio" />
    <ImageView
        android:id="@+id/linea"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea1"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio1"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea2"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio2"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea3"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio3"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea4"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio4"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea5"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/medio5"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/medio" />
    <ImageView
        android:id="@+id/linea6"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/linea"/>
    <ImageView
        android:id="@+id/meta"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_gravity="center_horizontal"
        android:background="@drawable/meta"/>

</LinearLayout>
```
- Una vez teniendo listo el xml pasamos a nuestra actividad principal, la cual se encargará de ejecutar nuestro proyecto.

```
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;


public class MyActivity extends Activity{

    pusagen;

    int listaIds [] = {R.id.inicio,R.id.linea, R.id.medio,R.id.linea1 ,R.id.medio1,R.id.linea2 ,R.id.medio2,R.id.linea3 ,R.id.medio3,
            R.id.linea4 ,R.id.medio4, R.id.linea5 ,R.id.medio5,R.id.linea6 ,R.id.meta};
    int checkIds [] = {R.drawable.check_inicio,R.drawable.check_linea, R.drawable.check_medio, R.drawable.check_meta };

    static int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        imagen = (ImageView) findViewById(listaIds[contador]);

    }


    public boolean onTouchEvent(MotionEvent event){
        // contador == listaIds.length es para que no haga el evento cuando ya llegó al final
        if(event.getAction()==MotionEvent.ACTION_DOWN && contador < listaIds.length){

            if(contador == 0){
                imagen.setImageResource(R.drawable.check_inicio);
                imagen= (ImageView)findViewById(listaIds[contador+1]);
                contador += 1;

            }

            else if(contador != 0){

                //para el caso ultimo
                if(contador == listaIds.length-2){
                    imagen.setImageResource(checkIds[1]);
                    imagen = (ImageView) findViewById(listaIds[contador +1]);

                    imagen.setImageResource(checkIds[3]);
                    imagen = (ImageView) findViewById(listaIds[contador + 1]);
                    contador += 2;

                }

                else {
                    imagen.setImageResource(checkIds[1]);
                    imagen = (ImageView) findViewById(listaIds[contador + 1]);

                    imagen.setImageResource(checkIds[2]);
                    imagen = (ImageView) findViewById(listaIds[contador + 2]);

                    contador += 2;
                }
            }
        }
        return false;
    }

}
```
Al inicio de la clase se definen 2 listas, una con los ids de las imágenes principales y otra con las imágenes coloreadas. Con el motivo de luego utilizar los elementos en el método onTouchListener para llevar el orden el las imágenes a utilizar.

**Explicación del método onTouchListener**

- Como se observa, hemos agregado el método onTouchListener. Este nos va a permitir la interacción con el usuario, se llama cuando el usuario realiza un contacto en la pantalla del dispositivo.

- El parametro MotionEvent nos permite seleccionar varios tipos de eventos por ejemplo si se apretó en la pantalla, si se arrastró el dedo, si se soltó el dedo, en fin, hay diferentes tipos los cuales pueden servir para la aplicación que se quiera realizar, en este caso utilizamos ACTION_DOWN que funciona cuando el usuario toca la pantalla del dispositivo. Entonces las imágenes cambian.

Código fuente
==========

Ver [Github](https://github.com/LabExperimental-SIUA/buses/tree/master/prototipo2)

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />Trabajo de <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Adrian Araya, Juan Manuel Esquivel</span> licencia bajo <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.
