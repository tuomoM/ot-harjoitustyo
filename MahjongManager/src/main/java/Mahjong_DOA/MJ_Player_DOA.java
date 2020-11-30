/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_DOA;
import Mahjong_domain.MJ_Player;
import java.sql.*;
import javax.sql.StatementEvent;
/**
 *
 * @author tuomomehtala
 */
public class MJ_Player_DOA {
    
    String name;
    Connection db;
    MJ_DataBase dataBase; 
    private boolean test;
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
   public boolean emptyTables() throws SQLException{
       if(test){
           this.dataBase.emptyDB();
           return true;
       }
       return false;
       
   }
   public void savePlayer(MJ_Player player)throws SQLException{
       
       this.db = dataBase.getDb();
       //check if the player already exists
       PreparedStatement p1 =db.prepareStatement("SELECT id, name from players WHERE name =?");
       p1.setString(1, player.getName());
       ResultSet r1 = p1.executeQuery();
       if(r1.next()){
           player.setId(r1.getInt(1));
           return;
       }
       
       // if not, create a new entry
       PreparedStatement p = db.prepareStatement("INSERT INTO players (name) VALUES(?) ");
       p.setString(1, player.getName());
       p.executeUpdate();
       
        try (ResultSet r = p.getGeneratedKeys()) {
            player.setId(r.getInt(1));
        }
        
       db.close();
   }
    
   private void statementClosed(StatementEvent event){
       
   }
   public void closeDb() throws SQLException{
       db.close();
   }
    
}
