/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luan
 */
public class Subject {
    private int ID;
    private String name;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

//    public List<Mark> getMark() {
//        return marks;
//    }

//    public void setMark(List<Mark> marks) {
//        this.marks = marks;
//    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
