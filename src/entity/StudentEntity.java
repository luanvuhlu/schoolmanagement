/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import connectDB.ConnectDB;
import controller.Manager;
import controller.StudentManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Admission;
import model.Batch;
import model.ExtraCurricular;
import model.FamilyStudent;
import model.Mark;
import model.Relative;
import model.Remark;
import model.Staff;
import model.Student;
import model.Subject;

/**
 *
 * @author luan
 */
public class StudentEntity extends Manager implements IEntity<Student> {

    private Student student;
    private List<Integer> ID_remove_relative = new ArrayList<>();
    private List<Integer> ID_remove_mark = new ArrayList<>();
    private List<Integer> ID_remove_remark = new ArrayList<>();
    private List<Integer> ID_remove_extraCurricular = new ArrayList<>();

    public void search(int registerNo, String No, String name, int batch, JTable tbl ){
        String SQL = "SELECT student.ID, student.academic_register_No, student.No, student.name,batch.name AS Batch, student.phone, student.email, student.gender "
                + "FROM student LEFT JOIN (academic LEFT JOIN batch on academic.batch_ID=batch.ID) ON academic.register_No=student.academic_register_No "
                + " WHERE student.status=1";
        int count=1;
        if(registerNo !=0){
            SQL+=" AND student.academic_register_No=?";
        }
        if(!name.isEmpty()){
            SQL+=" AND student.name like ?";
        }
        if(!No.isEmpty()){
            SQL+=" AND student.No like ?";
        }
        if(batch !=0){
            SQL+=" AND academic.batch_ID=?";
        }
        
        Connection connection=ConnectDB.getConnect();
        try {
            PreparedStatement state=connection.prepareStatement(SQL);
            if(registerNo !=0){
            state.setInt(count++, registerNo);
        }
        if(!name.isEmpty()){
            state.setString(count++, "%"+name+"%");
        }
        if(!No.isEmpty()){
            state.setString(count++, "%"+No+"%");
        }
        if(batch !=0){
            state.setInt(count++, batch);
        }
        
        loadTable(state, tbl, STUDENT_MODEL);
        } catch (SQLException ex) {
            Logger.getLogger(StudentEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void loadList(JTable table){
        String SQL = "SELECT student.ID, student.academic_register_No, student.No, student.name,batch.name AS Batch, student.phone, student.email, student.gender "
                + "FROM student LEFT JOIN (academic LEFT JOIN batch on academic.batch_ID=batch.ID) ON academic.register_No=student.academic_register_No "
                + " WHERE student.status=1";
     loadTable(SQL, table, STUDENT_MODEL);
    }
    
    @Override
    public Student findByID(int ID) {
        Student s = null;
        int count = 0;
        String SQL;
        Date date = null;
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return null;
        }

        SQL = "SELECT ID, No, academic_register_No, name,birthday, address, phone, email, gender, admission_ID "
                + "FROM student WHERE ID=" + ID;

        ResultSet res = null;
        try {
            res = connect.createStatement().executeQuery(SQL);
            if (res.next()) {
                s = new Student(res.getInt(++count), res.getString(++count), res.getInt(++count), res.getString(++count), parseDate(res.getString(++count)), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count), res.getInt(++count));
            }
            //Load Admission infomation
            if (s.getAdmission() != null) {
                SQL = "SELECT prev_Institute, reason_Leaving, date FROM admission WHERE ID=" + s.getAdmission().getID();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    s.getAdmission().setPrev_Institute(res.getString(1));
                    s.getAdmission().setReason_Leaving(res.getString(2));
                    s.getAdmission().setTime(parseDate(res.getString(3)));
                }
            }
            //Load Academic infomation
            if (s.getAcademic() != null) {
                SQL = "SELECT batch.ID, batch.name, batch.date FROM batch, academic WHERE batch.ID=academic.batch_ID AND academic.register_No=" + s.getAcademic().getRegisterNo();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    s.getAcademic().setBatch(new Batch(res.getInt(1), res.getString(2), parseDate(res.getString(3))));
                }
            }
            //Load Remarks
            SQL = "SELECT remark.ID, remark.content, remark.date, remark.staff_No, staff.name FROM remark, staff WHERE staff.No=remark.staff_No AND student_ID=" + s.getID();
            res = connect.createStatement().executeQuery(SQL);
            List<Remark> remarks = new ArrayList<>();
            while (res.next()) {
                Remark remark = new Remark(res.getInt(1), res.getString(2), parseDate(res.getString(3)), new Staff(res.getInt(4), res.getString(5)));
                remarks.add(remark);
            }
            if (remarks.size() > 0) {
                s.setRemarks(remarks);
            }
            //Load Marks
            SQL = "SELECT subject.ID, subject.name, mark.ID, mark.point, mark.date FROM mark, subject WHERE subject.ID=subject_ID AND mark.student_ID=" + s.getID();
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                Subject subject = new Subject(res.getInt(1), res.getString(2));
                Mark mark = new Mark(res.getInt(3), res.getInt(4), parseDate(res.getString(5)), subject);
                s.addMark(mark);
            }
            //Load Extra Curricular
            SQL = "SELECT extra_curricular.ID, "
                    + "extra_curricular.name, "
                    + "extra_curricular.location, "
                    + "extra_curricular.start, "
                    + "extra_curricular.end, "
                    + "extra_curricular_has_student.notes "
                    + "FROM extra_curricular INNER JOIN extra_curricular_has_student "
                    + "ON extra_curricular.ID=extra_curricular_ID "
                    + "WHERE extra_curricular_has_student.student_ID=" + s.getID();
            res = connect.createStatement().executeQuery(SQL);
            ExtraCurricular exCurri = null;
            while (res.next()) {
                exCurri = new ExtraCurricular();
                exCurri.setID(res.getInt(1));
                exCurri.setName(res.getString(2));
                exCurri.setLocation(res.getString(3));
                try {
                    date = new Date(res.getDate(4).getTime());
                } catch (NullPointerException ex) {
                }
                exCurri.setStart(date);
                date = null;
                try {
                    date = new Date(res.getDate(5).getTime());
                } catch (NullPointerException ex) {
                }
                exCurri.setEnd(date);
                date = null;
                exCurri.setNotes(res.getString(6));
                s.addExtraCurricalar(exCurri);
            }
            //Load Family
            SQL = "SELECT ID FROM family_student WHERE student_ID=" + s.getID();
            res = connect.createStatement().executeQuery(SQL);
            FamilyStudent familyStudent = null;
            if (res.next()) {
                familyStudent = new FamilyStudent(res.getInt(1));
            }
            if (familyStudent != null) {
                SQL = "SELECT ID, relation, name, phone, address, occupation, gender FROM relatives_student WHERE family_student_ID=" + familyStudent.getID();
                res = connect.createStatement().executeQuery(SQL);
                while (res.next()) {
                    count = 0;
                    Relative relatives = new Relative(res.getInt(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count));
                    familyStudent.addRelative(relatives);
                }
                s.setFamilyStudent(familyStudent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally { 
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    @Override
    public Student findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public void setStudent(Student s) {
        student = s;
    }

    @Override
    public int save() {
        if (student == null) {
            return FAIL;
        }
        String SQL = "";
        Connection connect = null;
        connect = ConnectDB.getConnect();
        if (connect == null) {
            return CONNECT_FAIL;
        }
        PreparedStatement state = null;
        try {
            connect.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(StaffEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date date = null;
        try {
            connect.setAutoCommit(false);
            if (student.getID() == 0) {
                //Insert admission
                Admission admission = student.getAdmission();
                if (admission == null) {
                    admission = new Admission();
                }
                if (admission != null) {

                    SQL = "INSERT admission (date, prev_Institute, reason_Leaving) VALUES(?, ?, ?);";
                    state = connect.prepareStatement(SQL);
                    try {
                        date = new java.sql.Date(admission.getTime().getTime());
                    } catch (NullPointerException ex) {
                    }
                    state.setDate(1, date);
                    date = null;
                    state.setString(2, admission.getPrev_Institute());
                    state.setString(3, admission.getReason_Leaving());
                    state.executeUpdate();
                    

                } else {
                    SQL = "INSERT admission () VALUES();";
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();
                }

                SQL = "SET @ID_ADMISSION:=LAST_INSERT_ID();";
                state = connect.prepareStatement(SQL);
                state.executeUpdate();
                //Insert academic
                Batch b = student.getAcademic().getBatch();
                SQL = "INSERT academic (batch_ID) VALUES (?);";
                state = connect.prepareStatement(SQL);
                state.setInt(1, b.getID());
                state.executeUpdate();
                //Get ID academic
                SQL = "SET @ID_ACADEMIC:=LAST_INSERT_ID();";
                state = connect.prepareStatement(SQL);
                state.executeUpdate();

                //Insert student
                SQL = "INSERT student (No, name, birthday, address, "
                        + "phone, email, gender, admission_ID, "
                        + "academic_register_No) VALUES(?, ?, ?, ?, ?, ?, ?, @ID_ADMISSION, @ID_ACADEMIC);";
                state = connect.prepareStatement(SQL);
                state.setString(1, student.getNo());
                state.setString(2, student.getName());
                try {
                    date = new java.sql.Date(student.getBirthday().getTime());
                } catch (NullPointerException ex) {
                }
                state.setDate(3, date);
                date = null;
                state.setString(4, student.getAddress());
                state.setString(5, student.getPhone());
                state.setString(6, student.getEmail());
                state.setInt(7, student.getGender());
                state.executeUpdate();

                //Get ID student
                SQL = "SET @ID_STUDENT:=LAST_INSERT_ID();";
                state = connect.prepareStatement(SQL);
                state.executeUpdate();
                //Insert family_student
                SQL = "INSERT family_student (student_ID) VALUES (@ID_STUDENT);";
                state = connect.prepareStatement(SQL);
                state.executeUpdate();
                //Get ID family_student
                SQL = "SET @ID_FAMILY:=LAST_INSERT_ID();";
                state = connect.prepareStatement(SQL);
                state.executeUpdate();
                //Insert relatives_student
                if (student.getFamilyStudent() != null) {
                    List<Relative> relatives = student.getFamilyStudent().getListRelative();
                    if (relatives != null && relatives.size() > 0) {
                        for (Relative r : relatives) {
                            SQL = "INSERT relatives_student (relation, name, phone, address, "
                                    + "occupation, gender, family_student_ID) "
                                    + "VALUES(?, ?, ?, ?, ?, ?, @ID_FAMILY);";
                            state = connect.prepareStatement(SQL);
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
                //Insert mark
                //Get ID subject
                List<Mark> marks = student.getMarks();
                for (Mark m : marks) {
                    Subject s = m.getSubject();

                    SQL = "SET @ID_SUBJECT:=" + s.getID();
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();

                    //Insert
                    SQL = "INSERT mark (subject_ID,student_ID, point, date) VALUES(@ID_SUBJECT, @ID_STUDENT, ?, ?);";

                    state = connect.prepareStatement(SQL);
                    state.setInt(1, m.getPoint());
                    try {
                        date = new java.sql.Date(m.getTime().getTime());
                    } catch (NullPointerException ex) {
                    }
                    state.setDate(2, date);
                    date = null;
                    state.executeUpdate();
                }

                //Insert remark

                for (Remark r : student.getRemarks()) {
                    SQL = "INSERT remark (content, date, staff_No, student_ID) VALUES(?, ?, ?, @ID_STUDENT)";
                    state = connect.prepareStatement(SQL);
                    state.setString(1, r.getContent());
                    try {
                        date = new java.sql.Date(r.getTime().getTime());
                    } catch (NullPointerException ex) {
                    }
                    state.setDate(2, date);
                    date = null;
                    state.setInt(3, r.getStaff().getNo());
                    state.executeUpdate();
                }

                //Insert Extra Curricular
                for (ExtraCurricular ec : student.getExtraCurriculars()) {
                    SQL = "INSERT extra_curricular_has_student (extra_curricular_ID, notes, student_ID) VALUES(?, ?, @ID_STUDENT);";
                    state = connect.prepareStatement(SQL);
                    state.setInt(1, ec.getID());
                    state.setString(2, ec.getNotes());
                    state.executeUpdate();
                }

            } else {
                if (student.getAdmission() != null) {
                    Admission admission = student.getAdmission();
                    SQL = "UPDATE admission SET date=?, prev_Institute=?, reason_Leaving=? WHERE ID=?;";
                    state = connect.prepareStatement(SQL);
                    try {
                        date = new java.sql.Date(admission.getTime().getTime());
                    } catch (NullPointerException ex) {
                    }
                    state.setDate(1, date);
                    date = null;
                    state.setString(2, admission.getPrev_Institute());
                    state.setString(3, admission.getReason_Leaving());
                    state.setInt(4, admission.getID());
                    state.executeUpdate();

                }
                if (student.getAcademic().getBatch() != null) {
                    Batch batch = new Batch();
                    SQL = "UPDATE academic SET batch_ID=? WHERE register_No=?;";
                    state = connect.prepareStatement(SQL);
                    state.setInt(1, batch.getID());
                    state.setInt(2, student.getAcademic().getRegisterNo());
                    state.executeUpdate();
                }
                //Update student
                SQL = "UPDATE student SET No=?, name=?, birthday=?, address=?, "
                        + "phone=?, email=?, gender=? ;";
                state = connect.prepareStatement(SQL);
                state.setString(1, student.getNo());
                state.setString(2, student.getName());
                try {
                    date = new java.sql.Date(student.getBirthday().getTime());
                } catch (NullPointerException ex) {
                }
                state.setDate(3, date);
                date = null;
                state.setString(4, student.getAddress());
                state.setString(5, student.getPhone());
                state.setString(6, student.getEmail());
                state.setInt(7, student.getGender());
                state.executeUpdate();

                //Insert relatives_student
                if (student.getFamilyStudent() != null) {
                    List<Relative> relatives = student.getFamilyStudent().getListRelative();
                    for (Relative r : relatives) {
                        if (r.getID() == 0) {
                            SQL = "INSERT relatives_student (relation, name, phone, address, "
                                    + "occupation, gender, family_student_ID) "
                                    + "VALUES(?, ?, ?, ?, ?, ?, ?);";
                            state = connect.prepareStatement(SQL);
                            state.setString(1, r.getRelation());
                            state.setString(2, r.getName());
                            state.setString(3, r.getPhone());
                            state.setString(4, r.getAddress());
                            state.setString(5, r.getOccupation());
                            state.setInt(6, r.getGender());
                            state.setInt(7, student.getFamilyStudent().getID());
                            state.executeUpdate();
                        } else {
                            SQL = "UPDATE relatives_student SET relation=?, name=?, phone=?, address=?, "
                                    + "occupation=?, gender=? WHERE ID=?;";
                            state = connect.prepareStatement(SQL);
                            state.setString(1, r.getRelation());
                            state.setString(2, r.getName());
                            state.setString(3, r.getPhone());
                            state.setString(4, r.getAddress());
                            state.setString(5, r.getOccupation());
                            state.setInt(6, r.getGender());
                            state.setInt(7, r.getID());
                            state.executeUpdate();
                        }
                    }

                }
                //Insert mark
                //Get ID subject
                List<Mark> marks = student.getMarks();
                for (Mark m : marks) {
                    Subject s = m.getSubject();
                    if (m.getID() == 0) {
                        //Insert

                        SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES(?, ?, ?, ?);";

                        state = connect.prepareStatement(SQL);
                        state.setInt(1, s.getID());
                        state.setInt(2, m.getPoint());
                        try {
                            date = new java.sql.Date(m.getTime().getTime());
                        } catch (NullPointerException ex) {
                        }
                        state.setDate(3, date);
                        date = null;
                        state.setInt(4, student.getID());
                        state.executeUpdate();

                    } else {
                        SQL = "UPDATE mark SET subject_ID=? , point=?, date=? WHERE ID=?;";

                        state = connect.prepareStatement(SQL);
                        state.setInt(1, s.getID());
                        state.setInt(2, m.getPoint());

                        try {
                            date = new java.sql.Date(m.getTime().getTime());
                        } catch (NullPointerException ex) {
                        }
                        state.setDate(3, date);
                        date = null;
                        state.setInt(4, m.getID());
                        state.executeUpdate();

                    }
                }

                //Insert remark

                for (Remark r : student.getRemarks()) {
                    if (r.getID() == 0) {
                        SQL = "INSERT remark (content, date, staff_No, student_ID) VALUES(?, ?, ?, @ID_STUDENT)";
                        state = connect.prepareStatement(SQL);
                        state.setString(1, r.getContent());
                        try {
                            date = new java.sql.Date(r.getTime().getTime());
                        } catch (NullPointerException ex) {
                        }
                        state.setDate(2, date);
                        date = null;
                        state.setInt(3, r.getStaff().getNo());
                        state.executeUpdate();
                    } else {
                        SQL = "UPDATE remark SET content=?, date=?, staff_No=?, student_ID=?;";
                        state = connect.prepareStatement(SQL);
                        state.setString(1, r.getContent());
                        try {
                            date = new java.sql.Date(r.getTime().getTime());
                        } catch (NullPointerException ex) {
                        }
                        state.setDate(2, date);
                        date = null;
                        state.setInt(3, r.getStaff().getNo());
                        state.setInt(4, student.getID());
                        state.executeUpdate();
                    }
                }

                //Insert Extra Curricular
                for (ExtraCurricular ec : student.getExtraCurriculars()) {
                    if (ec.getID() == 0) {
                        SQL = "INSERT extra_curricular_has_student (extra_curricular_ID, notes, student_ID) VALUES(?, ?, ?);";
                        state = connect.prepareStatement(SQL);
                        state.setInt(1, ec.getID());
                        state.setString(2, ec.getNotes());
                        state.executeUpdate();
                    } else {
                        SQL = "UPDATE extra_curricular_has_student SET extra_curricular_ID=?, notes=?, student_ID=?;";
                        state = connect.prepareStatement(SQL);
                        state.setInt(1, ec.getID());
                        state.setString(2, ec.getNotes());
                        state.setInt(3, student.getID());
                        state.executeUpdate();
                    }
                }

                for (int i : ID_remove_extraCurricular) {
                    SQL = "DELETE extra_curricular_has_student WHERE ID=" + i;
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();
                }
                for (int i : ID_remove_mark) {
                    SQL = "DELETE mark WHERE ID=" + i;
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();
                }
                for (int i : ID_remove_relative) {
                    SQL = "DELETE relatives_student WHERE ID=" + i;
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();
                }
                for (int i : ID_remove_remark) {
                    SQL = "DELETE remark WHERE ID=" + i;
                    state = connect.prepareStatement(SQL);
                    state.executeUpdate();
                }
            }
            state.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(StudentEntity.class.getName()).log(Level.SEVERE, null, ex);
            return FAIL;
        }
        return SUCCESS;
    }

    @Override
    public int remove(int ID) {
        String SQL = "DELETE FROM student WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactive(int ID) {
        String SQL = "UPDATE student SET status=0 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int active(int ID) {
        String SQL = "UPDATE student SET status=1 WHERE ID=" + ID;
        return executeUpdate(SQL);
    }
}
