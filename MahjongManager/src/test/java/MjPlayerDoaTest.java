/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuomomehtala
 */
import Mahjong_domain.MJ_Player;
import Mahjong_DOA.MJ_Player_DOA;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Random; 


public class MjPlayerDoaTest {
    MJ_Player_DOA playerDB;
      @Before
    public void setUp() {
        try{
        playerDB = new MJ_Player_DOA(true);
        //rand = new Random();
        }catch(SQLException e){
            System.out.println("Database exeception 1:"+e.getMessage());
        }
        
        try{
            playerDB.emptyTables();
        }catch(SQLException e){
             System.out.println("Database exeception 2:"+e.getMessage());
        }
    }
 
//    @Test
//    public void NoSQLErrors(){
//        boolean error = false;
//        MJ_Player testplayer = new MJ_Player("Donald Trump");
//        try{
//            playerDB.savePlayer(testplayer);
//        }catch(SQLException e){
//            error = true;
//            System.out.println("Database exeception 3:"+e.getMessage());
//        }
//      
//        assertTrue(!error);
//        
//    }
    @Test
    public void creationReturnsKey(){
        
        MJ_Player testplayer = new MJ_Player("Joe Biden");
        try{
                  playerDB.savePlayer(testplayer);
                  MJ_Player[] players = playerDB.getPlayers();
                  assertTrue(players[0].getId() == testplayer.getId()&& testplayer.getId()!=-1);
        }catch(SQLException e){
            
             System.out.println("Database exeception 4:"+e.getMessage());
            
        }
        
        
    }
    @Test
    public void noDublicatCreation(){
        MJ_Player testplayer = new MJ_Player("Donald Trump Jr");
        MJ_Player testplayer2 = new MJ_Player("Donald Trump Jr");
        try{
            playerDB.savePlayer(testplayer);
            playerDB.savePlayer(testplayer2);
            assertTrue(testplayer.getId()==testplayer2.getId());
        }catch(SQLException e){
               System.out.println("Database exeception 5:"+e.getMessage());
        }
    }
    
//    @Test
//    public void canCreaTwentyPlayers(){
//        try{
//            
//            for(int i = 0;i<2;i++)playerDB.savePlayer(new MJ_Player("Barack Obama the "+i+"s"));
//            assertTrue(playerDB.getPlayers()[1].getName().equals("Barack Obama the 2s"));
//        }catch(SQLException e){
//              System.out.println("Database exeception 6:"+e.getMessage());
//        }
//    }
    @Test
    public void onlyTwentyPlayersReturned(){
        try{
       
            for(int i = 0;i<21;i++)playerDB.savePlayer(new MJ_Player("Hilary Clinton the "+i+"s"));
            assertTrue(playerDB.getPlayers().length==20);
        }catch(SQLException e){
               System.out.println("Database exeception 7:"+e.getMessage());
        }
    }
    
    
    
    
    @After
    public void cleanUp(){
        try{
            
            playerDB.emptyTables();
        }catch(SQLException e){
               System.out.println("Database exeception 8:"+e.getMessage());
        }
    }
    
    
}
