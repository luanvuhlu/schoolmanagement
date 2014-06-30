/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import static controller.Manager.CONNECT_FAIL;
import controller.StaffManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Department;
import model.FamilyStaff;
import model.Promotion;
import model.Feedback;
import model.Position;
import model.Relative;
import model.Staff;

public class StaffEntity extends Manager implements IEntity<Staff>{
    private Staff staff;
    private PositionEntity positionEntity=new PositionEntity();
    private List<Integer> ID_removeRelative=new ArrayList<>();
    private List<Integer> ID_removePromotion=new ArrayList<>();
    private List<Integer> ID_removeFeedback=new ArrayList<>();

    
    
    public void loadList(JTable table){
        String SQL = "SELECT * FROM "+
                        "(SELECT staff.No, staff.Name, staff.phone, staff.email, position.name as Position, department.name as Department, staff.gender " +
                        "FROM staff " +
                            "LEFT JOIN " +
                                 "promotion ON staff.No=promotion.staff_No " +
                            "LEFT JOIN " +
                                "position ON promotion.position_ID=position.ID " +
                            "LEFT JOIN " +
                                "department ON position.department_ID=department.ID "+
                            "WHERE staff.status=1 "+
                            "ORDER BY promotion.date DESC) AS sub "+
                        "GROUP BY No ";
     loadTable(SQL, table, STAFF_MODEL);
    }
    public void search(int No, String name, int department, int position, JTable tbl ){
        String SQL = "SELECT * FROM "+
                        "(SELECT staff.No, staff.Name, staff.phone, staff.email, position.name as Position, department.name as Department, staff.gender " +
                        "FROM staff " +
                            "LEFT JOIN " +
                                 "promotion ON staff.No=promotion.staff_No " +
                            "LEFT JOIN " +
                                "position ON promotion.position_ID=position.ID " +
                            "LEFT JOIN " +
                                "department ON position.department_ID=department.ID "+
                            "WHERE staff.status=1 ";
        int count=1;
        if(No !=0){
            SQL+=" AND staff.No=?";
        }
        if(!name.isEmpty()){
            SQL+=" AND staff.name like ?";
        }
        if(department !=0){
            SQL+=" AND department.ID=?";
        }
        if(position !=0){
            SQL+=" AND position.ID=?";
        }
        SQL+=" ORDER BY promotion.date DESC) AS sub GROUP BY No ";
        Connection connection=ConnectDB.getConnect();
        try {
            PreparedStatement state=connection.prepareStatement(SQL);
            if(No !=0){
            state.setInt(count++, No);
        }
        if(!name.isEmpty()){
            state.setString(count++, "%"+name+"%");
        }
        if(department !=0){
            state.setInt(count++, department);
        }
        if(position !=0){
            state.setInt(count++, position);
        }
        loadTable(state, tbl, STAFF_MODEL);
        } catch (SQLException ex) {
            Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public Staff findByID(int No) {
        Staff s = null;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }
        Statement state = null;
        ResultSet res = null;
        String SQL;
        // Load Person detail, education and experience
        SQL = "SELECT No, name, birthday,address, phone, email, education_Background,experience, gender FROM staff WHERE No=" + No;
        try {
            state = connect.createStatement();
            res = state.executeQuery(SQL);
            int count = 0;
            if (res.next()) {
                s = new Staff(res.getInt(++count), res.getString(++count), parseDate(res.getString(++count)), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count));
            } else {
                return null;
            }
            // Load Family
            SQL = "SELECT ID, married FROM family_staff WHERE staff_No=" + No;
            res = state.executeQuery(SQL);
            FamilyStaff familyStaff = null;
            if (res.next()) {
                familyStaff = new FamilyStaff(res.getInt(1), res.getInt(2));
            }
            if (familyStaff != null) {
                SQL = "SELECT ID, relation, name, phone, address, occupation, gender FROM relatives_staff WHERE family_Staff_ID=" + familyStaff.getID();
                res = state.executeQuery(SQL);
                while (res.next()) {
                    count = 0;
                    Relative relatives = new Relative(res.getInt(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count));
                    familyStaff.addRelative(relatives);
                }
                s.setFamilyStaff(familyStaff);
            }
            // Load Promotion
            SQL = "SELECT promotion.ID, promotion.position_ID, position.name, position.department_ID,department.name, promotion.date "
                    + "FROM promotion inner join position ON promotion.position_ID=position.ID "
                    + "INNER JOIN department "
                    + "ON department.ID=position.ID "
                    + "WHERE promotion.staff_No=" + No;
            res = state.executeQuery(SQL);
            while (res.next()) {
                Department department=new Department(res.getInt(4), res.getString(5));
                Position position=new Position(res.getInt(2), res.getString(3), department);
                Promotion promotion=new Promotion(res.getInt(1), position, parseDate(res.getString(6)));
                s.addPromotion(promotion);
            }
            //Load Feedback
            SQL="SELECT feedback.ID, student.ID, student.academic_register_No, student.name, feedback.content, feedback.date "
                    + "FROM feedback inner join student ON feedback.student_ID=student.ID "
                    + "WHERE feedback.staff_No="+No;
            
            res=state.executeQuery(SQL);
            List<Feedback> feedbacks=new ArrayList<>();
            s.setFeedbacks(feedbacks);
            while(res.next()){
                Feedback f=new Feedback(res.getInt(1), new model.Student(res.getInt(2), res.getInt(3), res.getString(4)), res.getString(5), parseDate(res.getString(6)));
                s.addFeedback(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    @Override
    public Staff findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setStaff(Staff s){
        staff=s;
    }

    @Override
    public int save() {
        if(staff==null){
            return FAIL;
        }
        String SQL="";
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return CONNECT_FAIL;
        }
        PreparedStatement state=null;
        try {
            connect.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date date=null;
        try {
        if(staff.getNo()==0){
            SQL="INSERT staff(`name`, birthday, address, phone, email, experience,  education_Background, gender) VALUES(?, ?, ?, ?, ?,?, ?, ?);";
            state=connect.prepareStatement(SQL);
            state.setString(1, staff.getName());
            try{
                date=new Date(staff.getBirthday().getTime());
            }catch(NullPointerException ex){ex.printStackTrace();}
            state.setDate(2, date);
            date=null;
            state.setString(3, staff.getAddress());
            state.setString(4, staff.getPhone());
            state.setString(5, staff.getEmail());
            state.setString(6, staff.getExperience());
            state.setString(7, staff.getEducation());
            state.setInt(8, staff.getGender());
            state.executeUpdate();                   

            SQL = "SET @ID_STAFF:=LAST_INSERT_ID();";
            state=connect.prepareStatement(SQL);
            state.executeUpdate();                              
        }else{
            SQL = "UPDATE staff SET "
                    + "name=? ,"
                    + "birthday=? ,"
                    + "address=? ,"
                    + "phone=? ,"
                    + "email=? ,"
                    + "experience=? ,"
                    + "education_Background=? ,"
                    + "gender=? "
                    + "WHERE No=?; ";
            state = connect.prepareStatement(SQL);
            state.setString(1, staff.getName());
            try {
                date = new Date(staff.getBirthday().getTime());
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            state.setDate(2, date);
            date = null;
            state.setString(3, staff.getAddress());
            state.setString(4, staff.getPhone());
            state.setString(5, staff.getEmail());
            state.setString(6, staff.getExperience());
            state.setString(7, staff.getEducation());
            state.setInt(8, staff.getGender());
            state.setInt(9, staff.getNo());
            state.executeUpdate();                   

            SQL = "SET @ID_STAFF:=" + staff.getNo() + ";";
            state = connect.prepareStatement(SQL);
            state.executeUpdate();                    
            } 
        if(staff.getFamilyStaff() !=null){
            if(staff.getFamilyStaff().getID()==0){
                SQL="INSERT family_staff (married, staff_No) VALUES (?, @ID_STAFF);";
                state=connect.prepareStatement(SQL);
                state.setInt(1, staff.getFamilyStaff().getMarried());
                state.executeUpdate();                   
                SQL="SET @ID_FAMILY:=LAST_INSERT_ID();";
                state=connect.prepareStatement(SQL);
                state.executeUpdate();                   
            }else{
                SQL="UPDATE family_staff SET married=? WHERE ID=?;";
                state=connect.prepareStatement(SQL);
                state.setInt(1, staff.getFamilyStaff().getMarried());
                state.setInt(2, staff.getFamilyStaff().getID());
                state.executeUpdate();                   
                SQL="SET @ID_FAMILY:="+staff.getFamilyStaff().getID()+";";
                state=connect.prepareStatement(SQL);
                state.executeUpdate();                   
            }
            if (staff.getFamilyStaff().getRelatives().size()>0) {
            for(Relative r:staff.getFamilyStaff().getRelatives()){
                if(r.getID()==0){
                    SQL="INSERT relatives_staff (relation, name, phone, address, occupation, gender, family_Staff_ID) VALUES(?, ?, ?, ?, ?, ?, @ID_FAMILY);";
                    state=connect.prepareStatement(SQL);
                    state.setString(1, r.getRelation());
                    state.setString(2, r.getName());
                    state.setString(3, r.getPhone());
                    state.setString(4, r.getAddress());
                    state.setString(5, r.getOccupation());
                    state.setInt(6, r.getGender());
                    state.executeUpdate();                   
                }else{
                    SQL="UPDATE relatives_staff SET relation=? , name=? , phone=? , address=? , occupation=? , gender=? WHERE family_Staff_ID=@ID_FAMILY;";
                    state=connect.prepareStatement(SQL);
                    state.setString(1, r.getRelation());
                    state.setString(2, r.getName());
                    state.setString(3, r.getPhone());
                    state.setString(4, r.getAddress());
                    state.setString(5, r.getOccupation());
                    state.setInt(6, r.getGender());
                    state.executeUpdate();                   
                    
                }
            }
        }
        }
        
        if(staff.getPromotions().size() >0){
            for(Promotion p:staff.getPromotions()){
                if(p.getID()==0){
                    SQL="INSERT promotion (position_ID, date, staff_No) VALUES (?, ?, @ID_STAFF);";
                    state=connect.prepareStatement(SQL);
                    state.setInt(1, p.getPosition().getID());
                    try{
                        date=new Date(p.getTime().getTime());
                    }catch(NullPointerException ex){ex.printStackTrace();}
                    state.setDate(2, date);
                    date=null;
                    state.executeUpdate();                    
                }else{
                   SQL="UPDATE promotion SET position_ID=?, date=? WHERE ID=?;";
                    state=connect.prepareStatement(SQL);
                    state.setInt(1, p.getPosition().getID());
                    try{
                        date=new Date(p.getTime().getTime());
                    }catch(NullPointerException ex){ex.printStackTrace();}
                    state.setDate(2, date);
                    date=null;
                    state.setInt(3, p.getID());
                    state.executeUpdate();                    
                    
                }
            }
        }
        if(staff.getFeedbacks().size()>0){
            for(Feedback f:staff.getFeedbacks()){
                if(f.getID()==0){
                    SQL="INSERT feedback (content, date, status, student_ID, staff_No) VALUES (?, ?, ?, ?, @ID_STAFF);";
                    state=connect.prepareStatement(SQL);
                    state.setString(1, f.getContent());
                    try{
                        date=new Date(f.getTime().getTime());
                    }catch(NullPointerException ex){ex.printStackTrace();}
                    state.setDate(2, date);
                    date=null;
                    state.setInt(3, f.getStatus());
                    state.setInt(4, f.getStudent().getID());
                    state.executeUpdate();                   
                }else{
                    SQL="UPDATE feedback SET content=? , date=? , status=?, student_ID=?, staff_No=@ID_STAFF WHERE ID=?";
                    state=connect.prepareStatement(SQL);
                    state=connect.prepareStatement(SQL);
                    state.setString(1, f.getContent());
                    try{
                        date=new Date(f.getTime().getTime());
                    }catch(NullPointerException ex){ex.printStackTrace();}
                    state.setDate(2, date);
                    date=null;
                    state.setInt(3, f.getStatus());
                    state.setInt(4, f.getStudent().getID());
                    state.setInt(5, f.getID());
                    state.executeUpdate();                   
                }
            }
        }
        if(staff.getNo() !=0){
            for(int i:ID_removeFeedback){
                SQL="DELETE FROM feedback WHERE ID="+i+";";
                state=connect.prepareStatement(SQL);
                state.executeUpdate();                   
            }
            ID_removeFeedback.clear();
            for(int i:ID_removePromotion){
                SQL="DELETE FROM promotion WHERE ID="+i+";";
                state=connect.prepareStatement(SQL);
                state.executeUpdate();                   
            }
            ID_removePromotion.clear();
            for(int i:ID_removeRelative){
                SQL="DELETE FROM relatives_staff WHERE ID="+i+";";
                state=connect.prepareStatement(SQL);
                state.executeUpdate();                   
            }
            ID_removeRelative.clear();
        }       
        connect.commit();
        }catch (SQLException ex) {
            
                Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex);
                try {
                connect.rollback();
                    System.out.println("Da RollBack");
                return FAIL;
            } catch (SQLException ex1) {
                Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return SUCCESS;
    }

    @Override
    public int remove(int No) {
        String SQL = "DELETE FROM staff WHERE No=" + No;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int No) {
        String SQL = "UPDATE staff SET status=0 WHERE No=" + No;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int No) {
        String SQL = "UPDATE staff SET status=1 WHERE No=" + No;
        return executeUpdate(SQL);
    }
    public void removePromotion(int ID){
        ID_removePromotion.add(ID);
    }
    public void removeRelative(int ID){
        ID_removeRelative.add(ID);
    }
    public void removeFeedback(int ID){
        ID_removeFeedback.add(ID);
    }
    
}
