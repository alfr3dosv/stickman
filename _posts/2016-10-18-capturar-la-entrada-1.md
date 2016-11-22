---
layout: post
title: "Capturar las entradas 1"
date: 2016-10-18 18:31:25 -0500
categories: java
---
Habia contado con los Key events para capturar la entrada del jugador.
{% highlight java %}
	public DIRECTION captureInput(java.awt.event.KeyEvent e) 
	{
    	int keyCode = e.getKeyCode();
	    DIRECTION dir_captured = DIRECTION.NONE;
	    switch( keyCode ) 
	    { 
	        case KeyEvent.VK_UP:
	            dir_captured = DIRECTION.UP;
	            break;
	        case KeyEvent.VK_DOWN:
	            dir_captured = DIRECTION.DOWN;
	            break;
	        case KeyEvent.VK_LEFT:
	            dir_captured = DIRECTION.LEFT;
	            break;
	        case KeyEvent.VK_RIGHT :
	            dir_captured = DIRECTION.RIGHT;
	            break;
	     }
	     return dir_captured;
	 }
{% endhighlight %}
<a onclick="dl(0);">Descargar</a>

Pero eso no va ser posible, debido a que los Key events solo funcionan con Swing y no en la consola. 

Y lo peor la libreria estandar de java solo permite capturar la entrada despues de la tecla enter, navegando por internet la unica soucion que encontre involucra Java Native Inteface: para conoctar con metodos propios de cada plataforma.

