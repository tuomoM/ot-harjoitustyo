# Mahjong game score keeper #

Score keeper is used to keep track of game progress and score. The actual individual score after each round is calculated by players manually and inputted to system, which will take care of calculations.

##Planned functionalities ##
* Create new player profile

* Start a new game
* choose 4 players from the player profiles, if not available, move to create new player profile.
* Game will start with East being the game  wind assigned to player in slot 1.
* once round is completed, user inputs the individual scores and marks the winner of the round.
* System calculates the actual score from the user inputted points with following rules:
  * Winner receives the value of his/her winning hand from all players, with the rounds wind paying double. Should the winner be the rounds wind, everyone pays double to him/her.
  * All other players receive value of the difference between each other, so that the one with lowest score pays to all others the difference between his/her hand to other players hand
  * if the winner is not the wind of the game, the wind moves forward to next player. if wind of the game won she/he will remain the wind for the coming round aswell.	 
  * once the wind of the game returns to the first player, system will ask whether the players wish to continue for another game. If they do, the game starts with the current points from start. If the players are not willing to continue, the system will display end results
* It is possible to save and load game from database


Future options
* Statistics for the players, who has won most and who has lost most, how many consecutive wins / player
