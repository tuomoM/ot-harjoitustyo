/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_DOA;
import Mahjong_domain.MJ_Player;
import java.sql.*;

/**
 * DOA class for saving MJ_Players to database
 * @author tuomomehtala
 */
public class MJ_Player_DOA {
    
    String name;
    Connection db;
    MJ_DataBase dataBase; 
    private boolean test;
    
  /**
   * Constructor sets the database name depending on if this is a test run or actual gameplay
   * @param test True if test run otherwise false.
   * @throws SQLException 
   */  
   public MJ_Player_DOA(boolean test)throws SQLException{
    if(test){
        this.name = "MjTest";
    }else{
        this.name = "MjDb";
    }
    this.test = test;
   dataBase = new MJ_DataBase(name);
   // this.db = dataBase.getDb();
    
    
    
    
}   
   /**
    * Retrieve all players from database.
    * @return array of MJ_Players saved in database
    * @throws SQLException 
    */
   public MJ_Player[] getPlayers()throws SQLException{
       this.db = dataBase.getDb();
       MJ_Player[] players = new MJ_Player[20];
       int index = 0;
       PreparedStatement p =db.prepareStatement("SELECT id, name from players limit 20");
       
       try(ResultSet r = p.executeQuery()){
           while(r.next()){
               players[index] = new MJ_Player(r.getString("name"),r.getInt(1));
               index++;
               
           }
           
       }
       this.db.close();
       
       return players;
   }
   /**
    * Method to delete the contents of database. Can only be used in testing mode
    * @return True if deletion was possible, false otherwise.
    * @throws SQLException 
    */
   public boolean emptyTables() throws SQLException{
       if(test){
           this.dataBase.emptyDB();
           return true;
       }
       return false;
       
   }
   /**
    * Saves the MJ_Player object instance to database if the same named player doesn't already exist.
    * If player with same name already exists, the database will return the saved id otherwise id of the new saved entry. The returning is done by setting the value on the parameter object instance.
    * @param player
    * @throws SQLException 
    */
   public void savePlayer(MJ_Player player)throws SQLException{
       
       this.db = dataBase.getDb();
       db.setAutoCommit(false);
       //check if the player already exists
       PreparedStatement p1 =db.prepareStatement("SELECT id, name from players WHERE name =?");
       p1.setString(1, player.getName());
       ResultSet r1 = p1.executeQuery();
       db.commit();
       if(r1.next()){
           player.setId(r1.getInt(1));
           db.close();
           return;
       }
       
       // if not, create a new entry
       PreparedStatement p = db.prepareStatement("INSERT INTO players (name) VALUES(?) ");
       p.setString(1, player.getName());
       p.executeUpdate();
        db.commit();
        try (ResultSet r = p.getGeneratedKeys()) {
            player.setId(r.getInt(1));
        }
        
       db.close();
   }
    
   public void closeDb() throws SQLException{
       db.close();
   }
    
}
