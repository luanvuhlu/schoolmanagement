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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.*;

/**
 *
 * @author luan
 */
public class AcademicEntity extends Manager implements IEntity<Academic> {
    private Academic academic;
    private BatchEntity batchEntity=new BatchEntity();

    @Override
    public Academic findByID(int ID) {
        Academic a = null;
        String SQL = "SELECT register_no, batch_ID FROM academic WHERE register_no=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                a = new Academic(res.getInt(1), batchEntity.findByID(res.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public Academic findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setAcademic(Academic a) {
        this.academic = a;
    }

    @Override
    public int save() {
        if (academic == null) {
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
        if (academic.getRegisterNo() == 0) {
            SQL = "INSERT academic (batch_ID) VALUES (?)";
            try {
                state = connect.prepareStatement(SQL);
                state.setInt(1, academic.getBatch().getID());
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            SQL = "UPDATE academic SET batch_ID=? WHERE register_no=?";
            try {
                state = connect.prepareStatement(SQL);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (academic.getBatch() == null) {
                return FAIL;
            }
            if (academic.getBatch().getID() == 0) {
                academic.setBatch(batchEntity.findByName(academic.getBatch().getName()));
            }
            if (academic.getBatch() == null) {
                return FAIL;
            }
            try {
                state.setInt(1, academic.getBatch().getID());
                state.setInt(2, academic.getRegisterNo());
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
        String SQL = "DELETE FROM academic WHERE register_No=" + ID;
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
