/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import static controller.Manager.POSITION_MODEL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Subject;

/**
 *
 * @author luan
 */
public class SubjectEntity extends Manager implements IEntity<Subject>{
    private Subject subject;
    
    public void loadList(JTable table){
        String SQL="SELECT ID, name FROM subject WHERE status=1 ";
     loadTable(SQL, table, SUBJECT_MODEL);
    }
    public List<String> getNameList(){
        List<String> list=new ArrayList<>();
        String SQL="SELECT name FROM subject";
        Connection connect=null;
        connect=ConnectDB.getConnect();
        if(connect==null){
            return null;
        }
        try {
            ResultSet res=connect.createStatement().executeQuery(SQL);
            while(res.next()){
                list.add(res.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BatchEntity.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(BatchEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return list;
    }
    @Override
    public Subject findByID(int ID) {
        Subject s = null;
        String SQL = "SELECT ID, name FROM subject WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                s = new Subject();
                s.setID(res.getInt(1));
                s.setName(res.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    @Override
    public Subject findByName(String name) {
        Subject s=null;
        PreparedStatement state = null;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        String SQL = "SELECT ID, name FROM subject WHERE name=?";
        ResultSet res = null;
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, name);
            res = state.executeQuery();
            if (res.next()) {
                s = new Subject();
                s.setID(res.getInt(1));
                s.setName(res.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    public void setSubject(Subject s){
        subject=s;
    }

    @Override
    public int save() {
        if (subject == null) {
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
        if (subject.getID()== 0) {
            SQL = "INSERT subject (name) VALUES (?)";
            
                state = connect.prepareStatement(SQL);
                state.setString(1, subject.getName());

        } else {
            SQL = "UPDATE batch SET name=? WHERE ID=?";
                state = connect.prepareStatement(SQL);
                state.setString(1, subject.getName());
                state.setInt(2, subject.getID());
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
        String SQL = "DELETE FROM subject WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE subject SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE subject SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }
}
