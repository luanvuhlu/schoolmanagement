/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import static controller.Manager.CONNECT_FAIL;
import static controller.Manager.FAIL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import javax.swing.JTable;

/**
 *
 * @author luan
 */
public class MarkEntity extends Manager implements IEntity<Mark>{
    private Mark mark;
    
    public void search(){
        
    }
    @Override
    public void loadList(JTable tbl) {
        String SQL = "SELECT mark.ID, subject.name, student.No, student.name, mark.point, mark.date FROM mark "
                + "LEFT JOIN student ON mark.student_ID=student.ID  "
                + "LEFT JOIN subject ON mark.subject_ID=subject.ID";
        
     loadTable(SQL, tbl, MARK_MODEL);
    }

    @Override
    public Mark findByID(int ID) {
        Mark m=null;
        Connection connect=null;
        connect=ConnectDB.getConnect();
        if(connect==null){
            return null;
        }
        String SQL="SELECT ID, subject_ID, point, date, student_ID FROM mark WHERE ID="+ID;
        ResultSet res=null;
        try {
            res=connect.createStatement().executeQuery(SQL);
            if(res.next()){
                m=new Mark();
                m.setID(res.getInt(1));
                Subject s=new SubjectEntity().findByID(res.getInt(2));
                m.setSubject(s);
                m.setPoint(res.getInt(3));
                Date d=null;
                try{
                d=new Date(res.getDate(4).getTime());
                }catch(NullPointerException ex){}
                m.setTime(d);
                m.setStudent(new StudentEntity().findByID(res.getInt(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @Override
    public Mark findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setMark(Mark m){
        mark=m;
    }

    @Override
    public int save() {
        if (mark == null) {
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
        if (mark.getID()== 0) {
            SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES (?, ?, ?, ?)";
                state = connect.prepareStatement(SQL);
                state.setInt(1, mark.getSubject().getID());
                state.setInt(2, mark.getPoint());
                java.sql.Date d=null;
                try{
                    d=new java.sql.Date(mark.getTime().getTime());
                    
                }catch(NullPointerException ex){}
                state.setDate(3, d);
                state.setInt(4, mark.getStudent().getID());
                

        } else {
            SQL = "UPDATE mark SET subject_ID=?, point=?, date=?, student_ID=? WHERE ID=?";
                state = connect.prepareStatement(SQL);
                state.setInt(1, mark.getSubject().getID());
                state.setInt(2, mark.getPoint());
                java.sql.Date d=null;
                try{
                    d=new java.sql.Date(mark.getTime().getTime());
                    
                }catch(NullPointerException ex){}
                state.setDate(3, d);
                state.setInt(4, mark.getStudent().getID());
                state.setInt(5, mark.getID());
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
        String SQL = "DELETE FROM mark WHERE ID=" + ID;
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
    
}
