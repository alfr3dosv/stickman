---
layout: post
title: "Estructurando el proyecto"
date: 2016-10-30 12:12:12 -0500
categories: java
---
El proyecto se hace cada vez mas grande, para organizarlo mejor lo he estructurado en 3 directorios: <strong>build, assets, src</strong>.

Ademas de esto hay 2 scripts auxiliares: <strong>build.sh y test.sh</strong> 

### /src ###
Contiene todos los .java.

### /build ###
Contiene todos los .class generados.

### /assets ###
Contiene los archivos que se van a desplagar en la pantalla.

### build.sh ###
Script para compilar los archivos en /src

### test.sh ###
Script para probar alguna clase.Ejemplo test.sh *clase*

**Nota:** para que los scripts funcionen tienen que ejecutarse desde la raiz. 
