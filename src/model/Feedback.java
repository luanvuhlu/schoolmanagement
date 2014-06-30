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
public class Feedback {
    private int ID;
    private Student student;
    private String content;
    private Date time;
    private int status;

    public Feedback() {
    }

    public int getID() {
        return ID;
    }

    public Feedback(int ID, Student student, String content, Date time){
        this.ID=ID;
        this.student=student;
        this.content=content;
        this.time=time;
    }
    public String getContent() {
        return content;
    }

    public Student getStudent() {
        return student;
    }

    public Date getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
