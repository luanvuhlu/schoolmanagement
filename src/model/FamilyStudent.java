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
public class FamilyStudent{
    private int ID;
    private List<Relative> listRelative=new ArrayList<>();
    public FamilyStudent() {
    }

    public List<Relative> getListRelative() {
        return listRelative;
    }
    public FamilyStudent(int ID) {
        this.ID = ID;
        listRelative=new ArrayList<>();
    }
    public void addRelative(Relative relative){
        listRelative.add(relative);
    }

    public FamilyStudent(int ID, List<Relative> listRelatives) {
        this.ID = ID;
        this.listRelative = listRelatives;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setRelatives(List<Relative> listRelative) {
        this.listRelative = listRelative;
    }
    public void removeRelative(String relation, String name){
        for(Relative r:listRelative){
            if(r.getRelation().equalsIgnoreCase(relation) && r.getName().equalsIgnoreCase(name)){
                listRelative.remove(r);
                return;
            }
        }
    }
    
    
}
