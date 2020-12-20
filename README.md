# Ohjelmistotekniikka harjoitustyö
## Mahjong manager
Simple tracking tool for gameplay. Tool provides tracking of the status aswell as logic for score calculations.
The tool runs simple text UI.

## links to documentation 

[Requirements](documentation/REQUIREMENTS.md)

[Architecture](documentation/ARCHITECTURE.md)

[User manual](documentation/USERMANUAL.md)

[Testing document](documentation/TESTING.md)

[Hours tracking](hours/TRACKING.md)

## latest release

[Latest release](https://github.com/tuomoM/ot-harjoitustyo/releases/final)

## commandline commands
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

To create the report on checkstyle rules run
> mvn jxr:jxr checkstyle:checkstyle

The resulting error log can be found at target/site/checkstyle.html. Note that checkstyle rules and checks have been completely ignored in this project.

