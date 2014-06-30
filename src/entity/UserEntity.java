/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.*;

/**
 *
 * @author luan
 */
public class UserEntity extends Manager implements IEntity<User> {

    private User user;

    @Override
    public User findByID(int ID) {
        User u = null;
        String SQL = "SELECT ID, usrn, pswd, name, email, role FROM user WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                u = new User();
                u.setID(res.getInt(1));
                u.setUsrn(res.getString(2));
                u.setPswd(res.getString(3));
                u.setName(res.getString(4));
                u.setEmail(res.getString(5));
                u.setRole(res.getInt(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
    @Override
    public User findByName(String name) {
        User u = null;
        PreparedStatement state = null;
        
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        String SQL = "SELECT ID, usrn, pswd, name, email, role FROM user WHERE usrn=?";
        ResultSet res = null;
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, name);
            res = state.executeQuery();
            if (res.next()) {
                u.setID(res.getInt(1));
                u.setUsrn(res.getString(2));
                u.setPswd(res.getString(3));
                u.setName(res.getString(4));
                u.setEmail(res.getString(5));
                u.setRole(res.getInt(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public void setUser(User u) {
        user = u;
    }
    
    @Override
    public int save() {
        if (user == null) {
            return FAIL;
        }
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return CONNECT_FAIL;
        }
        int res = 0;
        PreparedStatement state = null;
        String SQL = "";
        try {
            if (user.getID() == 0) {
                SQL = "INSERT user (usrn, pswd, name, role, email) VALUES (?, ?, ?, ?, ?)";
                
                state = connect.prepareStatement(SQL);
                state.setString(1, user.getUsrn());
                state.setString(2, user.getPswd());
                state.setString(3, user.getName());
                state.setInt(4, user.getRole());
                state.setString(5, user.getName());
                
                
            } else {
                SQL = "UPDATE user SET usrn=?, pswd=?, name=?, role=?, email=? WHERE ID=?";
                state = connect.prepareStatement(SQL);
                state.setString(1, user.getUsrn());
                state.setString(2, user.getPswd());
                state.setString(3, user.getName());
                state.setInt(4, user.getRole());
                state.setString(5, user.getName());
                state.setInt(6, user.getID());
            }
            res = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            return FAIL;
        }
        return res;
    }
    
    @Override
    public int remove(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int deactive(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int active(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadList(JTable tbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
