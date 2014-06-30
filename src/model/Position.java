/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luan
 */
public class Position {
    private int ID;
    private String name;
    private Department department;

    public Position() {
    }
    public Position(String name, Department department) {
        this.name=name;
        this.department=department;
    }

    public Position(int ID, String name, Department department) {
        this.ID = ID;
        this.name = name;
        this.department = department;
    }



 

    public int getID() {
        return ID;
    }

    public Department getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
    
  

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    
}
