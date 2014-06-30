/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Staff extends Person{
    private int No;
    private String education;
    private String experience;
    private List<Feedback> feedbacks=new ArrayList<>();
    private FamilyStaff familyStaff=new FamilyStaff();
    private List<Promotion> promotions=new ArrayList<>();

    public Staff() {
        
    }
    public Staff(int No){
        this.No=No;
    }
    public Staff(int No, String name){
        this.No=No;
        setName(name);
    }
    public Staff(int No, String name, Date birthday, String phone, String email, String address, String education, int gender ){
        super(name, birthday, address, phone, email, gender);
        this.No=No;
        this.education=education;
        
        
    }

    public Staff(int No, String name, Date birthday, String address, String phone, String email, String education, String experience, int gender) {
        super(name, birthday, address, phone, email, gender);
        this.No = No;
        this.education = education;
        this.experience = experience;
    }

    public Staff(int No, String name,Date birthday, String phone, String email, int departmentID, String departmentName, int gender ){
        this.No=No;
        this.setBirthday(birthday);
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        setGender(gender);
               
    }
    
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    public void addFeedback(Feedback feedback){
        this.feedbacks.add(feedback);
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public int getNo() {
        return No;
    }

    

    public String getEducation() {
        if(education==null){
            return "Unknown";
        }
        return education;
    }

    public FamilyStaff getFamilyStaff() {
        return familyStaff;
    }
    

    public String getExperience() {
        if(experience==null){
            return "Unknown";
        }
        return experience;
    }


    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setFamilyStaff(FamilyStaff familyStaff) {
        this.familyStaff = familyStaff;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
    public void addPromotion(Promotion promotion){
        promotions.add(promotion);
    }
    public void removePromotion(int ID){
        for(Promotion p:promotions){
            if(p.getID()==ID){
                promotions.remove(p);
            }
        }
    }
    public void removePromotion(Date date){
        for(Promotion p:promotions){
            if(p.getTime().equals(date)){
                promotions.remove(p);
                return;
            }
        }
    }
    
    public Promotion getCurrentPromotion(){
        return promotions.get(promotions.size()-1);
    }
    public void removeFeedback(String StudentNo, Date time){
        for(Feedback f:feedbacks){
            if(f.getStudent().getNo().equalsIgnoreCase(StudentNo) && f.getTime().getTime()==time.getTime()){
                feedbacks.remove(f);
                return;
            }
        }
    }
    
    @Override
    public String toString(){
        String s= "No: "+getNo()+"\n"
                + "name: "+getName()+"\n"
                + "birthday: "+getBirthday()+"\n"
                + "address: "+getAddress()+"\n"
                + "phone: "+getPhone()+"\n"
                + "email: "+getEmail()+"\n"
                + "gender: "+getGender()+"\n"
                + "experience: "+getExperience()+"\n"
                + "education background: "+getEducation()+"\n";
        if(getFamilyStaff() !=null){
            s+="Family Detail: \n";
            if(familyStaff.getMarried()==1){
                s+="Married ! \n";
            }else{
                s+="Single !\n";
            }
            s+="Relatives: \n";
            for(Relative relatives:familyStaff.getRelatives()){
                s+="ID: "+relatives.getID()+"\n"
                        + "relation: "+relatives.getRelation()+"\n"
                        + "name: "+relatives.getName()+"\n"
                        + "phone: "+relatives.getPhone()+"\n"
                        + "address: "+relatives.getAddress()+"\n"
                        + "occupation: "+relatives.getOccupation()+"\n"
                        + "gender: "+relatives.getGender()+"\n"
                        ;
            }
        }
        if(getPromotions() !=null && getPromotions().size() >0){
            s+="Promotion Detail: \n";
            for(Promotion promotion:promotions){
                Position position=promotion.getPosition();
                s+="ID: "+promotion.getID()+"\n";
                s+="Position: "+position.getName()+"\n";
                s+="Date: "+promotion.getTime()+"\n";
            }
        }
        return s;
    }
    
}
