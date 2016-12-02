# gitman
Gitman es un juego escrito en Java y trata acerca de algunos comandos básicos de git.

##Big picture 
EL juego usa dos hilos: un hilo para capturar las entradas del usuario con la clase Player y otro hilo encargado de la pantalla con la clase Display.

El juego utiliza una librería lamada RawConsoleInput para capturar las teclas indivduales, esta librería se conecta con metdos propios de cada sistema operativo y requiera la Java Native Interface para funcionar.

Hay un documento llamado storyline.properties que contiene el orden en que se van a cargar las escenas y los niveles.

Para cade nivel hay un archivo que contiene un properties, de la forma nombre_nivel/nombre_nivel.properties
Para cade canjunto de escenas y dialogos hay un archivo que contiene un properties, de la forma nombre_escenas/nombre_escenas.properties

##Niveles
Dentro debe contener una directiva stage seguido de un numero(empezando en **1**) y dos puntos y la direccion del archivo empezando en 
**stage1:** stage1.txt 

El archivo debe tener por lo menos 20 lineas y cada linea 70 caracteres

###Enemigos
Para agregar enemigos se hace por medio de la directiva enemmie seguido de un numero(empezando en **1**) y dos puntos mas los valores mostrados en el siguiente ejemplo

**enemie1:**valor_Y, valor_X, direccion, velocida, pasos_o_espacios
Se empieza por 1 
La direccion puede ser: UP,DOWN,LEFT, RIGHT
los valores van separados por comas

##Escenas y dialogos
Dentro del directorio /historia1/historia1.properties debe contener una directiva scene un numero y dos puntos mas la direccion del archivo empezando en **1**
scene1: nombre_de_la_escena.txt

Para los dialogos se usa la directiva dialog un numero(empezando en **1**) y dos puntos mas el texto
**Dialog1:**Viajero->Hola viajero
**Dialog2:**Jugador->Hola

Para definir que dialogos se van a mostrar por escena se usa la directiva **dialogs_scene** esta servira para crear intervalos de dialogos
**dialogs_scene1:**4
**dialogs_scene2:**9

Lo anterior establece que la escena 1 contendra los dialogos del 1-4 y la escena 2 los dialogos del 4-9 
