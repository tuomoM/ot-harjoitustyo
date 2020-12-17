# Ohjelmistotekniikka harjoitustyö
## Mahjong manager
Simple tracking tool for gameplay.



To run current version, replicate repository to your computer. In folder MahjongManager run 

>mvn compile exec:java -Dexec.mainClass=Mahjong_UI.Main

to start the program.

to build jar file in the same directory run

>mvn package

To run tests, in the same directory run:

>mvn test

To get the test report in the same directory run

>mvn test jacoco:report

to generate the javadoc got to same directory and run:

>mvn javadoc:javadoc


## links to documentation and releases

[Requirements](documentation/REQUIREMENTS.md)


[Architecture](documentation/ARCHITECTURE.md)

[User manual](documentation/USERMANUAL.md)


[Hours tracking](hours/TRACKING.md)

[Latest release](https://github.com/tuomoM/ot-harjoitustyo/releases/final)
