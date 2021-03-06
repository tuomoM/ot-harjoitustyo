/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_DOA;

/**
 *
 * @author tuomomehtala
 */
import Mahjong_domain.MJ_Game;
import Mahjong_domain.MJ_Player;
import Mahjong_domain.MJ_Turn;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Database access class for game.
 * @author tuomomehtala
 */

public class MJ_Game_DOA {

    String name;
    Connection db;
    MJ_DataBase dataBase;
    private boolean test;
/**
 * Sets the database name to be used according to whether test or gameplay
 * @param test True if this is a test run, otherwise false
 * @throws SQLException 
 */
    public MJ_Game_DOA(boolean test) throws SQLException {

        if (test) {
            this.name = "MjTest";
        } else {
            this.name = "MjDb";
        }
        this.test = test;
        dataBase = new MJ_DataBase(name);
      //  this.db = dataBase.getDb();
     
    }
    /**
     * Returns a hashmap object with all the open games. 
     * @return HashMap with two fields id and date of the saving.
     * @throws SQLException 
     */
    public HashMap<Integer,Date> getOpenGames() throws SQLException{
        this.db = dataBase.getDb();
        HashMap<Integer,Date> openGames  = new HashMap<>();
        
        PreparedStatement p = db.prepareStatement("SELECT id AS id, date AS date FROM game WHERE status = 'false' ");
        p.execute();
        ResultSet r = p.getResultSet();
        while(r.next()){
            
              openGames.put(r.getInt("id"), r.getDate("date"));
        }
        r.close();
        db.close();
        return openGames;
        
    }
    
    public void closeDb() throws SQLException{
        db.close();
    }
/**
 * Loads game table contents for a game
 * @param gameid
 * @return returns the content as int[5] where indexes are 0 = (0=false, 1=true) game ended, 1 = index of current power player, 2 = winning score, 3 = winner (database id), 4 = turn number of the game.
 * @throws SQLException 
 */
    public int[] loadGameBasicData(int gameid) throws SQLException {
        this.db = dataBase.getDb();
        int[] gameData = new int[10];

        PreparedStatement p = db.prepareStatement("Select status, power, winscore, winner, turnNo from game where id =?");
        p.setInt(1, gameid);

        ResultSet r = p.executeQuery();

        if (r.next()) {

            if (r.getString("status").equals("false")) {
                gameData[0] = 0;
            } else {
                gameData[0] = 1;
            }
            gameData[1] = r.getInt("power");
            gameData[2] = r.getInt("winscore");
            gameData[3] = r.getInt("winner");
            gameData[4] = r.getInt("turnNo");
        } else {

        }
        r.close();
        db.close();
        return gameData;
    }
/**
 * Loads the players from database for a given game id
 * @param gameid Id of the game to be loaded
 * @return Array of MJ_Players as players of the game.
 * @throws SQLException 
 */
    public MJ_Player[] loadGamePlayers(int gameid) throws SQLException {
        // consider rewriting the queries into just one query
        this.db = dataBase.getDb();
        MJ_Player[] players = new MJ_Player[4];
        PreparedStatement p = db.prepareStatement("SELECT a.id AS id , a.name AS name FROM players a, game b WHERE a.id = b.player1 AND b.id =? ");
        p.setInt(1, gameid);
        ResultSet r = p.executeQuery();
        players[0] = new MJ_Player(r.getString("name"), r.getInt("id"));

        PreparedStatement p2 = db.prepareStatement("SELECT a.id AS id , a.name AS name FROM players a, game b WHERE a.id = b.player2 AND b.id =? ");
        p2.setInt(1, gameid);
        ResultSet r2 = p2.executeQuery();
        players[1] = new MJ_Player(r2.getString("name"), r2.getInt("id"));

        PreparedStatement p3 = db.prepareStatement("SELECT a.id AS id , a.name AS name FROM players a, game b WHERE a.id = b.player3 AND b.id =? ");
        p3.setInt(1, gameid);
        ResultSet r3 = p3.executeQuery();
        players[2] = new MJ_Player(r3.getString("name"), r3.getInt("id"));

        PreparedStatement p4 = db.prepareStatement("SELECT a.id AS id , a.name AS name FROM players a, game b WHERE a.id = b.player4 AND b.id =? ");
        p4.setInt(1, gameid);
        ResultSet r4 = p4.executeQuery();
        players[3] = new MJ_Player(r4.getString("name"), r4.getInt("id"));
        this.db.close();
        
        return players;
    }
/**
 * Loads the turns of the current game from database.
 * @param gameid
 * @return ArrayList containing the MJ_Turn objects for the game in sorted order. The active round is the last item.
 * @throws SQLException 
 */
    public ArrayList<MJ_Turn> loadTurns(int gameid) throws SQLException {
           this.db = dataBase.getDb();
        ArrayList<MJ_Turn> turns = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT a.id as id, a.turnNo AS turnNo, a.power AS power, a.winner as winner, b.score1 AS score1, b.score2 AS score2, b.score3 AS score3, b.score4 AS score4, c.money1 AS money1, c.money2 AS money2, c.money3 AS money3, c.money4 AS money4 "
                + "FROM turn a, turnScore b, turnMoney c WHERE a.id = b.turnId AND a.id = c.turnId AND a.gameId =?");
        p.setInt(1, gameid);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            int[] money = new int[4];
            int[] score = new int[4];
            for (int i = 0; i < 4; i++) {
                money[i] = r.getInt("money" + (i + 1));
                score[i] = r.getInt("score" + (i + 1));
            }
            turns.add(new MJ_Turn(money, r.getInt("power"), r.getInt("winner"), r.getInt("turnNo"), score, r.getInt("id")));
        }
     
        Collections.sort(turns, (MJ_Turn t, MJ_Turn s) -> {
            return t.getTurnNo() - s.getTurnNo();
        });
        
        return turns;

    }
