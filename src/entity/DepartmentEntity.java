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
import model.Department;

/**
 *
 * @author luan
 */
public class DepartmentEntity extends Manager implements IEntity<Department>{
    private Department department;

    public void loadList(JTable table){
        String SQL="SELECT ID, name FROM department WHERE status=1";
     loadTable(SQL, table, DEPARTMENT_MODEL);
    }
    public void search(String name, JTable tbl){
        Connection connection=ConnectDB.getConnect();
        PreparedStatement state=null;
        String SQL="SELECT ID, name FROM department WHERE status=1 ";
        try {
            state=connection.prepareStatement(SQL);
            if(!name.isEmpty()){
            SQL+="AND name=?";
            state.setString(1, name);
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentEntity.class.getName()).log(Level.SEVERE, null, ex);
        } 
        loadTable(state, tbl, STAFF_MODEL);
    }
    public List<String> getNameList(){
        List<String> list=new ArrayList<>();
        String SQL="SELECT name FROM department";
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
            Logger.getLogger(DepartmentEntity.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return list;
    }
    @Override
    public Department findByID(int ID) {
        Department d = null;
        String SQL = "SELECT ID, name FROM department WHERE ID=" + ID;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        try {
            ResultSet res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                d=new Department();
                d.setID(res.getInt(1));
                d.setName(res.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    @Override
    public Department findByName(String name) {
        Department d = null;
        PreparedStatement state = null;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        String SQL = "SELECT ID, name FROM department WHERE name=?";
        ResultSet res = null;
        try {
            state = connect.prepareStatement(SQL);
            state.setString(1, name);
            res = state.executeQuery();
            if (res.next()) {
                d=new Department();
                d.setID(res.getInt(1));
                d.setName(res.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    public void setDepartment(Department d){
        department=d;
    }

    @Override
    public int save() {
        if (department == null) {
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
        if (department.getID()== 0) {
            SQL = "INSERT department (name) VALUES (?)";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, department.getName());
            } catch (SQLException ex) {
                Logger.getLogger(AcademicEntity.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            SQL = "UPDATE department SET name=? WHERE ID=?";
            try {
                state = connect.prepareStatement(SQL);
                state.setString(1, department.getName());
                state.setInt(2, department.getID());
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
        String SQL = "DELETE FROM department WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE department SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE department SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }
    
    
}
