#!/bin/bash
# Compila todo en  /src
# Copia *.class y los ./assets en ./build
# Nota: llamar desde la raiz del juego
javac -classpath jna.jar:. src/*.java src/files/*.java src/level/*.java src/entity/*.java src/display/*.java
rm -r build/*
mkdir build/game
mkdir build/game/assets
mkdir build/game/files
mkdir build/game/level
mkdir build/game/entity
mkdir build/game/display
mv src/*.class build/game
mv src/files/*.class build/game/files
mv src/level/*.class build/game/level
mv src/entity/*.class build/game/entity
mv src/display/*.class build/game/display
cp -r ./assets/* ./build/game/assets
cp -r ./storyline.properties* ./build/game
