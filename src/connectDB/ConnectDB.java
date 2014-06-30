/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luan
 */
public class ConnectDB {
    public static Connection getConnect(){
        SettingsDB sdb= SettingsDB.readSettings();
        if(sdb==null){
            return null;
        }
        Connection connect=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://"+sdb.getServer()+":"+sdb.getPort()+"/SchoolManagement", sdb.getUsrn(), sdb.getPswd());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return connect;
    }
}
