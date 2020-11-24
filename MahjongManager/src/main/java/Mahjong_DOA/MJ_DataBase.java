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
import java.sql.*;
import java.util.*;

public class MJ_DataBase {

    String dbName;
    Connection db;
    
    public MJ_DataBase(String name)throws SQLException{
        this.dbName = name;
        this.db = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
        
    }
}
