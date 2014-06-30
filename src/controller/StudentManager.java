/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connectDB.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Academic;
import model.Admission;
import model.Batch;
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
public class StudentManager extends Manager implements StudentMng, BatchMng, ExtraCurricularMng, SubjectMng {

    @Override
    public String searchStudent(int No, String name) {

        String SQL = "SELECT student.ID, student.academic_register_No, student.No, student.name,batch.name AS Batch, student.phone, student.email, student.gender "
                + "from student INNER JOIN (academic inner join batch on academic.batch_ID=batch.ID) ON academic.register_No=student.academic_register_No WHERE 2>1 ";
        if (name != null && !name.isEmpty()) {
            SQL += " AND student.name like '%" + name + "%' ";
        }
        if (No != 0) {
            SQL += " AND academic_register_No like '" + No + "%'";
        }
        return SQL;
    }

    @Override
    public int addStudent(Student student) {
        String SQL;
        List<String> listSQL = new ArrayList();
        //Insert admission
        Admission admission = student.getAdmission();
        if(admission==null){
            admission=new Admission();
        }
        if (admission != null) {
            if(admission.getTime()==null){
                SQL = "INSERT admission (date, prev_Institute, reason_Leaving) VALUES("
                    + "null , "
                    + "'" + admission.getPrev_Institute() + "', "
                    + "'" + admission.getReason_Leaving() + "'"
                    + ");";
            }else{
                SQL = "INSERT admission (date, prev_Institute, reason_Leaving) VALUES("
                    + "'" + formatDate(admission.getTime()) + "', "
                    + "'" + admission.getPrev_Institute() + "', "
                    + "'" + admission.getReason_Leaving() + "'"
                    + ");";
            }

        } else {
            SQL = "INSERT admission () VALUES();";
        }
        listSQL.add(SQL);
        //Get ID admission
        SQL = "SET @ID_ADMISSION:=LAST_INSERT_ID();";
        listSQL.add(SQL);
        //Insert academic
        if (student.getAcademic() != null) {
            Batch b = student.getAcademic().getBatch();
            if (b != null) {
                if (b.getID() < 1) {
                    SQL = "SET @ID_BATCH:=(SELECT ID FROM batch WHERE name='" + b.getName() + "' LIMIT 0,1);";
                } else {
                    SQL = "SET @ID_BATCH:=" + b.getID();
                }
                listSQL.add(SQL);
                SQL = "INSERT academic (batch_ID) VALUES (@ID_BATCH);";
                listSQL.add(SQL);
                //Get ID academic
                SQL = "SET @ID_ACADEMIC:=LAST_INSERT_ID();";
                listSQL.add(SQL);
            }
        }
        //Insert student
        if(student.getBirthday()==null){
            SQL = "INSERT student (No, name, birthday, address, phone, email, gender, admission_ID, academic_register_No) VALUES("
                + "'" + student.getNo() + "', "
                + "'" + student.getName() + "', "
                + "null , "
                + "'" + student.getAddress() + "', "
                + "'" + student.getPhone() + "', "
                + "'" + student.getEmail() + "', "
                + student.getGender() + ", "
                + "@ID_ADMISSION, "
                + "@ID_ACADEMIC"
                + ");";
        }else{
            SQL = "INSERT student (No, name, birthday, address, phone, email, gender, admission_ID, academic_register_No) VALUES("
                + "'" + student.getNo() + "', "
                + "'" + student.getName() + "', "
                + "'" + formatDate(student.getBirthday()) + "', "
                + "'" + student.getAddress() + "', "
                + "'" + student.getPhone() + "', "
                + "'" + student.getEmail() + "', "
                + student.getGender() + ", "
                + "@ID_ADMISSION, "
                + "@ID_ACADEMIC"
                + ");";
        }
        listSQL.add(SQL);
        //Get ID student
        SQL = "SET @ID_STUDENT:=LAST_INSERT_ID();";
        listSQL.add(SQL);
        //Insert family_student
        SQL = "INSERT family_student (student_ID) VALUES (@ID_STUDENT);";
        listSQL.add(SQL);
        //Get ID family_student
        SQL = "SET @ID_FAMILY:=LAST_INSERT_ID();";
        listSQL.add(SQL);
        //Insert relatives_student
        if (student.getFamilyStudent() != null) {
            List<Relative> relatives = student.getFamilyStudent().getListRelative();
            if (relatives != null && relatives.size() > 0) {
                for (Relative r : relatives) {
                    SQL = "INSERT relatives_student (relation, name, phone, address, occupation, gender, family_student_ID) "
                            + "VALUES("
                            + "'" + r.getRelation() + "', "
                            + "'" + r.getName() + "',"
                            + "'" + r.getPhone() + "',"
                            + "'" + r.getAddress() + "', "
                            + "'" + r.getOccupation() + "',"
                            + r.getGender() + ", "
                            + "@ID_FAMILY"
                            + ");";
                    listSQL.add(SQL);
                }
            }
        }
        //Insert mark
        //Get ID subject
        List<Mark> marks = student.getMarks();
        if (marks != null && marks.size() > 0) {
            for (Mark m : marks) {
                if (m.getSubject() != null) {
                    if (m.getSubject().getID() == 0) {
                        SQL = "SET @ID_SUBJECT:=(SELECT ID FROM subject WHERE name='" + m.getSubject().getName() + "' LIMIT 0,1);";
                    } else {
                        SQL = "SET @ID_SUBJECT:=" + m.getSubject().getID();
                    }
                    listSQL.add(SQL);
                    //Insert
                    if(m.getTime()==null){
                        SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES("
                            + "@ID_SUBJECT, "
                            + m.getPoint() + ", "
                            + " null , "
                            + "@ID_STUDENT"
                            + ");";
                    }else{
                        SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES("
                            + "@ID_SUBJECT, "
                            + m.getPoint() + ", "
                            + "'" + formatDate(m.getTime()) + "', "
                            + "@ID_STUDENT"
                            + ");";
                    }
                    listSQL.add(SQL);
                }
            }
        }
        //Insert remark
        List<Remark> remarks = student.getRemarks();
        if (remarks != null && remarks.size() > 0) {
            //Insert
            for (Remark r : remarks) {
                if (r.getStaff() == null || r.getStaff().getNo() <= 0) {
                    continue;
                }
                if(r.getTime()==null){
                    SQL = "INSERT remark (content, date, staff_No, student_ID) VALUES("
                        + "'" + r.getContent() + "', "
                        + "null , "
                        + r.getStaff().getNo() + ", "
                        + "@ID_STUDENT"
                        + ");";
                }else{
                    SQL = "INSERT remark (content, date, staff_No, student_ID) VALUES("
                        + "'" + r.getContent() + "', "
                        + "'" + formatDate(r.getTime()) + "', "
                        + r.getStaff().getNo() + ", "
                        + "@ID_STUDENT"
                        + ");";
                }
                listSQL.add(SQL);
            }
        }
        return executeManySQL(listSQL);
    }

    @Override
    public int editStudent(Student student, Student oldStudent) {
        String SQL;
        List<String> listSQL = new ArrayList();
        SQL = "SET @ID_STUDENT:=" + oldStudent.getID()+";";
        listSQL.add(SQL);
        //Update admission
        Admission admission = student.getAdmission();
        if (admission != null) {
            if(admission.getTime()==null){
                SQL = "UPDATE admission SET "
                    + "prev_Institute='" + admission.getPrev_Institute() + "', "
                    + "reason_Leaving='" + admission.getReason_Leaving() + "', "
                    + "date=null "
                    + "WHERE ID=" + admission.getID()+";";
            }else{
            SQL = "UPDATE admission SET "
                    + "prev_Institute='" + admission.getPrev_Institute() + "', "
                    + "reason_Leaving='" + admission.getReason_Leaving() + "', "
                    + "date='" + formatDate(admission.getTime()) + "' "
                    + "WHERE ID=" + admission.getID()+";";
            }
            listSQL.add(SQL);
        }
        //Update batch
        Academic academic = student.getAcademic();
        if (academic != null) {
            Batch b = academic.getBatch();
            if (b != null) {
                if (b.getID() <= 0) {
                    SQL = "SET @ID_BATCH:=(SELECT ID FROM batch WHERE name='" + b.getName() + "');";
                    listSQL.add(SQL);
                    SQL = "UPDATE academic SET batch_ID=@ID_BATCH WHERE register_No=" + academic.getRegisterNo()+";";
                    listSQL.add(SQL);
                }
            }
        }
        //Update student
        if(student.getBirthday()==null){
            SQL = "UPDATE student SET "
                + "No='" + student.getNo() + "', "
                + "name='" + student.getName() + "', "
                + "birthday= null, "
                + "phone='" + student.getPhone() + "', "
                + "address='" + student.getAddress() + "', "
                + "email='" + student.getEmail() + "', "
                + "gender=" + student.getGender() + " "
                + "WHERE ID=@ID_STUDENT;";
        }else{
        SQL = "UPDATE student SET "
                + "No='" + student.getNo() + "', "
                + "name='" + student.getName() + "', "
                + "birthday='" + formatDate(student.getBirthday()) + "', "
                + "phone='" + student.getPhone() + "', "
                + "address='" + student.getAddress() + "', "
                + "email='" + student.getEmail() + "', "
                + "gender=" + student.getGender() + " "
                + "WHERE ID=@ID_STUDENT;";
        }
        listSQL.add(SQL);
        //Update Family
        if (student.getFamilyStudent() != null) {
            if (student.getFamilyStudent().getID() == 0) {
                SQL = "INSERT family_student(student_ID) values(@ID_STUDENT);";
            }
            listSQL.add(SQL);

            if (student.getFamilyStudent().getID() <= 0) {
                SQL = "SET @ID_FAMILY=LAST_INSERT_ID();";
            } else {
                SQL = "SET @ID_FAMILY:=" + student.getFamilyStudent().getID()+";";
            }
            listSQL.add(SQL);

            if (student.getFamilyStudent().getListRelative() != null) {
                for (Relative relatives : student.getFamilyStudent().getListRelative()) {
                    if (relatives.getID() == 0) {
                        SQL = "INSERT relatives_student(relation, name, phone, address, occupation, gender, family_student_ID) VALUES("
                                + "'" + relatives.getRelation() + "', "
                                + "'" + relatives.getName() + "',"
                                + "'" + relatives.getPhone() + "',"
                                + "'" + relatives.getAddress() + "', "
                                + "'" + relatives.getOccupation() + "',"
                                + relatives.getGender() + ", "
                                + "@ID_FAMILY"
                                + ");";
                    } else {
                        SQL = "UPDATE relatives_student SET "
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
                    for (Relative re : oldStudent.getFamilyStudent().getListRelative()) {
                        boolean flag = false;
                        for (Relative relative : student.getFamilyStudent().getListRelative()) {
                            if (re.getID() == relative.getID()) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            SQL = "DELETE FROM relatives_student WHERE ID=" + re.getID()+";";
                            listSQL.add(SQL);
                        }
                    }

                }
            }
        }
        //Update mark
        List<Mark> marks = student.getMarks();
        if (marks != null) {
            for (Mark m : marks) {
                if (m.getSubject().getID() <= 0) {
                    SQL = "SET @ID_SUBJECT:=(SELECT ID FROM subject WHERE name='" + m.getSubject().getName() + "');";
                } else {
                    SQL="SET @ID_SUBJECT:="+m.getSubject().getID();
                }
                listSQL.add(SQL);
                if (m.getID() <= 0) {
                    if(m.getTime()==null){
                        SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES("
                            + "@ID_SUBJECT, "
                            + m.getPoint() + ", "
                            + "null , "
                            + "@ID_SUBJECT"
                            + ")";
                    }else{
                    SQL = "INSERT mark (subject_ID, point, date, student_ID) VALUES("
                            + "@ID_SUBJECT, "
                            + m.getPoint() + ", "
                            + "'" + formatDate(m.getTime()) + "', "
                            + "@ID_SUBJECT"
                            + ")";
                    }
                } else {
                    if(m.getTime()==null){
                        SQL="UPDATE mark SET "
                            + "subject_ID=@ID_SUBJECT, "
                            + "point="+m.getPoint()+", "
                            + "date=null "
                            + "WHERE ID="+m.getID()+";";
                    }else{
                    SQL="UPDATE mark SET "
                            + "subject_ID=@ID_SUBJECT, "
                            + "point="+m.getPoint()+", "
                            + "date='"+formatDate(m.getTime())+"' "
                            + "WHERE ID="+m.getID()+";";
                    }
                }
                listSQL.add(SQL);
            }
            if(oldStudent.getMarks() !=null && oldStudent.getMarks().size() >0){
                for(Mark m:oldStudent.getMarks()){
                    System.out.println(m.getID());
                    boolean flag=false;
                    for(Mark mark:marks){
                        System.out.println(mark.getID());
                        if(m.getID()==mark.getID()){
                            flag=true;
                            break;
                        }
                    }
                    if(!flag){
                        SQL="DELETE FROM mark WHERE ID="+m.getID();
                        listSQL.add(SQL);
                    }
                }
            }
        }
        //Update remark
        List<Remark> remarks = student.getRemarks();
        if (remarks != null) {
            for (Remark r : remarks) {
                if(r.getStaff()==null || r.getStaff().getNo()==0){
                    continue;
                }
                if (r.getID() > 0) {
                    if(r.getTime()==null){
                        SQL="UPDATE remark SET "
                            + "student_ID=@ID_STUDENT, "
                            + "staff_No="+r.getStaff().getNo()+", "
                            + "content='"+r.getContent()+"', "
                            + "date=null "
                            + "WHERE ID="+r.getID()+";";
                    }else{
                    SQL="UPDATE remark SET "
                            + "student_ID=@ID_STUDENT, "
                            + "staff_No="+r.getStaff().getNo()+", "
                            + "content='"+r.getContent()+"', "
                            + "date='"+formatDate(r.getTime())+"' "
                            + "WHERE ID="+r.getID()+";";
                    }
                }else{
                    if(r.getTime()==null){
                        SQL = "INSERT remark (student_ID, staff_No, content, date) VALUES("
                            + "@ID_STUDENT, "
                            + r.getStaff().getNo()+ ", "
                            + "'"+r.getContent()+"', "
                            + "null "
                            + ")";
                    }else{
                    SQL = "INSERT remark (student_ID, staff_No, content, date) VALUES("
                            + "@ID_STUDENT, "
                            + r.getStaff().getNo()+ ", "
                            + "'"+r.getContent()+"', "
                            + "'" + formatDate(r.getTime()) + "'"
                            + ")";
                    }
                }
                listSQL.add(SQL);
            }
            if(oldStudent.getRemarks()!=null && oldStudent.getRemarks().size() >0){
                for(Remark r:oldStudent.getRemarks()){
                    System.out.println(r.getID());
                    boolean flag=false;
                    for(Remark re:remarks){
                        System.out.println(re.getID());
                        if(r.getID()==re.getID()){
                            flag=true;
                            break;
                        }
                    }
                    if(!flag){
                        SQL="DELETE FROM remark WHERE ID="+r.getID();
                        listSQL.add(SQL);
                    }
                }
            }
        }

        return executeManySQL(listSQL);
    }

    @Override
    public int removeStudent(int ID) {
        return SUCCESS;
    }
    @Override
    public int deactiveStudent(int ID) {
        String SQL="UPDATE student SET status =0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }

    @Override
    public Student getStudentDetail(int ID) {
        Student student = null;
        int count = 0;
        String SQL;
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
                student = new Student(res.getInt(++count), res.getString(++count), res.getInt(++count), res.getString(++count), parseDate(res.getString(++count)), res.getString(++count), res.getString(++count), res.getString(++count), res.getInt(++count), res.getInt(++count));
            }
            //Load Admission infomation
            if (student.getAdmission() != null) {
                SQL = "SELECT prev_Institute, reason_Leaving, date FROM admission WHERE ID=" + student.getAdmission().getID();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    student.getAdmission().setPrev_Institute(res.getString(1));
                    student.getAdmission().setReason_Leaving(res.getString(2));
                    student.getAdmission().setTime(parseDate(res.getString(3)));
                }
            }
            //Load Academic infomation
            if (student.getAcademic() != null) {
                SQL = "SELECT batch.ID, batch.name, batch.date FROM batch, academic WHERE batch.ID=academic.batch_ID AND academic.register_No=" + student.getAcademic().getRegisterNo();
                res = connect.createStatement().executeQuery(SQL);
                if (res.next()) {
                    student.getAcademic().setBatch(new Batch(res.getInt(1), res.getString(2), parseDate(res.getString(3))));
                }
            }
            //Load remarks
            SQL = "SELECT remark.ID, remark.content, remark.date, remark.staff_No, staff.name FROM remark, staff WHERE staff.No=remark.staff_No AND student_ID=" + student.getID();
            res = connect.createStatement().executeQuery(SQL);
            List<Remark> remarks = new ArrayList<>();
            while (res.next()) {
                Remark remark = new Remark(res.getInt(1), res.getString(2), parseDate(res.getString(3)), new Staff(res.getInt(4), res.getString(5)));
                remarks.add(remark);
            }
            if (remarks.size() > 0) {
                student.setRemarks(remarks);
            }
            //Load Marks
            SQL = "SELECT subject.ID, subject.name, mark.ID, mark.point, mark.date FROM mark, subject WHERE subject.ID=subject_ID AND mark.student_ID=" + student.getID();
            res = connect.createStatement().executeQuery(SQL);
            while (res.next()) {
                Subject subject = new Subject(res.getInt(1), res.getString(2));
                Mark mark = new Mark(res.getInt(3), res.getInt(4), parseDate(res.getString(5)), subject);
                student.addMark(mark);
            }
            //Load Extra Curricular
            //Load Family
            SQL = "SELECT ID FROM family_student WHERE student_ID=" + student.getID();
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
                student.setFamilyStudent(familyStudent);
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
        return student;
    }

    @Override
    public String getStudentList(int page) {
        String SQL = "SELECT student.ID, student.academic_register_No, student.No, student.name,batch.name AS Batch, student.phone, student.email, student.gender "
                + "FROM student LEFT JOIN (academic LEFT JOIN batch on academic.batch_ID=batch.ID) ON academic.register_No=student.academic_register_No "
                + " WHERE student.status=1";


        return SQL;
    }

    @Override
    public int addBatch(String[] data) {
        String SQL="";
        if(data[1].isEmpty()){
             SQL= "INSERT batch(name, date) values("
                + "'" + data[0] + "', "
                + "null"
                + ") ";
        }else{
            SQL = "INSERT batch(name, date) values("
                + "'" + data[0] + "', "
                + "'"+data[1]+"'"
                + ") ";
        }

        return executeUpdate(SQL);
    }

    @Override
    public int editBatch(String[] data) {
        String SQL ="";
        if(data[2].isEmpty()){
            SQL="UPDATE batch SET "
                + "name='" + data[1] + "', "
                + "date=null "
                + "WHERE ID=" + data[0];
        }else{
            SQL="UPDATE batch SET "
                + "name='" + data[1] + "', "
                + "date='" + data[2] + "' "
                + "WHERE ID=" + data[0];
        }

        return executeUpdate(SQL);
    }

    @Override
    public String removeBatch(int ID) {
        String SQL;
        SQL="SELECT 1 FROM academic WHERE batch_ID="+ID;
        if(getIDEdentity(SQL)==-1){
            SQL= "DELETE FROM batch WHERE ID=" + ID;
        }else{
            return "Have some student in batch ! You must remove or change students !";
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
    public int addExtraCurricular(String[] data) {
        String SQL = "INSERT extra_curricular(name) values("
                + "'" + data[0] + "'"
                + ") ";

        return executeUpdate(SQL);
    }

    @Override
    public int removeExtraCurricular(int ID) {
        String SQL = "DELETE FROM extra_curricular WHERE ID=" + ID;
        return executeUpdate(SQL);
    }

    @Override
    public int editExtraCurricular(String[] data) {
        String SQL = "UPDATE extra_curricular SET "
                + "name='" + data[1] + "' "
                + "WHERE ID=" + data[0];

        return executeUpdate(SQL);
    }

    @Override
    public int addSubject(String[] data) {
        String SQL = "INSERT subject(name) values("
                + "'" + data[0] + "'"
                + ") ";

        return executeUpdate(SQL);
    }

    @Override
    public String removeSubject(int ID) {
        String SQL ="";
        SQL="SELECT 1 FROM mark WHERE subject_ID="+ID;
        if(getIDEdentity(SQL)==-1){
            SQL="DELETE FROM subject WHERE ID=" + ID;
        }else{
            return "Have some student have this subject. You must remove subject in student info";
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
    public int editSubject(String[] data) {
        String SQL = "UPDATE subject SET "
                + "name='" + data[1] + "' "
                + "WHERE ID=" + data[0];
        return executeUpdate(SQL);
    }

    public Mark getMarkDetail(int ID) {
        Mark mark = new Mark(ID);

        return mark;
    }

    @Override
    public int deactiveBatch(int ID) {
        String SQL="UPDATE batch SET status=0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactiveExtraCurricular(int ID) {
        String SQL="UPDATE extra_curricular SET status=0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }

    @Override
    public int deactiveSubject(int ID) {
        String SQL="UPDATE subject SET status=0 WHERE ID="+ID;
        return executeUpdate(SQL);
    }
}
