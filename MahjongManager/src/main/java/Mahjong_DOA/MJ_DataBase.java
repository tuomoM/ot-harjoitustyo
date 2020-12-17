/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_DOA;

/**
 * Class to create all the database tables and provide the connection object.
 * @author tuomomehtala
 */
import java.sql.*;


public class MJ_DataBase {

    String dbName;
    Connection db;
    /**
     * Sets the name of the database and creates the tables if they dont exist
     * @param name Name of the database.
     * @throws SQLException 
     */
    public MJ_DataBase(String name)throws SQLException{
        this.dbName = name;
        this.db = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
       if(!DbExists()) init();
       this.db.close();
    }
    /**
     * Sets the database tables in place. 
     * @throws SQLException 
     */
    private void init()throws SQLException{
        db.setAutoCommit(false);
        Statement s = db.createStatement();
        s.execute("CREATE TABLE players (id INTEGER PRIMARY KEY, name TEXT );");
        
        db.commit();
       
        
        s.execute("CREATE TABLE game (id INTEGER PRIMARY KEY, status TEXT, date DATE, power INTEGER, winscore INTEGER, winner INTEGER, turnNo INTEGER, player1 INTEGER, player2 INTEGER, player3 INTEGER, player4 INTEGER,"+
                "FOREIGN KEY (winner) REFERENCES player(id), FOREIGN KEY (player1) REFERENCES players(id),FOREIGN KEY (player2) REFERENCES players(id),FOREIGN KEY (player3) REFERENCES players(id)"+
                ",FOREIGN KEY (player4) REFERENCES players(id));");
        db.commit();
        
       s.execute("CREATE TABLE turn (id INTEGER PRIMARY KEY, gameId INTEGER ,power INTEGER, turnNo INTEGER, winner INTEGER, FOREIGN KEY (gameId) REFERENCES game(id));");
       db.commit();
       s.execute("CREATE TABLE turnScore (id INTEGER PRIMARY KEY, turnId INTEGER,  score1 INTEGER, score2 INTEGER, score3 INTEGER, score4 INTEGER, FOREIGN KEY (turnId) REFERENCES turn(id));");
       db.commit();
       s.execute("CREATE TABLE turnMoney (id INTEGER PRIMARY KEY,turnId INTEGER , money1 INTEGER, money2 INTEGER, money3 INTEGER, money4 INTEGER,FOREIGN KEY (turnId) REFERENCES turn(id));");
       db.commit();
       s.execute("PRAGMA foreign_keys = ON"); 
        db.commit();
        db.setAutoCommit(true);
    }
  /**
   * Method to empty the database. Should be used in test mode only.
   * @throws SQLException 
   */
    public void emptyDB()throws SQLException{
        this.db = getDb();
        db.setAutoCommit(false); // should be more efficient to do in one commit
        Statement s = db.createStatement();
        
        s.execute("DELETE FROM players");
      
       s.execute("DELETE FROM game");
       s.execute("DELETE FROM turn");
       s.execute("DELETE FROM turnScore");
       s.execute("DELETE FROM turnMoney");
       
       db.commit();
       
       ResultSet r = s.getResultSet(); // trying to force synchronity
       db.setAutoCommit(true);
       db.close();
        
    }
    /**
     * Returns a new connection
     * @return
     * @throws SQLException 
     */
    public Connection getDb() throws SQLException{
         this.db = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
        return this.db;
    }
  
    private boolean DbExists()throws SQLException{
        DatabaseMetaData md = db.getMetaData();
        ResultSet r = md.getTables(null, null,"players" , null); 
        return r.next();
             
       
    }
}
