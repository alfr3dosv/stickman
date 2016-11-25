#!/bin/bash
CD build
java -classpath jna.jar:build $1
cd..
