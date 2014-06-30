/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luan
 */
public class Student extends Person{
    private int ID;
    private String No;
    private int registerNo;
    private Admission admission;
    private Academic academic;
    private List<Mark> marks=new ArrayList<>();
    private List<Remark> remarks=new ArrayList<>();
    private FamilyStudent familyStudent;
    private List<ExtraCurricular> extraCurriculars=new ArrayList<>();
    public Student() {
        this.admission=new Admission();
        this.academic=new Academic();
        this.familyStudent=new FamilyStudent();
    }
    public Student (int ID){
        this.ID=ID;
    }
    public Student(int ID, String No, String name) {
        this.ID=ID;
        this.No=No;
        setName(name);
        
    }
    public Student(String No, String name) {
        this.ID=ID;
        this.No=No;
        setName(name);
        
    }
    public Student(int ID, int registerNo, String name){
        this.ID=ID;
        this.registerNo=registerNo;
        setName(name);
    }
    public Student(int registerNo, String No, String name, String batch, String phone, String email, int gender) {
        this.No = No;
        this.registerNo=registerNo;
        setName(name);
        setPhone(phone);
        setEmail(email);
        setGender(gender);
        this.academic = new Academic(registerNo, new Batch(batch));
    }
    public Student(int ID, int registerNo, String No, String name, String batch, String phone, String email, int gender) {
        this.ID = ID;
        this.No=No;
        this.registerNo=registerNo;
        setName(name);
        setPhone(phone);
        setEmail(email);
        setGender(gender);
        this.academic = new Academic(registerNo, new Batch(batch));
    }

    public Student(int ID, String No, int registerNo, String name, Date birthday, String address, String phone, String email, int gender, int admissionID) {
        super(name, birthday, address, phone, email, gender);
        this.No = No;
        this.ID=ID;
        this.registerNo = registerNo;
        this.admission = new Admission(admissionID);
        this.academic = new Academic(registerNo);
    }

    public int getID() {
        return ID;
    }

    public void addRemarks(Remark remark){
        remarks.add(remark);
    }
    public void addExtraCurricalar(ExtraCurricular extraCurricular){
        extraCurriculars.add(extraCurricular);
    }

    public void setNo(String No) {
        this.No = No;
    }



    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    public void setFamilyStudent(FamilyStudent familyStudent) {
        this.familyStudent = familyStudent;
    }

    public void setExtraCurriculars(List<ExtraCurricular> extraCurriculars) {
        this.extraCurriculars = extraCurriculars;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public void setAcademic(Academic academic) {
        this.academic = academic;
    }

    public String getNo() {
        return No;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRegisterNo() {
        return registerNo;
    }
    public void addMark(Mark mark){
        marks.add(mark);
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

//    public List<Subject> getSubjects() {
//        return subjects;
//    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public FamilyStudent getFamilyStudent() {
        return familyStudent;
    }
  

    public List<ExtraCurricular> getExtraCurriculars() {
        return extraCurriculars;
    }

    public Admission getAdmission() {
        return admission;
    }

    public Academic getAcademic() {
        return academic;
    }
    public void removeMark(String SubjectName, Date time){
        for(Mark m:marks){
            if(m.getSubject().getName().equalsIgnoreCase(SubjectName) && m.getTime().equals(time)){
                marks.remove(m);
                return;
            }
        }
    }
    public void addRemark(Remark r){
        remarks.add(r);
    }
    public void removeRemark(int staffNo, Date time){
        for(Remark r:remarks){
            if(r.getStaff().getNo()==staffNo && r.getTime().getTime()==time.getTime()){
                remarks.remove(r);
                return;
            }
        }
    }
    public void removeExtraCurricular(String name, String location, Date start, Date end){
        for(ExtraCurricular extra:extraCurriculars){
            if(extra.getName().equalsIgnoreCase(name) 
                    && extra.getLocation().equalsIgnoreCase(location) 
                    && extra.getStart().getTime()==start.getTime() 
                    && extra.getEnd().getTime()==end.getTime()){
                extraCurriculars.remove(extra);
                return;
            }
        }
        
    }
}
