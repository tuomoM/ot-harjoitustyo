/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_domain;

import java.util.Arrays;

/**
 *
 * @author tuomomehtala
 */
public class MJ_Turn  {
   private int id; 
   private int[] money;
   private int[] score;
   private int winner;
    
    private int turnNo;
    
    private int power;
    
    public MJ_Turn(int[] money, int power, int turnNo){
        this.score  = new int[4];
        this.money = money;
        this.power = power;
        this.turnNo = turnNo;
        this.id = -1;
    }
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
    public MJ_Turn(int[] money, int power, int winner, int turnNo, int[] score, int id){ // this method is for loading the instances from database.
        this.winner = winner;
        this.score = score;
        this.money = money;
        this.power = power;
        this.turnNo = turnNo;
        this.id = id;
    }
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
            System.out.println("nonpower:"+winner+", "+second+", "+third+", "+last );
            endMoney[winner] = money[winner] + 4* score[winner];
            if(power==second){
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
