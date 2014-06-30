/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author luan
 */
public class BatchEntity extends Manager implements IEntity<Batch>{
    private Batch batch;
    
    public void loadList(JTable table){
        String SQL="SELECT ID, name, date FROM batch WHERE status=1 ";
     loadTable(SQL, table, BATCH_MODEL);
    }
    public List<String> getNameList(){
        List<String> list=new ArrayList<>();
        String SQL="SELECT name FROM batch";
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
    public Batch findByID(int ID) {
        Batch b = null;
        String SQL = "SELECT ID, name, date FROM batch WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                b = new Batch();
                b.setID(res.getInt(1));
                b.setName(res.getString(2));
                java.util.Date d=null;
                try{
                    d=new java.util.Date(res.getDate(3).getTime());
                }catch(NullPointerException ex){ex.printStackTrace();}
                b.setTime(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    @Override
    public Batch findByName(String name) {
        Batch b = null;
        PreparedStatement state = null;

        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        String SQL = "SELECT ID, name, date FROM batch WHERE name=?";
        java.util.Date date=null;
        ResultSet res = null;
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, name);
            res = state.executeQuery();
            if (res.next()) {
                b = new Batch();
                b.setID(res.getInt(1));
                b.setName(res.getString(2));
                try{
                    date=new java.util.Date(res.getDate(3).getTime());
                }catch(NullPointerException ex){}
                b.setTime(date);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    public void setBatch(Batch b){
        batch=b;
    }

    @Override
    public int save() {
        if (batch == null) {
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
        if (batch.getID()== 0) {
            SQL = "INSERT batch (name, date) VALUES ( ?, ?)";
                state = connect.prepareStatement(SQL);
                state.setString(1, batch.getName());
                Date date=null;
                try{
                    date=new Date(batch.getTime().getTime());
                }catch(NullPointerException ex){}
                state.setDate(2, date);

        } else {
            SQL = "UPDATE batch SET name=?, date=? WHERE ID=?";

                state = connect.prepareStatement(SQL);
                state.setString(1, batch.getName());
                state.setDate(2, new Date(batch.getTime().getTime()));
                state.setInt(3, batch.getID());

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
        String SQL = "DELETE FROM batch WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE batch SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE batch SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }
    
    
}
