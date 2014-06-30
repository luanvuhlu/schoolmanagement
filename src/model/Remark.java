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
public class Remark {
    private int ID;
    private String content;
    private Staff staff;
    private Date time;

    public Remark() {
    }

    public Remark(String content, Date time) {
        this.content = content;
        this.time = time;
    }

    public Remark(int ID, String content, Date time, Staff staff) {
        this.ID = ID;
        this.content = content;
        this.staff = staff;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public int getID() {
        return ID;
    }

    public Staff getStaff() {
        return staff;
    }

    public Date getTime() {
        return time;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Remark(String content, Staff staff, Date time) {
        this.content = content;
        this.staff = staff;
        this.time = time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
