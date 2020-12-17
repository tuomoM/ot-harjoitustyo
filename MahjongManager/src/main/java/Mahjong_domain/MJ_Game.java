/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_domain;
import Mahjong_DOA.MJ_Game_DOA;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class represents the game consisting of multiple turns.
 * It keeps track of the turns and who is the power player.
 * @author tuomomehtala
 */
public class MJ_Game {
    private int gameId;
    private int turnNo;
    private boolean test;
    private boolean gameEnd;
    private ArrayList<MJ_Turn> turns;
    private MJ_Turn turn;
    private MJ_Player[] players;
    private int power;
    private MJ_Player winner;
    private int winScore;
    private MJ_Game_DOA gamedb;
   /**
    * Constructor used to start a new game
    * @param players Players of the game of type MJ_Player[]. Game expect to have 4 players.
    * @param startAmount (int) Money each player has in the game. During game no new money is created but players can end up in dept.
    * @param test  Boolean tells game whether this is a test or an actual game play.  
    */ 
    public MJ_Game(MJ_Player[] players, int startAmount, boolean test){
        this.players = players;
        this.turnNo = 1;
        this.turns = new ArrayList<>();
        this.gameEnd = false;
        turn = new MJ_Turn(startAmount);
        this.gameId = -1;
        this.power = 0;
        this.test = test;
     
    }
    /**
     * Constructor can be used to load the game from database.
     * @param gameId  (int) id of the game to be loaded
     * @param test  indicator to tell whether this is a test, then test database is used.
     */
        public MJ_Game(int gameId, boolean test){
        try {
            // load game from database
            this.gamedb = new MJ_Game_DOA(test);
            this.gameId = gameId;
            int[] basicData = this.gamedb.loadGameBasicData(gameId);
            if(basicData[0]==0){
                this.gameEnd = false;
            }else{
                this.gameEnd = true;
            }
            this.power = basicData[1];
            
            //this.winner = basicData[3];
            this.winScore = basicData[2];
            this.turnNo= basicData[4];
            this.players = gamedb.loadGamePlayers(gameId);
            this.turns = gamedb.loadTurns(gameId);
            this.turn = turns.get(turns.size()-1);
            this.turns.remove(turns.size()-1);
        } catch (SQLException ex) {
            Logger.getLogger(MJ_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
//    public void setPlayers(MJ_Player[] players){
//        this.players = players;
//    }
    /**
     * Returns the players of the game
     * @return  Object instances of class MJ_Player
     */    
    public MJ_Player[] getPlayers(){
        return this.players;
    }
    /**
     * Sets the id for the game. This is called by MJ_Game_DOA once the id is retrieved from database
     * @param id 
     */
    public void setID(int id){
        this.gameId = id;
    }
    /**
     * Returns the Id of saved game. -1 if not yet saved.
     * @return 
     */
    public int getId(){
        return this.gameId;
    }
    /**
     * Returns the current turn number
     * @return 
     */
    public int getTurnNo(){
        return this.turnNo;
    }
    /**
     * Returns the name of the current power player
     * @return 
     */
    public String powerPlayer(){
        return players[power].getName();
    }
//    public String getPlayerName(int player){
//        return this.players[player].getName();
//    }

    public int[] getRoundScore(){
        return this.turn.getScore();
    }
    public MJ_Player winner(){
        if(gameEnd){
            
            return this.winner;
        }else return null;
    }
    public int getWinningScore(){
        if(gameEnd){
            return this.winScore;
        }
        return 0;
    }
    public int getPower(){
        return this.power;
    }
    /**
     * Sets the status of the game complete, calculates the winner and saves the game to database.
     * @return True in case the saving was successful.
     */
    public boolean endGame(){
        this.gameEnd = true;
        int[] localMoney = turn.getMoney();
        int local_winner = 0;
        for(int i= 1; i<4;i++){
            if(localMoney[i]>localMoney[local_winner]) local_winner = i;
        }
        this.winner = players[local_winner];
        this.winScore = localMoney[local_winner];
        return saveGame();
        
    }
    /**
     * The method is used to set score to players during turn. The score set will override possible already set score for the same player.
     * @param playerId - position of the player in the game ie. 0-3, not the ID of the saved player.
     * @param score - Score you wish to set for the player
     */
    public void setScore(int playerId, int score){
        turn.setScore(score, playerId);
 
    }
    /**
     * Method to end the turn and move to next.
     * The turn score is calculated and the money for each player is passed to next turn
     * @param winner  Index of winner in game players. 0-3
     */
    public void nextTurn(int winner){
        int nextP = this.power;
        MJ_Turn nextTurn;
        if(nextP != winner) nextP++;
        if(nextP == 4) nextP = 0;
        this.power = nextP;
        turns.add(turn);
        this.turnNo ++;
        int[] money = turn.endTurn(winner);
        
        turn = new MJ_Turn(money,nextP,this.turnNo);
        
        
        
    }
    /**
     * Returns true if game has ended. This is used in saving the game.
     * @return 
     */
    public boolean gameStatus(){
        return this.gameEnd;
    }
    /**
     * Returns ArrayList of MJ_Turn objects of the game. This is used in saving the game.
     * @return 
     */
    public ArrayList<MJ_Turn> getTurns(){
        return this.turns;
    }
    /**
     * Returns list of player names in order they are set in game.
     * @return String[4] where player 1 in index 0 and player 2 in index 1 ..
     */
    public String[] getPlayerNames(){
        String[] output = {players[0].getName(),players[1].getName(),players[2].getName(),players[3].getName()};
        return output;
    }
    public MJ_Turn getCurrentTurn(){
        // consider changing this to just send the score.
        return turn;
    }
    /**
     * Can be called to save the game.
     * @return True in case the saving was successful.
     */
    public boolean saveGame(){
        try {
            this.turns.add(this.turn);
            this.gamedb = new MJ_Game_DOA(test);
            gamedb.saveGame(this);
        } catch (SQLException ex) {
            Logger.getLogger(MJ_Game.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        String output = "";
        if(!this.turns.isEmpty()){
            for(int i = 0;i<=turns.size()-1; i++){
               output+= turns.get(i).toString() + "\n";
            }
        }
        output+= this.turn.toString();
        return output;
    }
    
            
    
}
