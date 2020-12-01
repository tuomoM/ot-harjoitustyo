/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuomomehtala
 */
import Mahjong_DOA.MJ_Player_DOA;
import Mahjong_domain.MJ_Game;
import Mahjong_domain.MJ_Player;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MJGameTest {
    MJ_Game game;
    MJ_Player[] players;
    int round;
    MJ_Player_DOA playerDB;
    
    
    @Before
    public void setup(){
          
            players = new MJ_Player[4];
            
                    try{
        playerDB = new MJ_Player_DOA(true);
    
        }catch(SQLException e){
            System.out.println("Database exeception 1:"+e.getMessage());
        }
        
        
            
            
            
            
        try {
           
             players[0] = new MJ_Player("Barack Obama");
             playerDB.savePlayer(players[0]);
             players[1] = new MJ_Player("Donald Trump");
             playerDB.savePlayer(players[1]);
             players[2] = new MJ_Player("Joe Biden");
             playerDB.savePlayer(players[2]);
             players[3] = new MJ_Player("Kamala Harris");
             playerDB.savePlayer(players[3]);
            
         
        } catch (SQLException ex) {
            Logger.getLogger(MJGameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.game = new MJ_Game(players,1000,true);
        }
        
        
        
    
    @Test
    public void powerMovesRight(){
        game.nextTurn(1);
        game.nextTurn(1);
        game.nextTurn(2);
        game.nextTurn(0);
        game.nextTurn(2);
        assertTrue(game.getPower()==0);
        
    }
    @Test
    public void KamalaWins(){
        game.setScore(0, 100);
        game.setScore(1, 50);
        game.setScore(2, 15);
        game.setScore(3,21);
        game.nextTurn(3);
        
         game.setScore(0, 10);
        game.setScore(1, 5);
        game.setScore(2, 15);
        game.setScore(3,21);
        game.nextTurn(3);
        
         game.setScore(0, 20);
        game.setScore(1, 50);
        game.setScore(2, 15);
        game.setScore(3,21);
        game.nextTurn(3);
        
         game.setScore(0, 100);
        game.setScore(1, 50);
        game.setScore(2, 15);
        game.setScore(3,41);
        game.nextTurn(3);
        
        game.endGame();
        assertTrue(game.winner().getName().equals("Kamala Harris"));
    }
    
    
    
    
}
