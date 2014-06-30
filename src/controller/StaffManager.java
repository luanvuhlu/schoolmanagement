/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connectDB.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.FamilyStaff;
import model.Feedback;
import model.Position;
import model.Promotion;
import model.Relative;
import model.Staff;
import model.Student;

/**
 *
 * @author luan
 */
public class StaffManager extends Manager implements StaffMng, DepartmentMng, PositionMng{

    @Override
    public String searchStaff(int No, String name) {

        String SQL = "SELECT * FROM "+
                        "(SELECT staff.No, staff.Name, staff.phone, staff.email, position.name as Position, department.name as Department, staff.gender " +
                        "FROM staff " +
                            "INNER JOIN " +
                                 "promotion ON staff.No=promotion.staff_No " +
                            "INNER JOIN " +
                                "position ON promotion.position_ID=position.ID " +
                            "INNER JOIN " +
                                "department ON position.department_ID=department.ID "+
                            "ORDER BY promotion.date DESC) AS sub "+
                    "WHERE 2>1 ";
        if (name != null && !name.isEmpty()) {
            SQL += " AND name like '%"+name+"%'";
        }
        if (No !=0){
            SQL += " AND No like '"+No+"%'";
        }
        SQL+="GROUP BY No ";
        return SQL;
    }
        
    @Override
    public int addStaff(Staff staff) {
        String SQL;
        List<String> listSQL=new ArrayList();
        if(staff.getBirthday() ==null){
            SQL = "INSERT staff(`name`, birthday, address, phone, email, experience,  education_Background, gender) VALUES("
                + "'" + staff.getName() + "', "
                + "null , "
                + "'" + staff.getAddress() + "', "
                + "'" + staff.getPhone() + "', "
                + "'" + staff.getEmail() + "', "
                + "'" + staff.getExperience() + "', "
                + "'" + staff.getEducation() + "', "
                + staff.getGender()
                + ");";
        }else{
        SQL = "INSERT staff(`name`, birthday, address, phone, email, experience,  education_Background, gender) VALUES("
                + "'" + staff.getName() + "', "
                + "'" + formatDate(staff.getBirthday()) + "', "
                + "'" + staff.getAddress() + "', "
                + "'" + staff.getPhone() + "', "
                + "'" + staff.getEmail() + "', "
                + "'" + staff.getExperience() + "', "
                + "'" + staff.getEducation() + "', "
                + staff.getGender()
                + ");";
        }
        listSQL.add(SQL);
        
        if (staff.getFamilyStaff() != null) {
            SQL = "SET @last_staff_id:=LAST_INSERT_ID();";
            listSQL.add(SQL);
            
            SQL = "INSERT family_staff(married, staff_No) values(" + staff.getFamilyStaff().getMarried() + ", @last_staff_id);";
            listSQL.add(SQL);
            
            SQL = "SET @last_family_staff_id:=LAST_INSERT_ID();";
            listSQL.add(SQL);
            
            if (staff.getFamilyStaff().getRelatives() != null) {
                for (Relative relatives : staff.getFamilyStaff().getRelatives()) {
                    SQL = "INSERT relatives_staff(relation, name, phone, address, occupation, gender, family_Staff_ID) values("
                            + "'" + relatives.getRelation() + "', "
                            + "'" + relatives.getName() + "',"
                            + "'" + relatives.getPhone() + "',"
                            + "'" + relatives.getAddress() + "', "
                            + "'" + relatives.getOccupation() + "',"
                            + relatives.getGender() + ", "
                            + "@last_family_staff_id"
                            + ");";
                    listSQL.add(SQL);
                    
                }
            }
        }
        if (staff.getPromotions() != null && staff.getPromotions().size() > 0) {
            for (Promotion promotion : staff.getPromotions()) {
                SQL = "INSERT promotion(position_ID, staff_No, date) VALUES("
                        + promotion.getPosition().getID() + ", "
                        + "@last_staff_id , "
                        + "'" + formatDate(promotion.getTime()) + "'"
                        + ")";
                listSQL.add(SQL);
            }
        }
        //insert feedback
        return executeManySQL(listSQL);
    }
    
