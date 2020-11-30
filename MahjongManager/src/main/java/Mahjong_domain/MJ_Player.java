
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
    private int id;
    public MJ_Player(String name){
        this.name = name;
        this.id = -1;
    }
    
    public MJ_Player(String name, int id){
        this.name = name;
        this.id = id;
    }
    public String getName(){
        return this.name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
}
