/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_UI;
import Mahjong_domain.*;
import Mahjong_DOA.*;
import java.sql.*;
import java.util.*;
import java.util.Scanner;
/**
 *
 * @author tuomomehtala
 */
public class MJTextUI {
    
    
    
    
    
    public MJTextUI() throws SQLException {
     
        
    }
    public void start() throws SQLException{
        MJ_Player_DOA playerDb = new MJ_Player_DOA(false);
        MJ_Player[] players = new MJ_Player[4];
        MJ_Player[] allPlayers = playerDb.getPlayers();
        int resp = 0;
        Scanner reader = new Scanner(System.in);
        printMenuOne();
        resp = Integer.valueOf(reader.nextLine().trim());
        switch(resp){
            case 1:
                        System.out.println("Input players");
        for (int i=0; i<4;i++){
            if(allPlayers[0]==null){
            System.out.println("Player no: " + (i+1));
            }else{
                int index = 1;
                while(true){
                    
                    System.out.println(index +" : "+allPlayers[index-1].getName());
                    if(index >= 100) break;
                    if(allPlayers[index]==null)break; 
                    index++;
                }
                System.out.println("Choose players ( 1-"+index+" ) choose 0 to create a new player");
                resp = Integer.valueOf(reader.nextLine());
                if(resp!=0){
                    players[i] = allPlayers[resp-1];
                    continue;
                }else{
                    System.out.println("Input name of player:");
                }
            }
            players[i]=new MJ_Player(reader.nextLine());
            playerDb.savePlayer(players[i]);
            
        }
                playerDb.closeDb();
                MJ_Game game = new MJ_Game(players,1000,false);
                play(game,reader);   
                break;
            case 2:
                
                playerDb.closeDb();
                MJ_Game_DOA gamedb = new MJ_Game_DOA(false);
                HashMap<Integer,java.sql.Date> openGames = gamedb.getOpenGames();
                gamedb.closeDb();
                int gameid = -1;
                while(true){
                System.out.println("Open games:");
               
                   openGames.forEach((id,date)->{
                       System.out.println(id+" "+date.toString());
                   });
                
                    System.out.println("Input gameID to be loaded, (0 quits)");
                 gameid = Integer.valueOf(reader.nextLine());
                 if(gameid==0) break;
                 if(openGames.containsKey(gameid)) break;
                }
  
            
            if(gameid!=0){
                MJ_Game loadedGame = new MJ_Game(gameid,false);
                play(loadedGame,reader);
            }
           
        }
        
       
        
        
        
        
    }
    public static void printMenuOne(){
        System.out.println("Welcome to MJ scorekeeper!");
        System.out.println("Choose action");
        System.out.println("1 = start new game");
        System.out.println("2 = continue existing game ");
        System.out.println("3 = exit");
        
        
    }
    public static void play(MJ_Game game, Scanner reader){
        int resp = -1;
        int amount = 0;
        while(true){
            System.out.println(Arrays.toString(game.getPlayerNames()));
            System.out.println(game);
            System.out.println("****************************");
            System.out.println(Arrays.toString(game.getRoundScore()));
            System.out.println("Power : "+game.powerPlayer());
            printGameMenu();
            resp = Integer.valueOf(reader.nextLine().trim());
            if(resp<1){
                if( game.saveGame()){
                    System.out.println("Game saved, game id: "+game.getId());
                }else{
                    System.out.println("Game was not saved");
                }
                break;
                
            }else
            if(resp<5){
                givePoints(resp-1,reader,game);
                
            }else if(resp == 5){
                endRound(reader,game);
                
            }
            else{
                game.endGame();
                System.out.println("Game over");
                System.out.println("Winner: "+game.winner().getName() );
                System.out.println("Winning money: "+game.getWinningScore());
                break;
            }
            
        }
        
    }
    public static void givePoints(int player, Scanner reader, MJ_Game game){
        System.out.println("Enter score for player "+ game.getPlayerName(player));
        int score = Integer.valueOf(reader.nextLine().trim());
        game.setScore(player, score);
    }
    public static void endRound(Scanner reader, MJ_Game game){
        System.out.println("Which player won (1-4):");
        int player = Integer.valueOf(reader.nextLine().trim());
        game.nextTurn(player-1);
    }
    public static void printGameMenu(){
        System.out.println("Choose action");
        System.out.println("0 - exit game");
        System.out.println("1 - add score to player 1");
        System.out.println("2 - add score to player 2");
        System.out.println("3 - add score to player 3");
        System.out.println("4 - add score to player 4");
        System.out.println("5 choose winner and go to next round");
        System.out.println("6 - End game");
        
        
        
    }
    public static void choosePlayers(){
        System.out.println("Input players");
        for (int i=0; i<4;i++){
            System.out.println("Player no: " + (i+1));
            
        }
        
        
        
    }
    
    
    
    
}
