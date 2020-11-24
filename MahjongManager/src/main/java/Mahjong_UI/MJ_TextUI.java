/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_UI;
import Mahjong_domain.*;
import java.sql.*;
import java.util.*;
import java.util.Scanner;
/**
 *
 * @author tuomomehtala
 */
public class MJ_TextUI {
    
    public static void main(String[] args) throws SQLException {
        int resp = 0;
        MJ_Player[] players = new MJ_Player[4];
      
        Scanner reader = new Scanner(System.in);
        printMenuOne();
        resp = Integer.valueOf(reader.nextLine().trim());
        switch(resp){
            case 1:
                        System.out.println("Input players");
        for (int i=0; i<4;i++){
            System.out.println("Player no: " + (i+1));
            players[i]=new MJ_Player(reader.nextLine());
            
        }
                MJ_Game game = new MJ_Game(players,1000);
                play(game,reader);    
                    
        }
        
       
        
        
        
        
    }
    public static void printMenuOne(){
        System.out.println("Welcome to MJ scorekeeper!");
        System.out.println("Choose action");
        System.out.println("1 = start new game");
        System.out.println("2= continue existing game (to be implemented )");
        System.out.println("3 = exit");
        
        
    }
    public static void play(MJ_Game game, Scanner reader){
        int resp = -1;
        int amount = 0;
        while(true){
            System.out.println(Arrays.toString(game.getPlayers()));
            System.out.println(game);
            System.out.println("****************************");
            System.out.println(Arrays.toString(game.getRoundScore()));
            System.out.println("Power : "+game.powerPlayer());
            printGameMenu();
            resp = Integer.valueOf(reader.nextLine().trim());
            if(resp<1) break;
            if(resp<5){
                givePoints(resp-1,reader,game);
                
            }else if(resp == 5){
                endRound(reader,game);
                
            }else{
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
