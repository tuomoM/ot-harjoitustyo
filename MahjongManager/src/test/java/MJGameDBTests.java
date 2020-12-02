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

public class MJGameDBTests {
    
    MJ_Game game;
    int gameId;
    
    @Before
    public void setUp(){
        
        MJ_Player[] players = new MJ_Player[4];
        
        players[0] = new MJ_Player("Paavo Väyrynen");
        players[1] = new MJ_Player("Sanna Marin");
        players[2] = new MJ_Player("Paula Ristikko");
        players[3] = new MJ_Player("Alexander Stubb");
        try {
            MJ_Player_DOA playerdb = new MJ_Player_DOA(true);
            playerdb.emptyTables();
            playerdb.savePlayer(players[0]);
            playerdb.savePlayer(players[1]);
            playerdb.savePlayer(players[2]);
            playerdb.savePlayer(players[3]);
        } catch (SQLException ex) {
            Logger.getLogger(MJGameDBTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        game = new MJ_Game(players, 1000, true);
        
        game.setScore(0, 100);
        game.setScore(1, 100);
        game.setScore(2, 110);
        game.setScore(3, 100);
        
        game.nextTurn(3); //paula wins first round
        
        game.setScore(0, 100);
        game.setScore(1, 250);
        game.setScore(2, 100);
        game.setScore(3, 100);
        
            game.nextTurn(1); //Sanna is winning
            
            game.saveGame();
            gameId = game.getId();
            this.game = null;
        
    }
    
    
    @Test
    public void checkSavedPlayers(){
        game = new MJ_Game(gameId,true);
        
        assertTrue(game.getPlayers()[0].getName().equals("Paavo Väyrynen")&& game.getPlayers()[1].getName().equals("Sanna Marin"));
        
        
        
    }
    
    @Test
    public void SannaStillWins(){ // FRAUD!! Screams Paavo
      game = new MJ_Game(gameId,true);
      game.endGame();
      assertTrue(game.winner().getName().equals("Sanna Marin"));
      
    
    }
    @Test
    public void gameSavedAgain(){
        game = new MJ_Game(gameId,true);
                game.setScore(0, 100);
        game.setScore(1, 250);
        game.setScore(2, 100);
        game.setScore(3, 100);
        assertTrue(game.saveGame());
        
        
        
    }
    @Test
    public void gameSavedAgainJustScore(){
        game = new MJ_Game(gameId,true);
        
        game.setScore(1, 250);
        game.setScore(2, 100);
        game.setScore(3, 90);
        game.saveGame();
        
        game = new MJ_Game(gameId,true);
        System.out.println(Arrays.toString(game.getCurrentTurn().getScore()));
        assertTrue(game.getCurrentTurn().getScore()[0]==0&&game.getCurrentTurn().getScore()[1]==250&&game.getCurrentTurn().getScore()[2]==100 &&game.getCurrentTurn().getScore()[3]==90);
        
    }
}
