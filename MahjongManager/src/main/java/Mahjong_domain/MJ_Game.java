/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_domain;
import java.util.ArrayList;
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
    
    
    public MJ_Game(MJ_Player[] players, int startAmount){
        this.players = players;
        this.turns = new ArrayList<>();
        this.gameEnd = false;
        turn = new MJ_Turn(startAmount);
        this.power = 0;
        
    }
    public String powerPlayer(){
        return players[power].getName();
    }
    public String getPlayerName(int player){
        return this.players[player].getName();
    }
    public MJ_Game(int gameId){
        // load game from database
        
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
    public ArrayList<MJ_Turn> getTurns(){
        return this.turns;
    }
    public String[] getPlayers(){
        String[] output = {players[0].getName(),players[1].getName(),players[2].getName(),players[3].getName()};
        return output;
    }
    public MJ_Turn getCurrentTurn(){
        // consider changing this to just send the score.
        return turn;
    }
    private boolean saveGame(){
        // to be implemented, maybe add this to the ui instead.
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
