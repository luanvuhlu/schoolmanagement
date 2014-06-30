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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.*;

/**
 *
 * @author luan
 */
public class PositionEntity extends Manager implements IEntity<Position>{
    private Position position;
    private DepartmentEntity departmentEntity=new DepartmentEntity();

    public void loadList(JTable table){
        String SQL="SELECT position.ID, position.name, department.name "
                + "FROM position, department "
                + "WHERE position.department_ID=department.ID AND position.status=1";
     loadTable(SQL, table, POSITION_MODEL);
    }
    public List<String> getNameList(String department){
        List<String> list=new ArrayList<>();
        String SQL="SELECT position.name FROM position, department "
                + "WHERE position.department_ID=department.ID AND department.name=?";
        
        Connection connect=null;
        connect=ConnectDB.getConnect();
        if(connect==null){
            return null;
        }
        try {
            PreparedStatement state=connect.prepareStatement(SQL);
            state.setString(1, department);
            ResultSet res=state.executeQuery();
            while(res.next()){
                list.add(res.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionEntity.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PositionEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return list;
    }
    @Override
    public Position findByID(int ID) {
        Position p = null;
        String SQL = "SELECT ID, name, department_ID FROM batch WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                p = new Position();
                p.setID(res.getInt(1));
                p.setName(res.getString(2));
                p.setDepartment(departmentEntity.findByID(res.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Position findByName(String name, int department) {
        Position p = null;
        PreparedStatement state = null;

        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        String SQL = "SELECT ID, name, department_ID FROM position WHERE name=? AND department_ID=?";
        ResultSet res = null;
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, name);
            state.setInt(2, department);
            res = state.executeQuery();
            if (res.next()) {
                p = new Position();
                p.setID(res.getInt(1));
                p.setName(res.getString(2));
                p.setDepartment(departmentEntity.findByID(res.getInt(3)));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    public void setPosition(Position p){
        position=p;
    }

    @Override
    public int save() {
        if (position == null) {
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
        if (position.getID()== 0) {
            SQL = "INSERT position (name, department_ID) VALUES (?, ?)";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, position.getName());
                state.setInt(2, position.getDepartment().getID());
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            SQL = "UPDATE position SET name=? AND department_ID=? WHERE ID=?";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, position.getName());
                state.setInt(2, position.getDepartment().getID());
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
        String SQL = "DELETE FROM position WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE position SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE position SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public Position findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
