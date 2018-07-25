#!/bin/sh
cd src/project4
javac -d ../../bin *.java
cd ../../bin
java project4.RentalStoreGUI
