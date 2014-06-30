/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author luan
 */
public class Mark {
    private int ID;
    private int point;
    private Date time;
    private Subject subject;
    private Student student;

    public Mark() {
        
    }
    public Mark(int ID){
        this.ID=ID;
    }

    public Mark(int point, Date time) {
        this.point = point;
        this.time = time;
    }

    public Mark(int ID, int point, Date time, Subject subject) {
        this.ID = ID;
        this.point = point;
        this.time = time;
        this.subject = subject;
    }

    public Mark(int ID, int point, Date time, Subject subject, Student student) {
        this.ID = ID;
        this.point = point;
        this.time = time;
        this.subject = subject;
        this.student = student;
    }

    public Mark(int ID, int point, Date time) {
        this.ID = ID;
        this.point = point;
        this.time = time;
    }


    public int getPoint() {
        return point;
    }

    public Student getStudent() {
        return student;
    }
    

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getTime() {
        return time;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
