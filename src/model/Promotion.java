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
public class Promotion {
    private int ID;
    private Position position;
    private Date time;

    public Promotion() {
    }

    public Promotion(int ID, Position position, Date time) {
        this.ID = ID;
        this.position = position;
        this.time = time;
    }
    public Promotion(Position position, Date time) {
        this.position = position;
        this.time = time;
    }
    

    public Date getTime() {
        return time;
    }

    public int getID() {
        return ID;
    }

    public Position getPosition() {
        return position;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    
    
}
