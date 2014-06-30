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
public class ExtraCurricular {
    private int ID;
    private String name;
    private String location;
    private Date start;
    private Date end;
    private String notes;

    public ExtraCurricular() {
    }

    public ExtraCurricular(String name, String location, Date start, Date end) {
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
    }
    public ExtraCurricular(int ID, String name, String location, Date start, Date end) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    public ExtraCurricular(int ID, String name, String location, Date start, Date end, String notes) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
        this.notes = notes;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getNotes() {
        return notes;
    }
    
}
