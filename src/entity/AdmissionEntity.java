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
public class AdmissionEntity extends Manager implements IEntity<Admission>{
    private Admission admission;
    @Override
    public Admission findByID(int ID) {
        Admission a = null;
        String SQL = "SELECT ID, prev_Institute, reason_Leaving, date FROM admission WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                a = new Admission(res.getInt(1), res.getString(2), res.getString(3), new java.util.Date(res.getDate(4).getTime()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public Admission findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setAdmission(Admission a){
        admission=a;
    }

    @Override
    public int save() {
        if (admission == null) {
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
        if (admission.getID()== 0) {
            SQL = "INSERT admiddsion (prev_Institute, reason_Leaving, date) VALUES (?, ?, ?, ?)";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, admission.getPrev_Institute());
                state.setString(2, admission.getReason_Leaving());
                Date date=null;
                try{
                    date=new Date(admission.getTime().getTime());
                }catch(NullPointerException ex){}
                state.setDate(3, date);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            SQL = "UPDATE admiddsion SET prev_Institute=? AND reason_Leaving=? AND DATE=? WHERE id=?";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, admission.getPrev_Institute());
                state.setString(2, admission.getReason_Leaving());
                state.setDate(3, new Date(admission.getTime().getTime()));
                state.setInt(4, admission.getID());
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            res = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public int remove(int ID) {
        String SQL = "DELETE FROM admission WHERE register_No=" + ID;
        return executeUpdate(SQL);
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
