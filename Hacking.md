#Hacking
Durante el desarrollo se intento tener un estilo consistente:

NO USAR TABULACIONES, en cambio usar 4 espacios

Las constantes, las banderas y los elementos de una enumeración van en mayusculas

Algunas variables estan en mayusculas sin ser constantes indica que deben ser tratadas como constantes, modificalas esta bajo tu propia responsaviidad  

Para las funciones y clases la apertura de llaves siempre va en una nueva linea
<pre>   
   void foo()
     {
         hi;
     }
     class ToolBox
     {
   
     }
</pre>
Para las sentencias de control las llaves van en la misma linea que la condición a menos que  la condicion use mas de una linea
<pre>     
     if (one line) {
         hi;
     }
     if (first line &&
         second line)
	{
         hi;
     }
 </pre>
 Si solo hay una condicion y las siguiente linea no ocupa mas de una linea incluyendo comentarios se puede poner sin llaves
 <pre>     
     if (one line) {
         hi; //says hi
     }
 </pre>
