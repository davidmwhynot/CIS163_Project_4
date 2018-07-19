#!/bin/sh
cd src/Project3
javac -d ../../bin *.java
cd ../../bin
java Project3.RentalStoreGUI
