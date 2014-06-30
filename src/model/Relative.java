/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luan
 */
public class Relative {
    private int ID;
    private String relation;
    private String name;
    private String phone;
    private String address;
    private String occupation;
    private int gender;
    

    public Relative() {
    }

    public Relative(int ID, String relation, String name, String phone, String address, String occupation, int gender) {
        this.relation = relation;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.occupation = occupation;
        this.gender = gender;
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPhone() {
        return phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
    
}
