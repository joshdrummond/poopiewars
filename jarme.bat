@echo off
del web\PoopieWars.jar
cd classes
jar cvf ..\web\PoopieWars.jar com\*.*
cd ..
