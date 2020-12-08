/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_domain;

import java.util.Arrays;

/**
 * Class represents one turn in program. 
 * The logic for calculating the outcome is stored in this class
 * as well as the information about the score on turn and the money at the beginning of turn.
 * The turn object is unaware of the players, instead it considers the players to be 0,1,2 & 3. The mapping of the players is not in this class.
 * @author tuomomehtala
 */
public class MJ_Turn  {
   private int id; 
   private int[] money;
   private int[] score;
   private int winner;
    
    private int turnNo;
    
    private int power;
    /**
     * Creates a new turn event with money information. this is the basic constructor used in game tracking. 
     * @param money Is the current standing of players within money. 
     * @param power Is Player who holds the power, traditionally East at the first round of rounds
     * @param turnNo The number of the turn
     */
    public MJ_Turn(int[] money, int power, int turnNo){
        this.score  = new int[4];
        this.money = money;
        this.power = power;
        this.turnNo = turnNo;
        this.id = -1;
    }
    /**
     * Constructor to be used for games very first round. Everyone has the same amount of money
     * @param startMoney Base money to be in game.
     */
    public MJ_Turn(int startMoney){
        this.winner = -1;
        this.score = new int[4];
        this.money = new int[4];
        for(int i = 0; i<4;i++){
            this.money[i]=startMoney;
        }
        this.power = 0;
        this.turnNo = 1;
        this.id=-1;
             
        
        
    }
    public int getId(){
        return this.id;
    }
    public int getWinner(){
        return this.winner;
    }
    /**
     * This constructor method is to be used for loading the turns 
     * @param money  table int[4] Money at the start of this round
     * @param power Power player during this round
     * @param winner Winner of this round in case already closed
     * @param turnNo # of turn
     * @param score table int[4] containing the scores
     * @param id this is the id used to save and load the turn.
     */
    public MJ_Turn(int[] money, int power, int winner, int turnNo, int[] score, int id){ // this method is for loading the instances from database.
        this.winner = winner;
        this.score = score;
        this.money = money;
        this.power = power;
        this.turnNo = turnNo;
        this.id = id;
    }
    /**
     * Sets the score to player for this round. System only allows positive score and players between 0 - 3
     * @param score The score to be updated. Has to be positive number
     * @param player  Which player is to be updated, values 0 - 3.
     */
    public void setScore(int score, int player){
        if(player<4&&score>0)
        this.score[player] = score;
        
    }
    public int getTurnNo(){return this.turnNo;}
    public int getPower(){
        return this.power;
    }
    public int[] getScore(){
        return this.score;
    }
    public int[] getMoney(){
        return this.money;
    }
    /**
     * The method is used to get the standing for the round knowing winner and using current score.
     * This method was made public to enable easier testing
     * @param winner Which player won the turn? values 0 - 3
     * @return  int[4] order where winner is always at index 0
     */
    public int[] roundOrder(int winner){
        int[] order = {winner,-1,-1,-1};
       
        
        for(int i = 0; i<4;i++){
            if(i == winner) continue;
            if(order[1]==-1){
                order[1]=i;
            }else{
                if(score[i]>=score[order[1]]){
                    if(order[2]==-1){
                        order[2] = order[1];
                        order[1]= i;
                    }else{
                        if(score[order[1]]>=score[order[2]]){  
                            order[3]= order[2];
                            order[2]=order[1];
                            order[1]=i;
                        }
                    }
                }//score[i] > score[order[1]]) 
                else if(score[i]==score[order[1]]){
                     if(order[2]==-1){
                        order[2] = order[1];
                        order[1]= i;
                    }else{
                        if(score[order[1]]>=score[order[2]]){
                            order[3] = order[2];
                            order[1]=i;                          
                        }
                    }
                }else{ // score[i]<score[order[1]]
                    if(order[2]==-1){
                        order[2]=i;
                    }else if(score[i]>=score[order[2]]){
                        order[3] = order[2];
                        order[2] = i;
                        
                    }else{
                        order[3]=i;
                        
                    }
                    
                }
            }
        }
        
        
        
        
        return order;
        
    }
    /**
     * This method is called when the turn is complete and should be scored.
     * Logic is simple, everyone pays winner amount of his/her score
     * everyone else pays the difference in score. power player pays and receives in double.s
     * @param winner
     * @return Int[4] containing the end money. 
     */
    public int[] endTurn(int winner){  
    // could use some refactoring
        this.winner = winner;   
        int[] endMoney = new int[4];
        int last = 0,third = 0,second = 0;
        boolean first = true;
        int[] order = roundOrder(winner);
        last = order[3];
        third = order[2];
        second = order[1];
        if(winner == power){
           
             endMoney[winner] = money[winner] + 6* score[winner];
             endMoney[order[1]] = money[order[1]] - 2*score[winner] + (score[order[1]]-score[order[2]]) + (score[order[1]]-score[order[3]]);
             endMoney[order[2]] = money[order[2]] - 2*score[winner] - (score[order[1]]-score[order[2]]) + (score[order[2]] - score[order[3]]);
             endMoney[last] = money[last]-2*score[winner]- (score[second]-score[last])-(score[third]-score[last]);
           
        }else{
            //System.out.println("nonpower:"+winner+", "+second+", "+third+", "+last );
            endMoney[winner] = money[winner] + 4* score[winner];
            if(power==second){
               // System.out.println("Right place Winner: "+score[winner] +" second: "+score[second]+" first dif"+2*(score[second]-score[third])+" Second diff "+ 2*(score[second]-score[last]));
             endMoney[second] = money[second] - 2*score[winner] + 2*(score[second]-score[third]) + 2*(score[second]-score[last]);
              endMoney[third] = money[third] - score[winner] - 2*(score[second]-score[third]) + (score[third] - score[last]);
              endMoney[last] = money[last]-score[winner]- 2*(score[second]-score[last])-(score[third]-score[last]);
            }else if(power == third){
                 endMoney[second] = money[second] - score[winner] + 2*(score[second]-score[third]) + (score[second]-score[last]);
                 endMoney[third] = money[third] - 2*score[winner] - 2*(score[second]-score[third]) + (score[third] - score[last]);
                  endMoney[last] = money[last]-score[winner]- (score[second]-score[last])-2*(score[third]-score[last]);
            }else{
                  endMoney[second] = money[second] - score[winner] + (score[second]-score[third]) + 2*(score[second]-score[last]);
                 endMoney[third] = money[third] - score[winner] - (score[second]-score[third]) + 2*(score[third] - score[last]);
                  endMoney[last] = money[last]-2*score[winner]- 2*(score[second]-score[last])-2*(score[third]-score[last]);
                
            }
                
            
        }
        this.winner = winner;
        return endMoney;
    }
   @Override
    public String toString(){
      String output = Arrays.toString(money);
      return output;
    }

   

    
    
}
