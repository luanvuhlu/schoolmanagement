/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luan
 */
public class Department {
    private int ID;
    private String name;
    private List<Position> listPosition=new ArrayList<>();

    public Department() {

    }

    public Department(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    
    public Department(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public List<Position> getListPosition() {
        return listPosition;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setListPosition(List<Position> listPosition) {
        this.listPosition = listPosition;
    }
    
    
}
