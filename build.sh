#!/bin/bash
# Compila todo en  /src
# Copia *.class y los ./assets en ./build
# Nota: llamar desde la raiz del juego
javac -classpath jna.jar:. src/*.java src/files/*.java
rm -r build/*
mkdir build/game
mkdir build/game/assets
mkdir build/game/files
mv src/*.class build/game
mv src/files/*.class build/game/files
cp -r ./assets/* ./build/game/assets
cp -r ./storyline.properties* ./build/game
