# Ohjelmistotekniikka harjoitustyö
## Mahjong manager
Simple tracking tool for gameplay.
Currently running non graphical user interface.
Updates coming weekly until complete.


To run current version, replicate repository to your computer. In folder MahjongManager run 

>mvn compile exec:java -Dexec.mainClass=Mahjong_UI.MJ_TextUI

to start the program.

to build jar file in the same directory run

>mvn package

To run tests, in the same directory run:

>mvn test

To get the test report in the same directory run

>mvn test jacoco:report

to generate the javadoc got to same directory and run:

>mvn javadoc:javadoc

[Requirements](documentation/REQUIREMENTS.md)


[Architecture](documentation/ARCHITECTURE.md)


[Hours tracking](hours/TRACKING.md)

[Latest release](https://github.com/tuomoM/ot-harjoitustyo/releases/Week5)
