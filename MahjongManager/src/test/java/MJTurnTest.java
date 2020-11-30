/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Mahjong_domain.MJ_Turn;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Random; 


/**
 *
 * @author tuomomehtala
 */
public class MJTurnTest {
   MJ_Turn turn;
   Random rand; 
//    public Mahjong_Turn_test() {
//    }
    
    @Before
    public void setUp() {
        turn = new MJ_Turn(1000);
        rand = new Random();
    }
    
 

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void checkOrder(){
         turn.setScore(10, 0);
         turn.setScore(100, 1);
         turn.setScore(10, 2);
         turn.setScore(101, 3);
         int[] order = turn.roundOrder(0);
         assertTrue(order[0]==0&&order[1]==3&&order[2]==1&&order[3]==2);
     }
    // public void hello() {}
     @Test
     public void NotPossibleToGiveNegScore(){
         turn.setScore(-10, 0);
         int[] score = turn.getScore();
         assertTrue(score[0]==0);
     }
     @Test
     public void EasyNoMoneyLostOrFound(){
      
         int[] money = turn.endTurn(0);
         assertTrue((money[0]+money[1]+money[2]+money[3])==4000);
     }
     @Test
     public void noMoneyLostOrFound(){
      turn.setScore(5+rand.nextInt(100), 0);
      turn.setScore(5+rand.nextInt(100), 1);
      turn.setScore(5+rand.nextInt(100), 2);
      turn.setScore(5+rand.nextInt(100), 3);
      int[] money = turn.endTurn(0);
      assertTrue((money[0]+money[1]+money[2]+money[3])==4000);
     }
    
}