---
layout: post
title: "Display"
date: 2016-10-17 08:31:25 -0500
categories: java
---

La clase tiene la funcion de almacenar, editar e imprmir cada frame

{% highlight java %}	
public class Display
{
	final int SIZE_X = 70;
	final int SIZE_Y = 20;
	final int WAIT_PER_FRAME = 16; 
	int frames=0;
	char[][] frame;
	char[][] stage;
	long start_time;	
{% endhighlight %}
La funcion print comprueba que haya pasado un intervalo de tiempo, si es asi actualiza la pantalla.
El intervalo de tiempo evita imprimir miles de frames por segundo.
{% highlight java %}
	public void print()
	{
		/* Si el tiempo actual - tiempo inicial > intevalo
		 * imprime el siguiente frame
		 */
		if( (System.currentTimeMillis() - start_time) > WAIT_PER_FRAME)
		{		
			update();
			start_time = System.currentTimeMillis();		
		}
	}
{% endhighlight %}
La funcion actualizar rescribe la variable frame.

 * Carga el mapa en el frame 
 * Limpia la pantalla
 * Imprime el frame

En el futuro despues de cargar el mapa, escribira en la variable los demas objetos, pero por el momento quiero comprabar si funciona
{% highlight java %}
	private void update()
	{

		 */
		frame = stage;
		clean();
		System.out.println("Frame" + frames++);
		for(int y=0; y<SIZE_Y; y++)
		{
			for(int x=0; x<SIZE_X; x++)
				System.out.print(frame[y][x]);
			System.out.print("\n");
		}
	}
{% endhighlight %}
Mediante una escape en ANSI se puede limpiar la pantalla. Puede no funcionar en Windows
{% highlight java %}
	private void clean()
	{
		/* 
	     * Debuelve el cursor a la parte superior
	     */
	    final String ANSI_CLS = "\u001b[2J";
	    final String ANSI_HOME = "\u001b[H";
	    System.out.print(ANSI_CLS + ANSI_HOME);
	    System.out.flush();
	}
{% endhighlight %}