    @Override
    public int editStaff(Staff staff, Staff oldStaff) {
        String SQL;
        List<String> listSQL=new ArrayList();
        SQL = "SET @staff_No=" + staff.getNo();
        listSQL.add(SQL);
        SQL = "UPDATE staff SET "
                + "name='" + staff.getName() + "', "
                + "birthday= '" + formatDate(staff.getBirthday()) + "', "
                + "address= '" + staff.getAddress() + "', "
                + "phone= '" + staff.getPhone() + "', "
                + "email= '" + staff.getEmail() + "', "
                + "experience= '" + staff.getExperience() + "', "
                + "education_Background='" + staff.getEducation() + "', "
                + "gender="+staff.getGender()
                + " ;";
        listSQL.add(SQL); 
        //Family Infomation
        if (staff.getFamilyStaff() != null) {
            if (staff.getFamilyStaff().getID() == 0) {
                SQL = "INSERT family_staff(married, staff_No) values(" + staff.getFamilyStaff().getMarried() + ", @staff_No);";
            } else {
                SQL = "UPDATE family_staff SET married=" + staff.getFamilyStaff().getMarried() + " WHERE staff_No=@staff_No;";
            }
            listSQL.add(SQL);
            System.out.println(SQL);
            
            if (staff.getFamilyStaff().getID() == 0) {
                SQL = "SET @family_staff_id=LAST_INSERT_ID();";
            } else {
                SQL = "SET @family_staff_id:=(SELECT ID FROM family_staff WHERE staff_No=@staff_No);";                
            }
            listSQL.add(SQL);
            
            if (staff.getFamilyStaff().getRelatives() != null) {
                for (Relative relatives : staff.getFamilyStaff().getRelatives()) {
                    if (relatives.getID() == 0) {
                        SQL = "INSERT relatives_staff(relation, name, phone, address, occupation, gender, family_Staff_ID) values("
                                + "'" + relatives.getRelation() + "', "
                                + "'" + relatives.getName() + "',"
                                + "'" + relatives.getPhone() + "',"
                                + "'" + relatives.getAddress() + "', "
                                + "'" + relatives.getOccupation() + "',"
                                + relatives.getGender() + ", "
                                + "@family_staff_id"
                                + ");";
                    } else {
                        SQL = "UPDATE relatives_staff SET "
                                + "relation='" + relatives.getRelation() + "', "
                                + "name='" + relatives.getName() + "',"
                                + "phone='" + relatives.getPhone() + "',"
                                + "address='" + relatives.getAddress() + "', "
                                + "occupation='" + relatives.getOccupation() + "',"
                                + "gender=" + relatives.getGender()
                                + " WHERE ID=" + relatives.getID()
                                + ";";
                    }
                    listSQL.add(SQL);
                    for(Relative re:oldStaff.getFamilyStaff().getRelatives()){
                        boolean flag=false;
                        for(Relative relative:staff.getFamilyStaff().getRelatives()){
                            if(re.getID()==relative.getID()){
                                flag=true;
                                break;
                            }
                        }
                        if(!flag){
                            SQL="DELETE FROM relatives_staff WHERE ID="+re.getID();
                            listSQL.add(SQL);
                        }
                    }
                    
                }
            }
        }
        //Promotion Infomation
        if(staff.getPromotions() !=null && staff.getPromotions().size() >0){
            List<Promotion> promotions=staff.getPromotions();
            for(Promotion p:promotions){
                if(p.getID()==0){
                    if(p.getTime()==null){
                        SQL="INSERT promotion(position_ID, staff_No, date) VALUES ("
                            + p.getPosition().getID()+", "
                            + "@staff_No, "
                            + "null"
                            + ");";
                    }else{
                    SQL="INSERT promotion(position_ID, staff_No, date) VALUES ("
                            + p.getPosition().getID()+", "
                            + "@staff_No, "
                            + "'"+formatDate(p.getTime())+"'"
                            + ");";
                    }
                }else{
                    SQL="UPDATE promotion SET "
                            + "position_ID="+p.getPosition().getID()+", "
                            + "staff_No=@staff_No"+", "
                            + "date='"+formatDate(p.getTime())+"' "
                            + " WHERE ID="+p.getID();
                }
                listSQL.add(SQL);
            }
            for(Promotion p:oldStaff.getPromotions()){
                boolean flag=false;
                for(Promotion pro:staff.getPromotions()){
                    if(p.getID()==pro.getID()){
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    SQL="DELETE FROM promotion WHERE ID="+p.getID();
                    listSQL.add(SQL);
                }
            }
        }
        //Feedback Infomation
//        if(staff.getFeedbacks() !=null && staff.getFeedbacks().size()>0){
//            List<Feedback> feedbacks=staff.getFeedbacks();
//            for(Feedback f:feedbacks){
//                if(f.getID()==0){
//                    SQL="INSERT Feedback(staff_No, content, time, student_ID)";
//                }else{
//                    SQL="UPDATE WHERE ID="+f.getID();
//                }
//            }
//        }      
        return executeManySQL(listSQL);
    }
    
    @Override
    public int removeStaff(int ID) {
        String SQL;
        List<String> listSQL=new ArrayList();
        
        SQL = "SET @staff_No:=" + ID + ";";
        listSQL.add(SQL);
        
        SQL = "SET @family_Staff:=(SELECT ID FROM family_Staff WHERE staff_No=@staff_No);";
        listSQL.add(SQL);

        //Delete relatives_staff
        SQL = "DELETE FROM relatives_staff WHERE family_Staff_ID=@family_Staff;";
        listSQL.add(SQL);

        //Delete family_Staff
        SQL = "DELETE FROM family_Staff WHERE ID=@family_Staff;";
        listSQL.add(SQL);

        //Delete promotion_has_staff
        SQL = "DELETE FROM promotion_has_staff WHERE staff_No=@staff_No;";
        listSQL.add(SQL);

        //Delete feedback
        SQL = "DELETE FROM feedback WHERE staff_No=@staff_No;";
        listSQL.add(SQL);

        //Delete staff
        SQL = "DELETE FROM staff WHERE No=@staff_No;";
        listSQL.add(SQL);
        
        return executeManySQL(listSQL);
    }
    
    
    @Override
    public Staff getDetailStaff(int No) {
        Staff staff = new Staff(No);
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
                staff = new Staff(res.getInt(++count), res.getString(++count), parseDate(res.getString(++count)), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count));
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
                staff.setFamilyStaff(familyStaff);
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
                staff.addPromotion(promotion);
            }
            //Load Feedback
            SQL="SELECT feedback.ID, student.ID, student.academic_register_No, student.name, feedback.content, feedback.date "
                    + "FROM feedback inner join student ON feedback.student_ID=student.ID "
                    + "WHERE feedback.staff_No="+No;
            System.out.println(SQL);
            res=state.executeQuery(SQL);
            List<Feedback> feedbacks=new ArrayList<>();
            staff.setFeedbacks(feedbacks);
            while(res.next()){
                Feedback f=new Feedback(res.getInt(1), new Student(res.getInt(2), res.getInt(3), res.getString(4)), res.getString(5), parseDate(res.getString(6)));
                staff.addFeedback(f);
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
        return staff;
    }
    
