/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connectDB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luan
 */
public class Authentication {
    private int ID;
    private int role;
    private String name;
    
    public static int CONNECT_FAIL=-1;
    public static int NOT_AUTH=0;
    public static int AUTH=1;
    public static int ADMIN=1;
    public static int USER=0;
    public static int LOGIN=1;
    public static int LOGOUT=0;
    public static int SUCCESS=1;
    public static int FAIL=0;
    public static int HEADER_ADMIN=1;
    public static int STAFF_MANAGER=2;
    public static int STUDENT_MANAGER=3;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getRole() {
        return role;
    }
    
    public boolean check(){
        if(ID > 0){
            return true;
        }
        return false;
    }

    public Authentication(int ID, int role) {
        this.ID = ID;
        this.role = role;
    }

    public Authentication() {
    }
    public int login(String usrn, String pswd, int role){
        Authentication authentication=null;
        Connection connect=ConnectDB.getConnect();
        if(connect==null){
            return CONNECT_FAIL;
        }
        PreparedStatement state=null;
        ResultSet res=null;
        String SQL="";
        if(role==USER)
            SQL="SELECT ID, name FROM user WHERE usrn=? and pswd=?";
        else if(role==ADMIN){
            SQL="SELECT ID, name FROM admin WHERE username=? and password=?";
        }
        try {
            state=connect.prepareStatement(SQL);
            state.setString(1, usrn);
            state.setString(2, pswd);
            res=state.executeQuery();
            if(res.next()){
                int id_people=res.getInt(1);
                if(role==USER){
                    this.role=getRoleManager(id_people);
                    addAuthTable(id_people, this.role, LOGIN);
                    this.ID=id_people;
                     
                }else if(role==ADMIN){
                     addAuthTable(id_people, HEADER_ADMIN, LOGIN);
                     this.ID=id_people;
                     this.role=HEADER_ADMIN;
                 }
                this.name=res.getString(2);
                return AUTH;
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return NOT_AUTH;
    }
    private int getRoleManager(int ID){
        Connection connect=ConnectDB.getConnect();
        Statement state=null;
        ResultSet res=null;
        try {
            String SQL="SELECT role FROM user where ID="+ID;
            state=connect.createStatement();
            res=state.executeQuery(SQL);
            if(res.next()){
                int role=res.getInt(1);
                return role;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return FAIL;
    }
    private int addAuthTable(int ID,int role, int action){
        Connection connect=ConnectDB.getConnect();
        if(connect==null){
            return CONNECT_FAIL;
        }
        PreparedStatement state=null;
        String SQL="INSERT authentication(ID_People, role, action) values(?, ?, ?)";
        int res=0;
        try {
            state=connect.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            state.setInt(1, ID);
            state.setInt(2, role);
            state.setInt(3, action);
            res=state.executeUpdate();
            if(res==1){
                return SUCCESS;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return FAIL;
    }
    public Timestamp getLastLoin(){
        Timestamp lastLogin=null;
        Connection connect=null;
        PreparedStatement state=null;
        ResultSet res=null;
        if(!check()){
            return null;
        }
        connect=ConnectDB.getConnect();
        if(connect==null){
            return null;
        }
        String SQL="select time from authentication where ID_People=? and action=0 and role=? ORDER BY time DESC limit 1";
        try {
            state=connect.prepareStatement(SQL);
            state.setInt(1, this.ID);
            state.setInt(2, this.role);
            res=state.executeQuery();
            if(res.next()){
               lastLogin=res.getTimestamp(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lastLogin;
    }
    public int logout(){
        if(this.ID <1){
            return FAIL;
        }
        int res=addAuthTable(this.ID, this.role, LOGOUT);
        this.ID=0;
        this.role=-1;
        this.name=null;
        return res;
    }
}
