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
public class Batch {
    private int ID;
    private String name;
    private Date time;

    public Batch() {
    }
    public Batch(int ID){
        this.ID=ID;
    }

    public Batch(String name, Date time) {
        this.name = name;
        this.time = time;
    }
    public Batch(String name) {
        this.name = name;
    }

    public Batch(int ID, String name, Date time) {
        this.ID = ID;
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }

    public int getID() {
        return ID;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
