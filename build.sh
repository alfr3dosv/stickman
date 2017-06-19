#!/bin/bash
# Compila todo en  /src
# Copia *.class y los ./assets en ./build
# Nota: llamar desde la raiz del juego
javac -classpath jna.jar:. src/*.java src/files/*.java src/level/*.java src/entity/*.java src/display/*.java src/enemie/*.java src/player/*.java
rm -r build/*
mkdir build/game
mkdir build/game/assets
mkdir build/game/files
mkdir build/game/level
mkdir build/game/entity
mkdir build/game/display
mkdir build/game/enemie
mkdir build/game/player
mv src/*.class build/game
mv src/files/*.class build/game/files
mv src/level/*.class build/game/level
mv src/entity/*.class build/game/entity
mv src/display/*.class build/game/display
mv src/enemie/*.class build/game/enemie
mv src/player/*.class build/game/player
cp -r ./assets/* ./build/game/assets
cp -r ./storyline.properties* ./build/game
