/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mahjong_UI;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuomomehtala
 */
public class Main {
    
    
    public static void main(String[] args){
        try {
            MJTextUI textUI = new MJTextUI();
            textUI.start();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
