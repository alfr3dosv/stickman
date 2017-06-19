#!/bin/bash
# Compila todo en  /src
# Copia *.class y los ./assets en ./build
# Nota: llamar desde la raiz del juego
javac -classpath jna.jar:. src/*.java src/files/*.java src/level/*.java
rm -r build/*
mkdir build/game
mkdir build/game/assets
mkdir build/game/files
mkdir build/game/level
mv src/*.class build/game
mv src/files/*.class build/game/files
mv src/level/*.class build/game/level
cp -r ./assets/* ./build/game/assets
cp -r ./storyline.properties* ./build/game
