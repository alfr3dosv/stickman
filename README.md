# Stickman
Strickman es un juego escrito en Java de plataformas

## Storyline

Dentro de /src/main/storyline.properties esta el orden en que se van a cargar los niveles y las escenas.
Para cada evento que puede ser un nivel o escene hay un archivo que contiene un properties, de la forma nombre_nivel/nombre_nivel.properties

### Linea de eventos
La linea de contiene el orden en que se van cargando los niveles y las escenas
**ejemplo** storyline:story1,level1,level2,level3,level4,level5

### Eventos
Para definir un evento es necesario  
 * La palabra **level** o **story** en una nueva linea
 * Un numero, empezando en **1**. La numeracion es dependiente del tipo de evento
 * Dos puntos **:**
 * La carpeta del evento (relativa a storyline.properties)
level1:caminar

### Ejemplo
storyline:story1,level1,level2,level3,level4,level5<br>
level1:caminar<br>
level2:pared<br>
level3:saltos<br>
level4:enemigos<br>
level5:a_hard_one<br>
story1:tutorial<br>


## Niveles
### Stages
Los stages son las plataformas, para definir uno:
 * La palabra **stage** en una nueva linea
 * Un numero (empezando en **1**),
 * Dos puntos **:**
 * Direccion del archivo (relativa a la carpeta del juego)

**ejemplo** stage1:nombreDelStage.txt<br>

**nota: el archivo debe tener por lo menos 20 lineas y cada linea 70 caracteres**;

### Enemigos
Los enemigos son asteriscos que se mueven en cuatro direcciones arriba, abajo, izquierda, derecha
y siempre regresan a su posicion inicial. Tienen una posicion, una velocidad de desplazamiento y
cuanto se desplazan.

Para definir uno:
 * La palabra **enemie** en una nueva linea
 * Un numero, se empieza por **1**
 * Dos puntos **:**
 * La direccion puede ser: **UP,DOWN,LEFT, RIGHT**
 * La velocidad con la que se desplaza
 * El desplazamiento en caracteres
 * Los valores van separados por comas

### Ejemplo
stage1=enemigos.txt
enemie1:5,5,DOWN,3,10
enemie2:7,2,DOWN,20,15
enemie3:9,5,UP,3,15
enemie4:20,9,RIGHT,10,15

## Escenas y dialogos
### Escenas
Las escenas son las imagenes, sobre las cuales se encuetran los dialogos
 * La palabra **scene** en una nueva linea
 * Un numero, se empieza por **1**
 * Dos puntos **:**
 * Direccion del archivo (relativa a la carpeta del juego)

**ejemplo** scene1: scene_1.txt

### Dialogos
Para los dialogos se usa la directiva dialog un numero(empezando en **1**) y dos puntos mas el texto
 * La palabra **dialog** en una nueva linea
 * Un numero, se empieza por **1**
 * Dos puntos **:**
 * Texto del dialogo

**ejemplo** dialog1:Viajero->Hola viajero


### Sincronizar dialogos y escenas
Para definir que dialogos se van a mostrar por escena se usa la directiva<br> **dialogs_scene** esta servira para crear intervalos de dialogos
**dialogs_scene1:**4
**dialogs_scene2:**9

Lo anterior establece que la escena 1 contendra los dialogos del 1-4 y la escena 2 los dialogos del 4-9

### Ejemplo

**stickman/src/main/resources/assets/tutorial/tutorial.properties**
scene1: scene_1.txt<br>
scene2: scene_2.txt<br>
scene3: scene_3.txt<br>
scene4: scene_4.txt<br>
dialogs_scene1:3<br>
dialogs_scene2:8<br>
dialogs_scene3:9<br>
dialogs_scene4:10<br>
dialog1: GIT:Hi player, press any key to continue<br>
dialog2: GIT:looks like you have too much svn<br>
dialog3: GIT:I was going to tell a joke about svn, but i don't think anyone would git it.<br>
dialog4: GIT:let's start<br>
dialog5: GIT:Move using a,w,s,d<br>
dialog6: GIT:Open the console using ':'<br>
dialog7: GIT:Talk to me using the console<br>
dialog8: GIT:If you have a question open the console and type git help<br>
dialog9: GIT:If you hit one of this, you die<br>
dialog10: GIT:On every level you need to reach the key 'k'<br>
