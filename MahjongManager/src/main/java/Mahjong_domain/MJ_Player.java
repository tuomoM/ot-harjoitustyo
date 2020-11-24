
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_domain;

/**
 *
 * @author tuomomehtala
 */
public class MJ_Player {
    //
    private String name;
    
    public MJ_Player(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
