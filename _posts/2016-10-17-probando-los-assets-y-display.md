---
layout: post
title: "Probando el display/assets"
date: 2016-10-17 10:20:25 -0500
categories: java
---

A la clase Display le agrege el metodo loadStage() para probar un assest que esta va a ser un stage.
 1 Lee el archivo texto
 2 Lo guarda en una char[][] que represante a un stage

Funciona!

Nota: loadStage() deberia ser algo como loadAsset() y estar en algun lugar mas apropiado como level o crear una clase propia para los assets 

##<center>Probando la clase display</center>##

{% highlight java%}
import java.util.Scanner;
public class runner
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.loadStage("game/assets/stages/story_1/before.txt");
		while (true)
			disp.print();
	}
}
{% endhighlight %}
<a onclick="dl(0);">Descargar</a>
