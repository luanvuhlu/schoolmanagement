/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class FamilyStaff{
    private List<Relative> relatives=new ArrayList<>();
    private int married;
    private int ID;
    public FamilyStaff() {
    }

    public FamilyStaff(int ID, int married) {
        this.married = married;
        this.ID = ID;
    }
        
    public int getMarried() {
        return married;
    }

    public int getID() {
        return ID;
    }

    public List<Relative> getRelatives() {
        return relatives;
    }


    public void setMaried(int maried) {
        this.married = maried;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRelatives(List<Relative> relatives) {
        this.relatives = relatives;
    }
    public void addRelative(Relative rela){
        relatives.add(rela);
    }
    public void removeRelative(String relation, String name){
        for(Relative r:relatives){
            if(r.getRelation().equalsIgnoreCase(relation) && r.getName().equalsIgnoreCase(name)){
                relatives.remove(r);
                return;
            }
        }
    }
}