/**
 *  Method to save game into database.
 * @param game MJ_Game object to be saved.
 * @throws SQLException 
 */
    public void saveGame(MJ_Game game) throws SQLException {
           this.db = dataBase.getDb();
           db.setAutoCommit(false);
        if (game.getId() == -1) { 
     
           
            PreparedStatement p = db.prepareStatement("INSERT INTO game(status,date, power, winscore, winner, turnNo"
                    + ",player1,player2,player3,"
                    + "player4) values (?,?,?,?,?,?,?,?,?,?)");
            p.setString(1, String.valueOf(game.gameStatus()));
            p.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            p.setInt(3, game.getPower());
            p.setInt(4, game.getWinningScore());
            if (game.gameStatus()) {
                p.setInt(5, game.winner().getId());
            } else {
                p.setInt(5, -1);
            }
            p.setInt(6, game.getTurnNo());
            p.setInt(7, game.getPlayers()[0].getId());
            p.setInt(8, game.getPlayers()[1].getId());
            p.setInt(9, game.getPlayers()[2].getId());
            p.setInt(10, game.getPlayers()[3].getId());

            p.execute();
           
            ResultSet r = p.getGeneratedKeys();
            
            int gameid = r.getInt(1);
             db.commit();
             game.setID(gameid);
            saveTurns(game,0);
     

        } else { 
        
            PreparedStatement p = db.prepareStatement("UPDATE game SET status = ?, date = ?, power = ?, winscore = ?, turnNo = ? WHERE id = ?");

            p.setString(1, String.valueOf(game.gameStatus()));
            p.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            p.setInt(3, game.getPower());
            p.setInt(4, game.getWinningScore());
         
           p.setInt(5,game.getTurnNo());
            p.setInt(6, game.getId());
           
            p.execute();
            db.commit();
            db.close();
            db = dataBase.getDb();
            db.setAutoCommit(false);
            
            //Find the turn to be updated.
            int index = -2;
            for (int i = 0; i < game.getTurns().size(); i++) {
          
                if (game.getTurns().get(i).getId() == -1) {
                    index = i - 1;
                    break;
                }
            }
            int lastI = (index > -1) ? index : game.getTurns().size()-1;
            
            
               
      
                PreparedStatement p2 = db.prepareStatement("UPDATE turn SET winner = ? WHERE id=?");
                p2.setInt(1, game.getTurns().get(lastI).getWinner());
                p2.setInt(2, game.getTurns().get(lastI).getId());

                PreparedStatement p3 = db.prepareStatement("UPDATE turnScore SET score1 = ?, score2 = ?, score3 = ?, score4 = ? WHERE turnId = ?");
                PreparedStatement p7 = db.prepareStatement("UPDATE turnMoney SET money1 = ?, money2 = ?, money3 = ?, money4 = ? WHERE turnId = ?");
                for (int i = 0; i < 4; i++) {
                    p3.setInt(i + 1, game.getTurns().get(lastI).getScore()[i]);
                    p7.setInt(i + 1, game.getTurns().get(lastI).getMoney()[i]);
                }   
                p3.setInt(5,game.getTurns().get(lastI).getId() );
                p7.setInt(5,game.getTurns().get(lastI).getId() );
               try{
                p3.execute();
               }finally{
                    db.commit();
               }
                 db.commit();
                 
               
                p7.execute();
                db.commit();
                
                saveTurns(game,lastI+1);


            }
        db.close();


    }
    /**
     * Method for saving new turns to database. This is a private method and is used by saveGame method.
     * @param game Object instance of MJ_Game to be saved.
     * @param startIndex Index of the first new turn, ie. not existing in database already.
     * @throws SQLException 
     */
    private void saveTurns(MJ_Game game, int startIndex) throws SQLException{
  
         PreparedStatement p2 = db.prepareStatement("INSERT INTO turn(gameId,power ,turnNo, winner) values (?,?,?,?)"); 
            PreparedStatement p3 = db.prepareStatement("INSERT INTO turnScore(turnId,score1,score2,score3,score4) values (?,?,?,?,?)");
            PreparedStatement p4 = db.prepareStatement("INSERT INTO turnMoney(turnId,money1,money2,money3,money4) values (?,?,?,?,?)");
            for (MJ_Turn turn : game.getTurns()) {
             
                if(turn.getTurnNo()<startIndex)continue;
                p2.setInt(1, game.getId());
                p2.setInt(2, turn.getPower());
                p2.setInt(3, turn.getTurnNo());
                p2.setInt(4, turn.getWinner());
                p2.execute();
                db.commit();
                ResultSet r2 = p2.getGeneratedKeys();
                int turnId = r2.getInt(1);
                p2.clearParameters();

                p3.setInt(1, turnId);
                p3.setInt(2, turn.getScore()[0]);
                p3.setInt(3, turn.getScore()[1]);
                p3.setInt(4, turn.getScore()[2]);
                p3.setInt(5, turn.getScore()[3]);
                p3.addBatch();
                p4.setInt(1, turnId);
                p4.setInt(2, turn.getMoney()[0]);
                p4.setInt(3, turn.getMoney()[1]);
                p4.setInt(4, turn.getMoney()[2]);
                p4.setInt(5, turn.getMoney()[3]);
                p4.addBatch();

            }

            p3.executeBatch();
            p4.executeBatch();

            db.commit();
           
    }

}
