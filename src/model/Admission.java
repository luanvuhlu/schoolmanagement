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
public class Admission {
    private int ID;
    private Date time;
    private String prev_Institute;
    private String reason_Leaving;

    public Admission() {
    }

    public Admission(int ID,  String prev_Institute, String reason_Leaving, Date time) {
        this.ID = ID;
        this.time = time;
        this.prev_Institute = prev_Institute;
        this.reason_Leaving = reason_Leaving;
    }
    public Admission(Date time, String prev_Institute, String reason_Leaving) {
        this.time = time;
        this.prev_Institute = prev_Institute;
        this.reason_Leaving = reason_Leaving;
    }

    public Admission(int ID) {
        this.ID = ID;
    }
    

    public String getPrev_Institute() {
        return prev_Institute;
    }

    public String getReason_Leaving() {
        return reason_Leaving;
    }

    public int getID() {
        return ID;
    }

    public Date getTime() {
        return time;
    }

    public void setPrev_Institute(String prev_Institute) {
        this.prev_Institute = prev_Institute;
    }

    public void setReason_Leaving(String reason_Leaving) {
        this.reason_Leaving = reason_Leaving;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
