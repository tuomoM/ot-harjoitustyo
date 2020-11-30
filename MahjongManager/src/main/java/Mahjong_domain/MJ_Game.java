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
 *
 * @author tuomomehtala
 */
public class MJ_Game {
    private int gameId;
    private int turnNo;
    private boolean gameEnd;
    private ArrayList<MJ_Turn> turns;
    private MJ_Turn turn;
    private MJ_Player[] players;
    private int power;
    private MJ_Player winner;
    private int winScore;
    private MJ_Game_DOA gamedb;
    
    public MJ_Game(MJ_Player[] players, int startAmount, boolean test){
        this.players = players;
        this.turnNo = 1;
        this.turns = new ArrayList<>();
        this.gameEnd = false;
        turn = new MJ_Turn(startAmount);
        this.power = 0;
        try {
            this.gamedb = new MJ_Game_DOA(false);
        } catch (SQLException ex) {
            Logger.getLogger(MJ_Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
    public void setPlayers(MJ_Player[] players){
        this.players = players;
    }
    public MJ_Player[] getPlayers(){
        return this.players;
    }
    public void setID(int id){
        this.gameId = id;
    }
    public int getId(){
        return this.gameId;
    }
    public int getTurnNo(){
        return this.turnNo;
    }
    public String powerPlayer(){
        return players[power].getName();
    }
    public String getPlayerName(int player){
        return this.players[player].getName();
    }

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
    public boolean endGame(){
        this.gameEnd = true;
        int[] localMoney = turn.getMoney();
        int local_winner = 0;
        for(int i= 1; i<4;i++){
            if(localMoney[i]>localMoney[local_winner]) local_winner = i;
        }
        this.winner = players[local_winner];
        this.winScore = localMoney[local_winner];
        return false; // to be fixed
    }
    public void setScore(int playerId, int score){
        turn.setScore(score, playerId);
 
    }
    public void nextTurn(int winner){
        int nextP = turn.getPower();
        MJ_Turn nextTurn;
        if(nextP != winner) nextP++;
        if(nextP == 4) nextP = 0;
        this.power = nextP;
        turns.add(turn);
        this.turnNo ++;
        int[] money = turn.endTurn(winner);
        
        turn = new MJ_Turn(money,nextP,this.turnNo);
        
        
        
    }
    public boolean gameStatus(){
        return this.gameEnd;
    }
    public ArrayList<MJ_Turn> getTurns(){
        return this.turns;
    }
    public String[] getPlayerNames(){
        String[] output = {players[0].getName(),players[1].getName(),players[2].getName(),players[3].getName()};
        return output;
    }
    public MJ_Turn getCurrentTurn(){
        // consider changing this to just send the score.
        return turn;
    }
    public boolean saveGame(){
        try {
            this.turns.add(turn);
            gamedb.saveGame(this);
            this.turns.remove(turn);
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
