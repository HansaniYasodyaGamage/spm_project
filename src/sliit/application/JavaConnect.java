/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sliit.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHATA
 */
public class JavaConnect {
    public static Connection connectdb(){
        Connection con  = null;
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/sliit","sliit","sliit");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(JavaConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
}