    @Override
    public String getListStaff(){
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
        System.out.println(SQL);
        return SQL;
    }

    @Override
    public int deactiveStaff(int ID) {
        String SQL="UPDATE staff SET status=0 WHERE No="+ID;
        return executeUpdate(SQL);
    }
       @Override
    public int deactiveDepartment(int ID) {
        String SQL="UPDATE department SET status=0 WHERE ID="+ID;
        int res=executeUpdate(SQL);
        return res;
    }

    @Override
    public int editDepartment(String[] data) {
        String SQL="UPDATE department SET "
                + "name='"+data[1]+"' "
                + "WHERE ID="+data[0];
        int res=executeUpdate(SQL);
        return res;
    }

    @Override
    public int addDepartment(String[] data) {
        String SQL="INSERT department(name) VALUES('"+data[0]+"')"; 
        int res=executeUpdate(SQL);
        return res;
    }
    
    @Override
    public int addPosition(String[] data) {
        List<String> listSQL=new ArrayList<>();
        listSQL.add("SET @idDepartment:=(SELECT ID FROM department where name='"+data[1]+"');");
        listSQL.add("INSERT position (name, department_ID) VALUES ('"+data[0]+"', @idDepartment);");
        return executeManySQL(listSQL);
    }

    @Override
    public int deactivePosition(int ID) {
        String SQL="UPDATE position SET status=0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }

    @Override
    public int editPosition(String[] data) {
        List<String> listSQL=new ArrayList<>();
        listSQL.add("SET @idDepartment:=(SELECT ID FROM department where name='"+data[2]+"');");
        listSQL.add("UPDATE position SET name='"+data[1]+"', department_ID=@idDepartment WHERE ID="+data[0]+";");
        System.out.println(listSQL);
        return executeManySQL(listSQL);
    }


    @Override
    public String removePosition(int ID) {
        String SQL ="";
        SQL="SELECT 1 FROM promotion WHERE position_ID="+ID;
        if(getIDEdentity(SQL)==-1){
            SQL="DELETE FROM position WHERE ID=" + ID;
        }else{
            return "Have some staff have this position. You must remove promotion in staff info";
        }
        int res=executeUpdate(SQL);
        if(res==CONNECT_FAIL){
            return "Connect fail ! Please try again !";
        }
        if(res==FAIL){
            return "Have some error !";
        }
        if(res==SUCCESS){
            return "Success !";
        }
        return "Unknown !";
    }

    @Override
    public String removeDepartment(int ID) {
        String SQL ="";
        SQL="SELECT 1 FROM position WHERE department_ID="+ID;
        System.out.println(SQL);
        if(getIDEdentity(SQL)==-1){
            SQL="DELETE FROM department WHERE ID=" + ID;
        }else{
            return "Have some positon have this department. You must remove or change position";
        }
        
        int res=executeUpdate(SQL);
        if(res==CONNECT_FAIL){
            return "Connect fail ! Please try again !";
        }
        if(res==FAIL){
            return "Have some error !";
        }
        if(res==SUCCESS){
            return "Success !";
        }
        return "Unknown !";
    }
 
}
