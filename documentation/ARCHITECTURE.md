Mahjong manager contains 3 packages with following structure

![Package and class architecture](Achitecturev01.jpg)



The logic of the program is straight forward. 
The sample says that there is no loading from database in that process and it means that game is started from scratch and not taken an old game from database. At the end the system automatically saves the game in to database. This is to prevent possibly already saved games from appearing in open games and for possible (dream on...) addition of reporting on which player has won or lost the most etc.

![Simple gameplay sequence](sequence.jpeg)


The database design should be refactored. The turn, turnScore and turnMoney should be merged into just one table.

![db diagram](dbdiagram1.jpeg)
